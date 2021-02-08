package org.qf.service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.qf.mapper.SmbmsBillMapper;
import org.qf.pojo.SmbmsBill;
import org.springframework.stereotype.Service;
import sun.util.resources.ga.LocaleNames_ga;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.security.acl.LastOwnerException;
import java.util.List;

@Service
public class SmbmsBillService {

    @Resource
    private SmbmsBillMapper smbmsBillMapper;

    @Resource
    private SmbmsProviderService smbmsProviderService;

    //列出所有并分页
    public PageInfo<SmbmsBill> listBills(Integer startNo,String queryProductName,Long queryProviderId,Long queryIsPayment){
        PageHelper.startPage(startNo,3);
        Example example=new Example(SmbmsBill.class);
        Example.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(queryProductName)){
            criteria.andLike("productname","%"+queryProductName+"%");
        }
        if (queryProviderId!=null && queryProviderId!=0){
            criteria.andEqualTo("providerid",queryProviderId);
        }
        if (queryIsPayment!=null && queryIsPayment!=0){
            criteria.andEqualTo("ispayment",queryIsPayment);
        }

        List<SmbmsBill> smbmsBillList=smbmsBillMapper.selectByExample(example);
        bindProvider(smbmsBillList);
        PageInfo<SmbmsBill> pageInfo=new PageInfo<>(smbmsBillList);
        return pageInfo;
    }

    //绑定供应商
    public List<SmbmsBill> bindProvider(List<SmbmsBill> smbmsBills){
        for (SmbmsBill smbmsBill:smbmsBills){
            smbmsBill.setProviderName(smbmsProviderService.selectProviderById(smbmsBill.getProviderid()).getProname());
        }
        return smbmsBills;
    }


    //删除
    public int deleteBill(Long id){
        return smbmsBillMapper.deleteByPrimaryKey(id);
    }

    //单个查找，不管其他属性
    public SmbmsBill selectById(Long billid){
        return smbmsBillMapper.selectByPrimaryKey(billid);
    }

    //插入
    public int addBill(SmbmsBill smbmsBill){
        return smbmsBillMapper.insert(smbmsBill);
    }

    //根据供应商id查找
    public int selectByProId(Long proid){
        Example example=new Example(SmbmsBill.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("providerid",proid);
        return smbmsBillMapper.selectByExample(example).size();
    }

    //更新
    public int updateBill(SmbmsBill smbmsBill){
        return smbmsBillMapper.updateByPrimaryKeySelective(smbmsBill);
    }

}
