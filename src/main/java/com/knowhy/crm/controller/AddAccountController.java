package com.knowhy.crm.controller;

import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.Roles;
import com.knowhy.crm.service.RoleService;
import com.knowhy.crm.service.UserService;
import com.knowhy.crm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class AddAccountController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @RequestMapping("/getInnerRole")
    public List<Roles> getAllInnerRole(){
        return roleService.getInner();
    }

    @RequestMapping("/getInnerUser")
    public List<IUser> getAllUser(){
        return userService.getInnerUser();
    }

    @RequestMapping("/saveInnerUser")
    @Transactional
    public String saveInner(String account , String name , String phone , String email , String wechatNum , String roleId){
        IUser user = new IUser();
        if(account == null || "".equals(account)){
            return "请输入账号";
        }else{
            user.setAccount(account);
        }
        if(name == null || "".equals(name)){
            return "请输入姓名";
        }else{
            user.setName(name);
        }
        if(phone == null || "".equals(phone)){
            return "请输入手机号";
        }else {
            user.setPhone(phone);
        }
        if(email == null || "".equals(email)){
            return "请输入邮箱";
        }else{
            user.setEmail(email);
        }
        if(wechatNum != null && !"".equals(wechatNum)){
            user.setWechatNum(wechatNum);
        }
        String password = "123456";
        password = MD5Utils.addMD5(password).toString();
        user.setPassword(password);
        user.setSalt("abc");
        user.setCreateTime(new Date());
        user.setStatus("O");
        user.setNote("inner");
        int role;
        if(roleId.equals("0")){
            return "请选择角色";
        }else{
            role = Integer.parseInt(roleId);
        }

        try{
            userService.createAccount(user);
            userService.setRole(account , role);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }
}
