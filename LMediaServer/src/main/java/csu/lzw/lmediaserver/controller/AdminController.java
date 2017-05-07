package csu.lzw.lmediaserver.controller;

import csu.lzw.lmediaserver.pojo.Admin;
import csu.lzw.lmediaserver.service.AdminService;
import csu.lzw.lmediaserver.service.imp.AdminServiceImp;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * Created by allenzwli on 2017/4/10.
 */
@Controller
@RequestMapping("/admins")
public class AdminController {

    private Logger log = Logger.getLogger(AdminController.class);

    @Resource
    private AdminService mAdminService;

    @RequestMapping("/manage")
    public String adminManageDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return "login";
        }
        return "admin_manage";
    }

    @RequestMapping("/add")
    public String adminAddDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return "login";
        }
        return "admin_add";
    }

    @RequestMapping("/login")
    public String adminLogin(HttpServletRequest request, String account, String encyptPassword){
        //检查登录态
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin!=null){
            return "main";
        }
        //未登录，则登录
        admin=mAdminService.getAdmin(account,encyptPassword);
        if(admin!=null){
            request.getSession().setAttribute("admin",admin);
            return "main";
        }else {
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String adminLogout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "login";
    }

    @ResponseBody
    @RequestMapping("/validate")
    public Admin validateAdmin(String account,String encyptPassword){
        return mAdminService.getAdmin(account,encyptPassword);
    }
}
