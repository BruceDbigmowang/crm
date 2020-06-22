package com.konwhy.crm.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.konwhy.crm.shiro.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

//    @Value("${server.session-timeout}")
//    private int tomcatTimeout;
    //添加创建securityManage工厂类注入bean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /**
         * 设置权限过滤器
         */
        Map<String , String> perms = new HashMap<String , String>();
        perms.put("/foreLogin" , "anon");
        perms.put("/foreIndex" , "authc");
        perms.put("/foreRegister" , "anon");

        shiroFilterFactoryBean.setLoginUrl("doLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("foreLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(perms);

        return shiroFilterFactoryBean;
    }

    //创建securityManage类的注入bean
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") MyShiroRealm shiroRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(shiroRealm);
        return defaultWebSecurityManager;
    }

    //创建自定义realm域类的注入bean
    @Bean(name = "shiroRealm")
    public MyShiroRealm getMyShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(6);
        myShiroRealm.setCredentialsMatcher(credentialsMatcher);
        return myShiroRealm;
    }

    @Bean
    public SessionDAO sessionDAO(){
        return new MemorySessionDAO();
    }

    @Bean
    public SimpleCookie simpleCookie(){
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("jeesite.session.id");
        return simpleCookie;
    }

//    @Bean
//    public DefaultWebSessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        sessionManager.setGlobalSessionTimeout(tomcatTimeout*1000);
//        //设置sessionDao对session查询，在查询在线用户service中用到了
//        sessionManager.setSessionDAO(sessionDAO());
//        //配置session的监听
//        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
//        listeners.add(new BDSessionListener());
//        sessionManager.setSessionListeners(listeners);
//        //设置在cookie中的sessionId名称
//        sessionManager.setSessionIdCookie(simpleCookie());
//        return sessionManager;
//    }

    @Bean //提供对thymeleaf模板引擎的页面中的shiro自定义标签的支持
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
