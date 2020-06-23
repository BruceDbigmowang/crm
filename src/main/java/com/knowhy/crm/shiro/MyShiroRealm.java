package com.knowhy.crm.shiro;


import com.knowhy.crm.pojo.Func;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权的方法");
        //获取用户对象
        IUser user = (IUser)principalCollection.getPrimaryPrincipal();
        //获取用户权限列表
        List<String> perms = new ArrayList<>();
        //根据用户id获取权限类别
        List<Func> funcList = userService.findPermsByAccount(user.getAccount());
        if(funcList != null){
            for(Func func:funcList){
                perms.add(func.getCode());
            }
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(perms); //把用户的所有权限类别添加到对象中
        //authorizationInfo.addRoles(roles); //把所有的用户角色添加到对象中
        return authorizationInfo;
    }


    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证的方法");
        String account = authenticationToken.getPrincipal().toString();
        IUser user = userService.findUserByAccount(account);
        //账号登录
        if(user != null ){
            System.out.println("用户："+user.getAccount());
            String password = user.getPassword();
            //把用户名和密码封装到AuthenticationInfo对象中
            ByteSource salt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,password,salt ,"shiroRealm");
            return authenticationInfo;
        }else{
            //手机号登录
            IUser user1 = userService.findByPhone(account);
            if(user != null ) {
                System.out.println("用户：" + user.getAccount());
                String password = user.getPassword();
                //把用户名和密码封装到AuthenticationInfo对象中
                ByteSource salt = ByteSource.Util.bytes(user.getSalt());
                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user1, password, salt, "shiroRealm");
                return authenticationInfo;
            }
        }
        return null;
    }
}
