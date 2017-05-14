package csu.lzw.lmediaserver.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import csu.lzw.lmediaserver.pojo.Admin;
import csu.lzw.lmediaserver.service.AdminService;
import csu.lzw.lmediaserver.service.HomeService;
import csu.lzw.lmediaserver.service.MusicService;
import csu.lzw.lmediaserver.service.VideoService;
import csu.lzw.lmediaserver.service.imp.AdminServiceImp;
import csu.lzw.lmediaserver.util.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    @Resource
    private HomeService mHomeService;


    @RequestMapping("/main")
    public ModelAndView mainDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null){
            return new ModelAndView("login");
        }

        int musicCount=mHomeService.getSongAllCount();
        int videoCount=mHomeService.getVideoAllCount();

        ModelAndView modelAndView=new ModelAndView("main");
        modelAndView.addObject("musicCount",musicCount);
        modelAndView.addObject("videoCount",videoCount);
        return modelAndView;
    }

    @RequestMapping("/manage")
    public ModelAndView adminManageDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null||admin.getIsSuperAdmin()!=1){
            return new ModelAndView("login");
        }

        ModelAndView modelAndView=new ModelAndView("admin_manage");
        modelAndView.addObject("adminList",mAdminService.getAllAdmins());
        return modelAndView;
    }

    @RequestMapping("list")
    @ResponseBody
    public List<Admin> getAdminList(){
        return mAdminService.getAllAdmins();
    }


    @RequestMapping("register")
    public ModelAndView registerAdmin(HttpServletRequest request,String account,String password,String repeatPassword,String nickName){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null||admin.getIsSuperAdmin()!=1){
            return new ModelAndView("login");
        }

        String msg=mAdminService.registAdmin(account,password,repeatPassword,nickName,request.getSession().getId());

        ModelAndView modelAndView=new ModelAndView("admin_add");
        modelAndView.addObject("msg",msg);
        return modelAndView;
    }


    @RequestMapping("/delete")
    public ModelAndView deleteAdmin(HttpServletRequest request,int id){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null||admin.getIsSuperAdmin()!=1){
            return new ModelAndView("login");
        }

        mAdminService.deleteAdmin(id);

        ModelAndView modelAndView=new ModelAndView("admin_manage");
        modelAndView.addObject("adminList",mAdminService.getAllAdmins());
        return modelAndView;
    }

    @RequestMapping("/add")
    public String adminAddDirect(HttpServletRequest request){
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin==null||admin.getIsSuperAdmin()!=1){
            return "login";
        }
        return "admin_add";
    }

    @RequestMapping("/login")
    public ModelAndView adminLogin(HttpServletRequest request, String account, String encyptPassword){
        //检查登录态
        Admin admin=(Admin) request.getSession().getAttribute("admin");
        if(admin!=null){
            return new ModelAndView("main");
        }
        admin=mAdminService.getAdmin(account,encyptPassword);
        if(admin==null){
            return new ModelAndView("login");
        }
        request.getSession().setAttribute("admin",admin);

        int musicCount=mHomeService.getSongAllCount();
        int videoCount=mHomeService.getVideoAllCount();

        ModelAndView modelAndView=new ModelAndView("main");
        modelAndView.addObject("musicCount",musicCount);
        modelAndView.addObject("videoCount",videoCount);
        return modelAndView;
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
