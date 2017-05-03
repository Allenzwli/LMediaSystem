package csu.lzw.lmediaserver.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/logs")
public class LogController {
    private Logger log = Logger.getLogger(LogController.class);

    @RequestMapping("/manage")
    public String logManageDirect(){
        return "log_manage";
    }
}
