package csu.lzw.lmediaserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
public class HomeController {
    @RequestMapping("/main")
    public String mainDirect(){
        return "main";
    }
}
