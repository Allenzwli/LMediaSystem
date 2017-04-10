package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Admin;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    private Logger log = Logger.getLogger(AdminController.class);

    @RequestMapping("manage")
    public String adminManageDirect(){
        return "admin_manage";
    }

    @RequestMapping("add")
    public String adminAddDirect(){
        return "admin_add";
    }

}
