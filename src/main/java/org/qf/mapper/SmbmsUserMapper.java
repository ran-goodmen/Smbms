package org.qf.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.qf.pojo.SmbmsUser;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface SmbmsUserMapper extends Mapper<SmbmsUser> {

    @Select("select * from smbms_user where userName=#{username} and userPassword=#{password}")
    SmbmsUser selectByUsernameAndPwd(@Param("username") String username, @Param("password") String password);
}