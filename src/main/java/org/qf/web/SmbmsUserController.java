package org.qf.web;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.qf.pojo.SmbmsRole;
import org.qf.pojo.SmbmsUser;
import org.qf.service.SmbmsRoleService;
import org.qf.service.SmbmsUserService;
import org.qf.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class SmbmsUserController {

    @Resource
    private SmbmsUserService smbmsUserService;

    @Resource
    private SmbmsRoleService smbmsRoleService;


    @PostMapping("/login")
    public String login(HttpSession session,Model model,String userCode,String userPassword){
        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken(userCode,userPassword);
        try {
            subject.login(token);
            return "/jsp/frame";
        }catch (UnknownAccountException e){
            model.addAttribute("error","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("error","密码错误");
            return "login";
        }catch (Exception e){
            model.addAttribute("error","登录失败");
            return "login";
        }

//        SmbmsUser smbmsUser=smbmsUserService.login(userCode,userPassword);
//
//        if (smbmsUser!=null){
//            session.setAttribute("user",smbmsUser);
//            return "redirect:/jsp/frame.jsp";
//        }else {
//            return "redirect:login";
//        }

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login.jsp";
    }

    @RequestMapping("/listUser")
    public String userList(Model model, @RequestParam(name = "pageIndex",defaultValue = "1")Integer pageIndex,String queryName,Long queryUserRole){
        PageInfo<SmbmsUser> users= smbmsUserService.listUser(pageIndex,queryName,queryUserRole);
        model.addAttribute("users",users);

        model.addAttribute("roleList",smbmsRoleService.queryRole());
        model.addAttribute("queryName",queryName);
        model.addAttribute("queryUserRole",queryUserRole);
        return "jsp/userlist";
    }


    @RequestMapping("/findUserCode")
    @ResponseBody
    public JsonResult findUserCode(String userCode){
        SmbmsUser smbmsUser=smbmsUserService.findUserCode(userCode);
        if (smbmsUser==null){
            return new JsonResult().message("可以使用").success(true);
        }else {
            return new JsonResult().message("该账户已经存在，不能使用").success(false);
        }
    }


    @RequestMapping("/findUserRole")
    @ResponseBody
    public List<SmbmsRole> findUserRole(){
        List<SmbmsRole> smbmsRoleList=smbmsRoleService.queryRole();
        return smbmsRoleList;
    }

    @RequestMapping("/addUser")
    public String addUser(Model model, SmbmsUser userForm){

        System.out.println(userForm);
        userForm.setCreationdate(new Date());
        smbmsUserService.addUser(userForm);
        return "redirect:/listUser";
    }

    @RequestMapping("/deleteUserById")
    @ResponseBody
    public JsonResult deleteUser(Long id){
        int i=smbmsUserService.deleteUser(id);
        if(i==0){
            return new JsonResult().message("删除失败").success(false);
        }else {
            return new JsonResult().message("删除成功").success(true);
        }

     //   return "redirect:/listUser";
    }


    @RequestMapping("/viewUser")
    public String viewUser(Long id,Model model){
        SmbmsUser smbmsUser=smbmsUserService.findById(id);
        model.addAttribute("user",smbmsUser);
        return "jsp/userview";
    }

    @RequestMapping("/userModify")
    public String userModify(Long id,Model model){
        SmbmsUser smbmsUser= smbmsUserService.findByUserById(id);
        model.addAttribute("roleList",smbmsRoleService.queryRole());
        model.addAttribute("user",smbmsUser);
        return "jsp/usermodify";
    }

    @RequestMapping("/updateUser")
    public String updateUser(SmbmsUser smbmsUser,Model model){
        int i=smbmsUserService.updateUser(smbmsUser);
        if (i==0){
            model.addAttribute("user",smbmsUser);
            return "jsp/usermodify";
        }else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/updatePwd")
    public String updatePwd(HttpSession session,HttpServletRequest request){
        String password=request.getParameter("password");
        System.out.println(password);
        SmbmsUser user=(SmbmsUser) session.getAttribute("user");
        user.setUserpassword(password);

        int i=smbmsUserService.updatePwd(user);
        if (i==0){
            return "jsp/pwdmodify";
        }else {
            return "login";
        }
    }

    @RequestMapping("/confirmPwd")
    @ResponseBody
    public JsonResult confirmPwd(HttpSession session,String password){
        SmbmsUser user=(SmbmsUser)session.getAttribute("user");
        if (user==null){
            return new JsonResult().message("sessionerror");
        }
        if (password==null){
            return new JsonResult().message("error");
        }
        String pwd=user.getUserpassword();
        if (pwd.equals(password)){
            return new JsonResult().message("密码匹配成功").success(true);
        }else {
            return new JsonResult().message("密码匹配失败").success(false);
        }
    }
}
