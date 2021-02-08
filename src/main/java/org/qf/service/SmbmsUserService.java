package org.qf.service;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsRoleMapper;
import org.qf.mapper.SmbmsUserMapper;
import org.qf.pojo.SmbmsRole;
import org.qf.pojo.SmbmsUser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmbmsUserService {

    @Resource
    private SmbmsUserMapper smbmsUserMapper;

    @Resource
    private SmbmsRoleMapper smbmsRoleMapper;

    @Resource
    private SmbmsRoleService smbmsRoleService;

    //登录
    public SmbmsUser login(String username,String password){

        return smbmsUserMapper.selectByUsernameAndPwd(username,password);
    }

    public SmbmsUser queryLogin(String name){
        SmbmsUser smbmsUser=new SmbmsUser();
        smbmsUser.setUsername(name);
        return smbmsUserMapper.selectOne(smbmsUser);
    }


    //查询并分页  样例查询
    public PageInfo<SmbmsUser> listUser(Integer startNo,String queryName,Long queryUserRole){
        PageHelper.startPage(startNo,3);
        Example example=new Example(SmbmsUser.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(queryName)){
            criteria.andLike("username","%"+queryName+"%");
        }
        if (queryUserRole!=null && queryUserRole!=0){
            criteria.andEqualTo("userrole",queryUserRole);
        }
        List<SmbmsUser> smbmsUserList=smbmsUserMapper.selectByExample(example);
        bindRoleName(smbmsUserList);
        PageInfo<SmbmsUser> pageInfo=new PageInfo<>(smbmsUserList);
        return pageInfo;
    }


    //绑定rolename
    public  List<SmbmsUser> bindRoleName(List<SmbmsUser> smbmsUsers){
        for(SmbmsUser smbmsUser:smbmsUsers){
            smbmsUser.setRoleName(smbmsRoleService.queryUserRoleById(smbmsUser.getUserrole()).getRolename());
        }
        return smbmsUsers;
    }


    //单个查找
    public SmbmsUser findUserCode(String usercode){
        SmbmsUser smbmsUser=new SmbmsUser();
        smbmsUser.setUsercode(usercode);
        SmbmsUser user=smbmsUserMapper.selectOne(smbmsUser);
        return user;
    }

    //添加
    public int addUser(SmbmsUser smbmsUser){
        return smbmsUserMapper.insert(smbmsUser);
    }

    //删除
    public int deleteUser(Long id){
        return smbmsUserMapper.deleteByPrimaryKey(id);
    }

    //主键查找单个
    public SmbmsUser findById(Long id){
        SmbmsUser smbmsUser=smbmsUserMapper.selectByPrimaryKey(id);
        SmbmsRole smbmsRole=smbmsRoleMapper.selectByPrimaryKey(smbmsUser.getUserrole());
        smbmsUser.setRoleName(smbmsRole.getRolename());
        return smbmsUser;
    }

    //主键单个查找，不修改角色
    public SmbmsUser findByUserById(Long id){
        SmbmsUser smbmsUser=smbmsUserMapper.selectByPrimaryKey(id);
        return smbmsUser;
    }

    public int updateUser(SmbmsUser smbmsUser){
        int i=smbmsUserMapper.updateByPrimaryKeySelective(smbmsUser);
        return i;
    }

    //更新密码
    public int updatePwd(SmbmsUser smbmsUser){
        int i=smbmsUserMapper.updateByPrimaryKeySelective(smbmsUser);
        return i;
    }

}
