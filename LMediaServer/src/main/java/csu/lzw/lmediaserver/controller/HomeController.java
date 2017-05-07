package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
public class HomeController {
    @RequestMapping("/main")
    public String mainDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return "login";
        }
        return "main";
    }
}
