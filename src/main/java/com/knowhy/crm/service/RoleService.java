package com.knowhy.crm.service;

import com.knowhy.crm.dao.RolesDAO;
import com.knowhy.crm.dao.UserRoleDAO;
import com.knowhy.crm.pojo.Roles;
import com.knowhy.crm.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
