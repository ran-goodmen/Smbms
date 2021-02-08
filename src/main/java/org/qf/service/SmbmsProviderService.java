package org.qf.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsProviderMapper;
import org.qf.pojo.SmbmsProvider;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SmbmsProviderService {

    @Resource
    private SmbmsProviderMapper smbmsProviderMapper;

    public PageInfo<SmbmsProvider> listProvider(int pageIndex,String queryProCode,String queryProName){
        PageHelper.startPage(pageIndex,3);
        Example example=new Example(SmbmsProvider.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(queryProCode) && queryProCode!=""){
            criteria.andLike("procode","%"+queryProCode+"%");
        }
        if (!StringUtils.isEmpty(queryProName) && queryProName!=""){
            criteria.andLike("proname","%"+queryProName+"%");
        }
        List<SmbmsProvider> smbmsProviderList=smbmsProviderMapper.selectByExample(example);
        PageInfo<SmbmsProvider> pageInfo=new PageInfo<>(smbmsProviderList);
        return pageInfo;
    }


    public SmbmsProvider selectProviderById(Long id){
        return smbmsProviderMapper.selectByPrimaryKey(id);
    }

    public List<SmbmsProvider> selectAll(){
        return smbmsProviderMapper.selectAll();
    }

    public int addProvider(SmbmsProvider smbmsProvider){
        return smbmsProviderMapper.insert(smbmsProvider);
    }

    public int deleteProviderById(Long proid){
        return smbmsProviderMapper.deleteByPrimaryKey(proid);
    }

    public int updateProvider(SmbmsProvider smbmsProvider){
        return smbmsProviderMapper.updateByPrimaryKeySelective(smbmsProvider);
    }
}
