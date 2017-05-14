package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.AdminMapper;
import csu.lzw.lmediaserver.pojo.Admin;
import csu.lzw.lmediaserver.service.AdminService;
import csu.lzw.lmediaserver.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by allenzwli on 2017/4/11.
 */
@Service("AdminService")
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImp implements AdminService{

    @Resource
    private AdminMapper mAdminMapper;

    public Admin getAdmin(String account, String encyptPassword) {
        return mAdminMapper.getAdminByAccountByEncyptPassword(account,encyptPassword);
    }

    public boolean validAdminToken(String token, int adminId) {
        Admin admin=mAdminMapper.getAdminByIdByToken(token,adminId);
        if(admin==null)
            return false;
        else
            return true;

    }

    public List<Admin> getAllAdmins() {
        return mAdminMapper.getAllAdmins();
    }

    public void deleteAdmin(int adminId) {
        mAdminMapper.deleteAdmin(adminId);
    }

    public String registAdmin(String account,String password,String repeatPassword,String nickName,String token) {
        String msg="注册成功";
        if(account==null||account.trim().equals("")||password==null||password.trim().equals("")||nickName==null||nickName.trim().equals("")||repeatPassword==null||repeatPassword.trim().equals("")){
            msg="注册失败，输入不能为空";
        }else if(!password.equals(repeatPassword)){
            msg="注册失败，两次输入的密码不一致";
        }else if(mAdminMapper.geAdminByAccount(account)!=null){
            msg="注册失败，用户名已存在";
        }else {
            Admin adminBean=new Admin();
            adminBean.setAccount(account);
            adminBean.setToken(token);
            adminBean.setEncyptPassword(MD5Util.getMD5(password));
            adminBean.setNickName(nickName);
            mAdminMapper.insertAdmin(adminBean);
        }
        return msg;
    }


}
