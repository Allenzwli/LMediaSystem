package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Song;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.util.AudioUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

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

    @RequestMapping("manage")
    public ModelAndView musicManageDirect(){
        ModelAndView modelAndView=new ModelAndView("music_manage");
        List<Song> songList=mMusicService.getAllSongs();
        modelAndView.addObject("songList",songList);
        return modelAndView;
    }

    @RequestMapping("add")
    public String musicAddDirect(){
        return "music_add";
    }

    @RequestMapping("upload")
    public ModelAndView musicUpload(@RequestParam(value = "songFile", required = false) MultipartFile songFile){
        ModelAndView modelAndView=new ModelAndView("music_add");
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
                song.setFileName(originalFileName);
                mMusicService.saveSong(song);
                modelAndView.addObject("message","文件:"+originalFileName+" 上传成功");
            } else {
                modelAndView.addObject("message","上传失败，文件类型应为mp3 ");
            }
        }else{
            modelAndView.addObject("message","上传失败，上传的文件不能为空");
        }
        return modelAndView;
    }

}
