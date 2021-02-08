package org.qf.web;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.qf.pojo.SmbmsProvider;
import org.qf.service.SmbmsBillService;
import org.qf.service.SmbmsProviderService;
import org.qf.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
public class SmbmsProviderController {

    @Resource
    private SmbmsProviderService smbmsProviderService;

    @Resource
    private SmbmsBillService smbmsBillService;

    @RequestMapping("/listProvider")
    public String providerList(Model model, @RequestParam(name="pageIndex",defaultValue = "1")Integer pageIndex,String queryProCode,String queryProName){
        PageInfo<SmbmsProvider> smbmsProviderList=smbmsProviderService.listProvider(pageIndex,queryProCode,queryProName);
        model.addAttribute("providerList",smbmsProviderList);
        model.addAttribute("queryProCode",queryProCode);
        model.addAttribute("queryProName",queryProName);
        return "/jsp/providerlist";
    }

    @RequestMapping("/getProviders")
    @ResponseBody
    public List<SmbmsProvider> getProviders(){
        return smbmsProviderService.selectAll();
    }

    @RequestMapping("/addProvider")
    public String addProvider(SmbmsProvider smbmsProvider){
        smbmsProvider.setCreationdate(new Date());
        int i =smbmsProviderService.addProvider(smbmsProvider);
        if (i>0){
            return "redirect:listProvider";
        }else {
            return "/jsp/provideradd";
        }
    }

    @RequestMapping("/selectOne")
    public String selectOneById(Model model,int type,Long proid){
        SmbmsProvider smbmsProvider=smbmsProviderService.selectProviderById(proid);
        model.addAttribute("provider",smbmsProvider);
        if (type==1){
            return "/jsp/providerview";
        }else {
            return "/jsp/providermodify";
        }
    }

    @RequestMapping("deleteProviderById")
    @ResponseBody
    public JsonResult deleteById(Long proid){
        int i=smbmsProviderService.deleteProviderById(proid);
        int num=smbmsBillService.selectByProId(proid);
        if (num>0){
            return new JsonResult().message("该供应商尚有商品，无法删除").success(false).num(num);
        }else {
            if (i>0){
                return new JsonResult().message("删除成功").success(true);
            }else {
                return new JsonResult().message("删除失败").success(false);
            }
        }
    }

    @RequestMapping("/providerModify")
    public String providerModify(SmbmsProvider smbmsProvider,Model model){
        int i=smbmsProviderService.updateProvider(smbmsProvider);
        if (i>0){
            return "redirect:listProvider";
        }else {
            model.addAttribute("provider",smbmsProvider);
            return "/jsp/providermodify";
        }
    }
}
