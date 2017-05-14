package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Admin;

import java.util.List;

/**
 * Created by allenzwli on 2017/4/11.
 */
public interface AdminService {
    Admin getAdmin(String account,String encyptPassword);
    boolean validAdminToken(String token,int adminId);

    List<Admin> getAllAdmins();
    void deleteAdmin(int adminId);
    String registAdmin(String account,String password,String repeatPassword,String nickName,String token);

}
