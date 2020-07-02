package com.knowhy.crm.service;

import com.knowhy.crm.dao.RolesDAO;
import com.knowhy.crm.dao.UserRoleDAO;
import com.knowhy.crm.pojo.Roles;
import com.knowhy.crm.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RolesDAO rolesDAO;

    public String findByAccount(String account){
        List<UserRole> userRoleList = userRoleDAO.findByAccount(account);
        String role ="";
        if(userRoleList != null || userRoleList.size()!= 0){
            for(int i = 0 ; i < userRoleList.size() ; i++){
                int roleId = userRoleList.get(i).getId();
                Roles roles = rolesDAO.getOne(roleId);
                if(i == userRoleList.size()-1){
                    role = role+roles.getRoleName();
                }else{
                    role = role+roles.getRoleName()+",";
                }
            }
        }
        return role;
    }

    public List<Roles> findAll(){
        return rolesDAO.findAll();
    }

    public List<String> findRolesByAccount(String account){
        List<Roles> rolesList = rolesDAO.findByAccount(account);
        if(rolesList != null || rolesList.size()!= 0){
            List<String> roles = new ArrayList<>();
            for(int i = 0 ; i < rolesList.size() ; i++){
                String roleName = rolesList.get(i).getRoleName();
                if(!roles.contains(roleName)){
                    roles.add(roleName);
                }
            }
            return roles;
        }else{
            return null;
        }
    }

    @Transactional
    public String changeRoles(String account , int[] roles){
        List<UserRole> userRoleList = userRoleDAO.findByAccount(account);
        int size = userRoleList.size();
        int[] had = new int[size];
        for(int i = 0 ; i < userRoleList.size() ; i++){
            had[i] = userRoleList.get(i).getId();
        }
        if(had.equals(roles)){
            return "";
        }else{
            userRoleDAO.deleteByAccount(account);
            for(int i = 0 ; i < roles.length ; i++){
                UserRole userRole = new UserRole();
                userRole.setAccount(account);
                userRole.setId(roles[i]);
                userRoleDAO.save(userRole);
            }
            return "角色修改成功";
        }
    }

    public boolean isCustomer(int roleId){
        Roles role = rolesDAO.getOne(roleId);
        String roleName = role.getRoleName();
        if(roleName.equals("客户")){
            return true;
        }else{
            return false;
        }
    }
}
