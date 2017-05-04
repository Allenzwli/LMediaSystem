package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.util.StaticConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public Map getSongsByStartAndLength(int startIndex,int pageSize){
            Map<String,Object> resultMap=new HashMap<String,Object>();
            resultMap.put("pageData",mMusicService.getSongsByStartIndexAndLength(startIndex,pageSize));
            resultMap.put("total",mMusicService.getSongAllCount());
            return resultMap;
    }

    @RequestMapping("/add")
    public String musicAddDirect(){
        return "music_add";
    }



    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Map musicUpload(@RequestParam(value = "songFile", required = false) MultipartFile songFile,int adminId){
        Map<String,Object> resultMap=new HashMap<String, Object>();
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
                //Song song= AudioUtil.getMP3Info(file);
                Song song=new Song();
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
        return resultMap;
    }

}
