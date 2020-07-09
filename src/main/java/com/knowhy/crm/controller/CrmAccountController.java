package com.knowhy.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.entity.CrmAccount;
import com.knowhy.crm.mapper.CrmAccountMapper;
import com.knowhy.crm.pojo.Roles;
import com.knowhy.crm.service.RoleService;
import com.knowhy.crm.service.UserService;
import com.knowhy.crm.util.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.knowhy.crm.controller.BaseController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Bruce
 * @since 2020-06-23
 */
@RestController
public class CrmAccountController extends BaseController {
    @Resource
    CrmAccountMapper crmAccountMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @RequestMapping("/selectUserByPage")
    public Map<String,Object> selectByPage(@RequestParam("start")int start){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<CrmAccount> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("account");

        Page<CrmAccount> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmAccount> iPage = crmAccountMapper.selectPage(page,queryWrapper);

        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }

        List<CrmAccount> crmAccountList = iPage.getRecords();
        List<UserInfo> userInfoList = new ArrayList<>();
        for(int i = 0 ; i < crmAccountList.size() ; i++){
            CrmAccount crmAccount = crmAccountList.get(i);
            UserInfo userInfo = new UserInfo();
            String account = crmAccount.getAccount();
            userInfo.setAccount(account);
            userInfo.setName(crmAccount.getName());
            userInfo.setCompany(crmAccount.getCompany());
            userInfo.setEmail(crmAccount.getEmail());
            userInfo.setPhone(crmAccount.getPhone());
            userInfo.setRole(roleService.findByAccount(account));
            userInfoList.add(userInfo);
        }
        map.put("user" , userInfoList);
        map.put("size",size);
        return map;
    }

    @RequestMapping("/selectUserByPageAndCondition")
    public Map<String,Object> selectByPageAndCondition(@RequestParam("start")int start , @RequestParam("account")String userAccount , @RequestParam("company")String company , @RequestParam("phone")String phone ){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<CrmAccount> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("account");
        if(!userAccount.isEmpty()){
            queryWrapper.eq("account" , userAccount.trim());
        }
        if(!company.isEmpty()){
            queryWrapper.eq("company" , company.trim());
        }
        if(!phone.isEmpty()){
            queryWrapper.eq("phone" , phone.trim());
        }

        Page<CrmAccount> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmAccount> iPage = crmAccountMapper.selectPage(page,queryWrapper);

        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }

        List<CrmAccount> crmAccountList = iPage.getRecords();
        List<UserInfo> userInfoList = new ArrayList<>();
        for(int i = 0 ; i < crmAccountList.size() ; i++){
            CrmAccount crmAccount = crmAccountList.get(i);
            UserInfo userInfo = new UserInfo();
            String account = crmAccount.getAccount();
            userInfo.setAccount(account);
            userInfo.setName(crmAccount.getName());
            userInfo.setCompany(crmAccount.getCompany());
            userInfo.setEmail(crmAccount.getEmail());
            userInfo.setPhone(crmAccount.getPhone());
            userInfo.setRole(roleService.findByAccount(account));
            userInfoList.add(userInfo);
        }
        map.put("user" , userInfoList);
        map.put("size",size);
        return map;
    }

    @RequestMapping("/getAllRoles")
    public Map<String , Object> getAllRoles(@RequestParam("account")String account){
        Map<String , Object> map = new HashMap<>();

        List<Roles> roles = roleService.findAll();
        List<String> had = roleService.findRolesByAccount(account);
        map.put("roles" , roles);
        map.put("had" , had);
        return map;
    }

    @RequestMapping("/findAllRoles")
    public Map<String , Object> findRoles(){
        Map<String , Object> map = new HashMap<>();
        List<Roles> roles = roleService.findAll();
        map.put("roles" , roles);
        return map;
    }

    @RequestMapping("/updateRoles")
    public String changeRoles(@RequestParam("account")String account , @RequestParam("roles[]")int[] roles){
        return roleService.changeRoles(account , roles);
    }

    @RequestMapping("/onlyGetAllRoles")
    public Map<String , Object> selectAllRoles(){
        Map<String , Object> map = new HashMap<>();

        List<Roles> roles = roleService.findAll();
        map.put("roles" , roles);
        return map;
    }

    @RequestMapping("/deleteUser")
    public String deleteFromAccount(@RequestParam("account")String account){
        try{
            userService.deleteAccount(account);
        }catch (Exception e){
            return e.getMessage();
        }
        return "账号删除成功";
    }

}
