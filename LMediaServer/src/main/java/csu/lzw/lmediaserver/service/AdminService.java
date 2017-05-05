package csu.lzw.lmediaserver.service;

import csu.lzw.lmediaserver.pojo.Admin;

/**
 * Created by allenzwli on 2017/4/11.
 */
public interface AdminService {
    Admin getAdmin(String account,String encyptPassword);
    boolean validAdminToken(String token,int adminId);

}
