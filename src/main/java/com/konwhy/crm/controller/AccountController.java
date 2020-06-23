package com.konwhy.crm.controller;

import com.konwhy.crm.pojo.IUser;
import com.konwhy.crm.service.CompanyInfoService;
import com.konwhy.crm.service.UserService;
import com.konwhy.crm.util.MD5Utils;
import com.konwhy.crm.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    CompanyInfoService companyInfoService;
    @Autowired
    UserService userService;

    //登录
    @RequestMapping("/doLogin")
    @ResponseBody
    public Object dologin(@RequestBody Map<String,String> userData, Model model , HttpSession session){

        String account = userData.get("name");
        String password = userData.get("password");
        String validateCode = userData.get("validateCode");

        if(!"".equals(account) && account != null && !"".equals(password) && password != null && validateCode != null && !"".equals(validateCode)){
            String vcode2 = (String)session.getAttribute("randCheckCode");
            if(validateCode.equals(vcode2)){
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(account , password);
                try{
                    subject.login(token);
                    boolean flag = subject.isAuthenticated();
                    if(flag){
                        System.out.println("登录成功！");
                        //获取当前用对象，放入到session中
                        IUser user = (IUser)subject.getPrincipal();
                        subject.getSession().setAttribute("user",user);
//                        IUser person = (IUser)session.getAttribute("user");
//                        System.out.println(person.getAccount());
                        return Result.success();
                    }else{

                        return Result.fail("用户名或密码不正确!");
                    }
                }catch (Exception e){
                    return Result.fail("用户名或密码不正确!");
                }
            }else{
                return Result.fail("验证码不正确!");
            }
        }else{
            return Result.fail("账号、密码、验证码均不能为空!");
        }
    }

    //客户注册
    @RequestMapping("/customerRegister")
    @ResponseBody
    public Object registe(@RequestBody Map<String,String> customerData, Model model){

        String account = customerData.get("name");
        String password = customerData.get("password");
        String phone = customerData.get("phone");
        String email = customerData.get("email");
        String company = customerData.get("company");

        if("".equals(account) || account == null || "".equals(company) || company == null || "".equals(phone) || phone == null ||"".equals(password) || password == null
        ||"".equals(email) || email == null){
            return Result.fail("信息未填写完整");
        }else{
            //判断注册的账号是否被占用
            if(userService.AccountExist(account)== false){
                if(companyInfoService.completeSurvey(company) == 0){
                    return Result.fail("您还未完成在线尽调");
                }else{
                    if(userService.phoneUsed(phone)){
                        return Result.fail("该手机号已被注册");
                    }else{
                        IUser user = new IUser();
                        /**
                         * 生成账号
                         */
//                        int total = userService.getAll()+1;
//                        String target = String.valueOf(total);
//                        String account = "";
//                        if(target.length() < 6){
//                            int num = 6 - target.length();
//
//                            for(int i = 0 ; i < num ; i++){
//                                account = account + "0";
//                            }
//                            account = account + target;
//                        }else{
//                            account = target;
//                        }

                        user.setAccount(account);

                        password = MD5Utils.addMD5(password).toString();
                        user.setName("用户"+account);
                        user.setPassword(password);
                        user.setSalt("abc");
                        user.setPhone(phone);
                        user.setCompany(company);
                        user.setCreateTime(new Date());
                        user.setStatus("O");
                        user.setEmail(email);

                        userService.createAccount(user);
                        userService.setRole(account , 2);

                        return Result.success("注册成功");

                    }
                }
            }else{
                return Result.fail("该账号已被注册");
            }
        }
    }

    //技术专家注册
    @RequestMapping("/expertRegister")
    @ResponseBody
    public Object registeExpert(@RequestBody Map<String,String> expertData, Model model){

        String account = expertData.get("name");
        String password = expertData.get("password");
        String phone = expertData.get("phone");
        String email = expertData.get("email");
        String company = expertData.get("company");

        if("".equals(account) || account == null || "".equals(phone) || phone == null ||"".equals(password) || password == null ||
                "".equals(email) || email == null){
            return Result.fail("信息未填写完整");
        }else{
            //判断注册的账号是否被占用
            if(userService.AccountExist(account)== false){

                if(userService.phoneUsed(phone)){
                    return Result.fail("该手机号已被注册");
                }else{
                    IUser user = new IUser();
                    /**
                     * 生成账号
                     */
//                        int total = userService.getAll()+1;
//                        String target = String.valueOf(total);
//                        String account = "";
//                        if(target.length() < 6){
//                            int num = 6 - target.length();
//
//                            for(int i = 0 ; i < num ; i++){
//                                account = account + "0";
//                            }
//                            account = account + target;
//                        }else{
//                            account = target;
//                        }

                    user.setAccount(account);

                    password = MD5Utils.addMD5(password).toString();
                    user.setName("用户"+account);
                    user.setPassword(password);
                    user.setSalt("abc");
                    user.setPhone(phone);
                    if(company != null && !"".equals(company)){
                        user.setCompany(company);
                    }
                    user.setCreateTime(new Date());
                    user.setStatus("O");
                    user.setEmail(email);

                    userService.createAccount(user);
                    userService.setRole(account , 3);

                    return Result.fail("注册成功");

                }
            }else{
                return Result.fail("该账号已被注册");
            }
        }
    }

    //技术专家注册
    @RequestMapping("/agentRegister")
    @ResponseBody
    public Object registeAgent(@RequestBody Map<String,String> agentData, Model model){
        String account = agentData.get("name");
        String password = agentData.get("password");
        String phone = agentData.get("phone");
        String email = agentData.get("email");
        String company = agentData.get("company");

        if("".equals(account) || account == null || "".equals(phone) || phone == null ||"".equals(password) || password == null ||
                "".equals(email) || email == null){
            return Result.fail("信息未填写完整");
        }else{
            //判断注册的账号是否被占用
            if(userService.AccountExist(account)== false){

                if(userService.phoneUsed(phone)){
                    return Result.fail("该手机号已被注册");
                }else{
                    IUser user = new IUser();
                    /**
                     * 生成账号
                     */
//                        int total = userService.getAll()+1;
//                        String target = String.valueOf(total);
//                        String account = "";
//                        if(target.length() < 6){
//                            int num = 6 - target.length();
//
//                            for(int i = 0 ; i < num ; i++){
//                                account = account + "0";
//                            }
//                            account = account + target;
//                        }else{
//                            account = target;
//                        }

                    user.setAccount(account);

                    password = MD5Utils.addMD5(password).toString();
                    user.setPassword(password);
                    user.setName("用户"+account);
                    user.setSalt("abc");
                    user.setPhone(phone);
                    if(company != null && !"".equals(company)){
                        user.setCompany(company);
                    }
                    user.setCreateTime(new Date());
                    user.setStatus("O");
                    user.setEmail(email);

                    userService.createAccount(user);
                    userService.setRole(account , 3);

                    return Result.fail("注册成功");

                }
            }else{
                return Result.fail("该账号已被注册");
            }
        }
    }

    //重置密码
    @RequestMapping("/restPassword")
    @ResponseBody
    public Object rest(@RequestBody Map<String,String> resetPasswordData, Model model){

        String account = resetPasswordData.get("name");
        String password = resetPasswordData.get("password");
        String email = resetPasswordData.get("email");
        String password2 = resetPasswordData.get("confirmPwd");

        IUser iUser = userService.findByAccountAndEmail(account , email);
        if(iUser != null){
            if(password.equals(password2)){
                String newPass = MD5Utils.addMD5(password).toString();
                iUser.setPassword(newPass);
                userService.updatePassword(iUser);
                return Result.fail("重置成功");
            }else{
                return Result.fail("密码输入不一致");
            }
        }else{
            return Result.fail("账号或邮箱填写错误");
        }
    }

    //退出登录
    @RequestMapping("/logout")
    public String outSys(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated())
            subject.logout();
        return "redirect:loginPage";
    }

    @RequestMapping("/getPersonInfo")
    @ResponseBody
    public Object loadInfo(HttpSession session){

        IUser iUser = (IUser)session.getAttribute("user");
        System.out.println(iUser.getAccount());
        Map<String , Object>map = new HashMap<>();
        map.put("user" , iUser);
        return map;
    }

}
