package org.qf.service;

import org.qf.mapper.SmbmsRoleMapper;
import org.qf.pojo.SmbmsRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmbmsRoleService {

    @Resource
    private SmbmsRoleMapper smbmsRoleMapper;

    public List<SmbmsRole> queryRole(){
        List<SmbmsRole> smbmsRoleList=smbmsRoleMapper.selectAll();
        return smbmsRoleList;
    }

    public SmbmsRole queryUserRoleById(Long id){
        return smbmsRoleMapper.selectByPrimaryKey(id);
    }

}
