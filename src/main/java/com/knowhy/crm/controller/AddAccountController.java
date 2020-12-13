package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
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
    @Autowired
    SaleManDAO saleManDAO;
    @Autowired
    GroupDAO groupDAO;
    @Autowired
    DeptDAO deptDAO;
    @Autowired
    DeptGroupDAO deptGroupDAO;
    @Autowired
    UserRoleDAO userRoleDAO;

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
    public String saveInner(String account , String name , String phone , String email , String wechatNum , String roleId , String roleName , String group){
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
//        if(phone == null || "".equals(phone)){
//            return "请输入手机号";
//        }else {
//            user.setPhone(phone);
//        }
        if(phone != null && !"".equals(phone)){
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
            if(roleName.equals("销售")){
                SaleMan saleMan = new SaleMan();
                saleMan.setAccount(account);
                saleMan.setName(name);
                saleMan.setPhone(phone);
                saleMan.setEmail(email);
                saleManDAO.save(saleMan);
            }
            if(!"0".equals(group)){
                Group group1 = groupDAO.getOne(group);
                DeptGroup deptGroup = new DeptGroup();
                deptGroup.setAccount(account);
                deptGroup.setName(name);
                deptGroup.setPhone(phone);
                deptGroup.setDeptCode(group1.getDeptCode());
                deptGroup.setGroupCode(group);
                if(roleName.equals("销售助理")){
                    deptGroup.setRole("assistant");
                }
                deptGroupDAO.save(deptGroup);
            }else{
                if(roleName.equals("市场总监")){
                    DeptGroup deptGroup = new DeptGroup();
                    deptGroup.setAccount(account);
                    deptGroup.setName(name);
                    deptGroup.setPhone(phone);
                    deptGroup.setRole("manager");
                    Dept dept = deptDAO.findByDeptNameLike("市场部").get(0);
                    deptGroup.setDeptCode(dept.getDeptCode());
                    deptGroupDAO.save(deptGroup);
                }else if (roleName.equals("技术总监")){
                    DeptGroup deptGroup = new DeptGroup();
                    deptGroup.setAccount(account);
                    deptGroup.setName(name);
                    deptGroup.setPhone(phone);
                    deptGroup.setRole("manager");
                    Dept dept = deptDAO.findByDeptNameLike("技术部").get(0);
                    deptGroup.setDeptCode(dept.getDeptCode());
                    deptGroupDAO.save(deptGroup);
                }else if(roleName.equals("运营总监")){
                    DeptGroup deptGroup = new DeptGroup();
                    deptGroup.setAccount(account);
                    deptGroup.setName(name);
                    deptGroup.setPhone(phone);
                    deptGroup.setRole("manager");
                    Dept dept = deptDAO.findByDeptNameLike("运营部").get(0);
                    deptGroup.setDeptCode(dept.getDeptCode());
                    deptGroupDAO.save(deptGroup);
                }
                else if(roleName.equals("供应链总监")){
                    DeptGroup deptGroup = new DeptGroup();
                    deptGroup.setAccount(account);
                    deptGroup.setName(name);
                    deptGroup.setPhone(phone);
                    deptGroup.setRole("manager");
                    Dept dept = deptDAO.findByDeptNameLike("供应链").get(0);
                    deptGroup.setDeptCode(dept.getDeptCode());
                    deptGroupDAO.save(deptGroup);
                }
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/changeUserRole")
    @Transactional
    public String updateUserRole(String account , int role){
        userRoleDAO.deleteByAccount(account);
        UserRole userRole = new UserRole();
        userRole.setAccount(account);
        userRole.setId(role);
        userRoleDAO.save(userRole);
        return "OK";
    }
}
