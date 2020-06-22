package com.konwhy.crm.service;

import com.konwhy.crm.dao.*;
import com.konwhy.crm.pojo.Func;
import com.konwhy.crm.pojo.IUser;
import com.konwhy.crm.pojo.RoleFunc;
import com.konwhy.crm.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserDAO iUserDAO;
    @Autowired
    RolesDAO rolesDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    FuncDAO funcDAO;
    @Autowired
    RoleFuncDAO roleFuncDAO;

    /**
     * 根据账号获取所有权限
     * 首先获取该账号所包含的角色
     * 根据角色获取所有的操作权限
     * @param account
     * @return
     */
    public List<Func> findPermsByAccount(String account){
        List<UserRole> userRoleList = userRoleDAO.findByAccount(account);
        List<Func> perms = new ArrayList<>();
        for(int i = 0 ; i < userRoleList.size() ; i++){
            int rid = userRoleList.get(i).getId();
            List<RoleFunc> roleFuncList = roleFuncDAO.findByRid(rid);
            for(int j = 0 ; j < roleFuncList.size() ; j++){
                int fid = roleFuncList.get(j).getFid();
                Func func = funcDAO.getOne(fid);
                if(!perms.contains(func)){
                    perms.add(func);
                }

            }
        }
        return perms;
    }

    public IUser findUserByAccount(String account){
        return iUserDAO.findByAccount(account).get(0);
    }

    public  boolean AccountExist(String account){
        List<IUser> accountList = iUserDAO.findByAccount(account);
        if(accountList == null || accountList.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    public int getAll(){
        List<IUser> userList = iUserDAO.findAll();
        return userList.size();
    }

    public void createAccount(IUser user){
        iUserDAO.save(user);
    }

    public boolean phoneUsed(String phone){
        List<IUser> userList = iUserDAO.findByPhone(phone);
        if(userList == null || userList.size() == 0){
            return false;
        }else{
            return true;
        }
    }

    public void setRole(String account , int roleId){
        UserRole userRole = new UserRole();
        userRole.setAccount(account);
        userRole.setId(roleId);
        userRoleDAO.save(userRole);
    }

    public IUser findByAccountAndEmail(String account , String email){
        List<IUser> iUserList = iUserDAO.findByAccountAndEmail(account , email);
        if(iUserList != null && iUserList.size() != 0){
            return iUserList.get(0);
        }else{
            return null;
        }
    }

    public void updatePassword(IUser iUser){
        iUserDAO.save(iUser);
    }

    public IUser findByPhone(String phone){
        List<IUser> iUserList = iUserDAO.findByPhone(phone);
        if(iUserList == null || iUserList.size() == 0){
            return null;
        }else{
            return iUserList.get(0);
        }
    }
}
