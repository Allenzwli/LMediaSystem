package csu.lzw.lmediaserver.service.imp;

import csu.lzw.lmediaserver.mapper.AdminMapper;
import csu.lzw.lmediaserver.pojo.Admin;
import csu.lzw.lmediaserver.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
}
