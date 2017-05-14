package csu.lzw.lmediaserver.mapper;

import csu.lzw.lmediaserver.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by allenzwli on 2017/4/11.
 */
@Repository
public interface AdminMapper {
    Admin getAdminByAccountByEncyptPassword(@Param("account") String account,@Param("encyptPassword") String encyptPassword);
    Admin getAdminByIdByToken(@Param("token")String token,@Param("id") int adminId);
    List<Admin> getAllAdmins();
    void deleteAdmin(int id);
    Admin geAdminByAccount(@Param("account") String account);
    void insertAdmin(Admin admin);

}
