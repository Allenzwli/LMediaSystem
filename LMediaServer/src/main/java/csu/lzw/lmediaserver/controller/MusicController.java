package csu.lzw.lmediaserver.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/musics")
public class MusicController {
    private Logger log = Logger.getLogger(MusicController.class);

    @RequestMapping("manage")
    public String musicManageDirect(){
        return "music_manage";
    }

    @RequestMapping("add")
    public String musicAddDirect(){
        return "music_add";
    }
}
