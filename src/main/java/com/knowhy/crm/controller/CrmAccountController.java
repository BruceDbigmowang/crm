package com.knowhy.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.entity.CrmAccount;
import com.knowhy.crm.mapper.CrmAccountMapper;
import com.knowhy.crm.service.RoleService;
import com.knowhy.crm.util.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/selectUserByPage")
    public Map<String,Object> selectByPage(){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<CrmAccount> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("account");

        Page<CrmAccount> page = new Page<>(1,5);  // 查询第1页，每页返回5条
        IPage<CrmAccount> iPage = crmAccountMapper.selectPage(page,queryWrapper);
        List<UserInfo> userInfoList = new ArrayList<>();
        for(int i = 0 ; i < iPage.getSize() ; i++){
            CrmAccount crmAccount = iPage.getRecords().get(i);
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
        return map;
    }

}
