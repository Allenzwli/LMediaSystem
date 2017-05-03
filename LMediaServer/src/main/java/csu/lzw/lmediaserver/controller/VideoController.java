package csu.lzw.lmediaserver.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/videos")
public class VideoController {
    private Logger log = Logger.getLogger(VideoController.class);

    @RequestMapping("/manage")
    public String videoManageDirect(){
        return "video_manage";
    }

    @RequestMapping("/add")
    public String videoAddDirect(){
        return "video_add";
    }
}
