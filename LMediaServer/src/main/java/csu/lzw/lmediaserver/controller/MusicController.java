package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.util.AudioUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    private static final String BASE_LOCAL_MUSIC_FILE_PATH="/Users/allenzwli/Documents/songlib/";

    private Logger mLogger = Logger.getLogger(MusicController.class);

    @Resource
    private MusicService mMusicService;

    @RequestMapping("/manage")
    public String musicManageDirect(){
        return "music_manage";
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Song> getAllSongs(){
        return mMusicService.getAllSongs();
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
            String realPath=BASE_LOCAL_MUSIC_FILE_PATH+originalFileName;
            String songType=originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
            if(songType.equals(".mp3")) {
                File file = new File(realPath);
                try {
                    songFile.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Song song= AudioUtil.getMP3Info(file);
                Song song=new Song();
                song.setFileSize(file.length());
                song.setFileUrl("localhost:8080/songlib/"+originalFileName);
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
