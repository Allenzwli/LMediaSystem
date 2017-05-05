package csu.lzw.lmediaserver.mapper;

import csu.lzw.lmediaserver.pojo.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by allenzwli on 2017/4/11.
 */
@Repository
public interface AdminMapper {
    Admin getAdminByAccountByEncyptPassword(@Param("account") String account,@Param("encyptPassword") String encyptPassword);
    Admin getAdminByIdByToken(@Param("token")String token,@Param("id") int adminId);
}
