package org.qf.web;

import com.github.pagehelper.PageInfo;
import org.qf.pojo.SmbmsBill;
import org.qf.pojo.SmbmsProvider;
import org.qf.service.SmbmsBillService;
import org.qf.service.SmbmsProviderService;
import org.qf.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
public class SmbmsBillController {

    @Resource
    private SmbmsBillService smbmsBillService;

    @Resource
    private SmbmsProviderService smbmsProviderService;

    @RequestMapping("/listBill")
    public String listBill(Model model, @RequestParam(name = "pageIndex",defaultValue = "1")Integer pageIndex, @RequestParam(name = "queryProductName",defaultValue = "") String queryProductName, Long queryProviderId,Long queryIsPayment){
        PageInfo<SmbmsBill> billList=smbmsBillService.listBills(pageIndex,queryProductName,queryProviderId,queryIsPayment);
        model.addAttribute("bills",billList);
        model.addAttribute("providerList",smbmsProviderService.selectAll());
        model.addAttribute("queryIsPayment",queryIsPayment);
        model.addAttribute("queryProductName",queryProductName);
        model.addAttribute("queryProviderId",queryProviderId);
        return "/jsp/billlist";
    }

    @ResponseBody
    @RequestMapping("/deleteBill")
    public JsonResult deleteBill(Long billid){
        int i=smbmsBillService.deleteBill(billid);
        if(i==0){
            return new JsonResult().message("删除失败").success(false);
        }else {
            return new JsonResult().message("删除成功").success(true);
        }
    }


    @RequestMapping("/viewBill")
    public String viewBill(Model model,int type,Long billid){
        SmbmsBill smbmsBill=smbmsBillService.selectById(billid);
        SmbmsProvider smbmsProvider=smbmsProviderService.selectProviderById(smbmsBill.getProviderid());
        smbmsBill.setProviderName(smbmsProvider.getProname());
        model.addAttribute("bill",smbmsBill);
        if (type==1){
            return "/jsp/billview";
        }else {
            return "/jsp/billmodify";
        }
    }


    @RequestMapping("/addBill")
    public String addBill(SmbmsBill billForm){
        billForm.setCreationdate(new Date());
        smbmsBillService.addBill(billForm);
        return "redirect:/listBill";
    }

    @RequestMapping("/updateBill")
    public String updateBill(SmbmsBill billForm,Model model){
        int i=smbmsBillService.updateBill(billForm);
        if (i>0){
            return "redirect:listBill";
        }else {
            model.addAttribute("bill",billForm);
            return "/jsp/billmodify";
        }
    }

}
