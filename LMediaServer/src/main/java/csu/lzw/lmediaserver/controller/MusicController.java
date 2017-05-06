package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.AdminService;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.util.MediaUtil;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/musics")
public class MusicController {

    private Logger mLogger = Logger.getLogger(MusicController.class);

    @Resource
    private MusicService mMusicService;

    @Resource
    private AdminService mAdminService;

    @RequestMapping("/manage")
    public String musicManageDirect(){
        return "music_manage";
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Song> getSongsList(@RequestParam(required = false) int page){
            return mMusicService.getSongsPerPage(page);
    }

    @ResponseBody
    @RequestMapping("/get")
    public Map getSongsByStartAndLength(int startIndex,int pageSize,String fuzzy,String orderColumn,String orderDir,boolean fuzzySearch,String fileName,String songName,String artist,String album){
            Map<String,Object> resultMap=new HashMap<String,Object>();
            if(fuzzySearch){
                //模糊搜索
                resultMap.put("pageData",mMusicService.getSongsByStartIndexAndLength(fuzzy,orderColumn,orderDir,startIndex,pageSize));
                resultMap.put("total",mMusicService.getSongAllCount(fuzzy));
            }
            else {
                //高级搜索
                resultMap.put("pageData",mMusicService.getSongsByStartIndexAndLengthAdvanced(fileName,artist,songName,album,orderColumn,orderDir,startIndex,pageSize));
                resultMap.put("total",mMusicService.getSongAllCountAdvanced(fileName,artist,songName,album));
            }
            return resultMap;
    }

    @RequestMapping("/add")
    public String musicAddDirect(){
        return "music_add";
    }



    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map musicUpload(@RequestParam(value = "songFile", required = false) MultipartFile songFile,int adminId,String token){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(!mAdminService.validAdminToken(token,adminId)){
            resultMap.put("data",4);
            resultMap.put("msg","上传失败，无效的管理员身份");
        }else{
            if(songFile!=null&&!songFile.isEmpty()){
                String originalFileName=songFile.getOriginalFilename();
                String realPath= StaticConfig.BASE_LOCAL_MUSIC_FILE_PATH+originalFileName;
                String songType=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
                if(songType.equals(".mp3")) {
                    File file = new File(realPath);
                    try {
                        songFile.transferTo(file);
                    } catch (IOException e) {
                        //e.printStackTrace();
                        resultMap.put("data",3);
                        resultMap.put("msg",e.toString());
                    }
                    Song song= MediaUtil.getMP3Info(file);
                    song.setFileSize(file.length());
                    song.setFileUrl(StaticConfig.MUSIC_FILE_URL_PREFIX+originalFileName);
                    song.setAdminId(adminId);
                    song.setFileName(originalFileName);
                    mMusicService.saveSong(song);
                    resultMap.put("data",0);
                    resultMap.put("msg","歌曲:"+originalFileName+" 已上传成功");
                } else {
                    resultMap.put("data",1);
                    resultMap.put("msg","上传失败，文件类型应为mp3");
                }
            }else{
                resultMap.put("data",2);
                resultMap.put("msg","上传失败，上传的文件不能为空");
            }
        }
        return resultMap;
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Map musicDelete(@RequestParam(value = "needDeleteJsonArray")String needDeleteJsonArray, String token, int adminId){
        Map<String,Object> resultMap=new HashMap<String, Object>();
        if(!mAdminService.validAdminToken(token,adminId)){
            resultMap.put("data",3);
            resultMap.put("msg","删除失败，无效的管理员身份");
        }else{
            List<Song> needDeleteSongList=null;
            try {
                needDeleteSongList=new ObjectMapper().readValue(needDeleteJsonArray,new TypeReference<List<Song>>() {});
            } catch (IOException e) {
                e.printStackTrace();
            }
            int idArray[]=new int[needDeleteSongList.size()];
            for(int i=0;i<needDeleteSongList.size();i++){
                idArray[i]=needDeleteSongList.get(i).getId();
                File file=new File(StaticConfig.BASE_LOCAL_MUSIC_FILE_PATH+needDeleteSongList.get(i).getFileName());
                if(file.exists())
                    file.delete();
            }
            mMusicService.deleteSongs(idArray);
            resultMap.put("data",0);
        }
        return resultMap;
    }
}
