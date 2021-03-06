package com.knowhy.crm.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.knowhy.crm.shiro.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
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
        perms.put("/homePage" , "authc");
        perms.put("/foreCustomerManage" , "authc");
        perms.put("/forePool" , "authc");
        perms.put("/foreIntroduce" , "authc");
        perms.put("/foreSecret" , "authc");
        perms.put("/foreSurveyOnline" , "authc");
        perms.put("/foreAccountInner" , "authc");
        perms.put("/foreScheme" , "authc");
        perms.put("/foreContractPrevious" , "authc");
        perms.put("/foreContractLater" , "authc");
        perms.put("/foreSurveyOffline" , "authc");
        perms.put("/foreCustomerInfo" , "authc");
        perms.put("/foreAddFile" , "authc");
        perms.put("/foreExtraFile" , "authc");
        perms.put("/foreContractSum" , "authc");
        perms.put("/foreSchedule" , "authc");
        perms.put("/foreOperate" , "authc");
        perms.put("/foreAddOpportunity" , "authc");
        perms.put("/foreUpdateOpportunity" , "authc");
        perms.put("/foreMarketingOnline" , "authc");
        perms.put("/foreMarketingOffline" , "authc");
        perms.put("/foreMarketSpend" , "authc");
        perms.put("/foreTechnicalService" , "authc");
        perms.put("/foreMonthlyReport" , "authc");
        perms.put("/foreAllRoles" , "authc");
        perms.put("/foreArrangeSale" , "authc");
        perms.put("/foreContractManage" , "authc");
        perms.put("/foreAddressManage" , "authc");
        perms.put("/foreSatisfaction" , "authc");
        perms.put("/foreAllArea" , "authc");
        perms.put("/foreUserCenter" , "authc");

        shiroFilterFactoryBean.setLoginUrl("loginPage");
        shiroFilterFactoryBean.setUnauthorizedUrl("loginPage");
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
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
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
