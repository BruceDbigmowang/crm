package com.knowhy.crm.controller;

import com.knowhy.crm.service.CompanyInfoService;
import com.knowhy.crm.service.RoleService;
import com.knowhy.crm.service.UserService;
import com.knowhy.crm.util.MD5Utils;
import com.knowhy.crm.util.Result;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.Path;
import com.knowhy.crm.service.PathService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    CompanyInfoService companyInfoService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PathService pathService;



    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //登录
    @RequestMapping("/doLogin")
    @ResponseBody
    public Object dologin(@RequestBody Map<String,String> userData, Model model , HttpSession session){

        String account = userData.get("name");
        System.out.println(account);
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
                        String operator = user.getAccount()+"("+user.getName()+")";
                        String action = "登录系统";
                        Date now = new Date();
                        String record = sdf.format(now);
                        Path path = new Path();
                        path.setOperator(operator);
                        path.setAction(action);
                        path.setRecord(record);
                        path.setCreateTime(now);
                        pathService.writeRecord(path);
                        //测试阶段，验证码为固定值111111
                        session.setAttribute("code" , "111111");
                        return Result.success();
                    }else{

                        return Result.fail("用户名或密码不正确!");
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                    return Result.fail("用户名或密码不正确!!!");
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
                        IUser user = new IUser();
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

                String operator = iUser.getAccount()+"("+iUser.getName()+")";
                String action = "重置密码";
                Date now = new Date();
                String record = sdf.format(now);
                Path path = new Path();
                path.setOperator(operator);
                path.setAction(action);
                path.setRecord(record);
                path.setCreateTime(now);
                pathService.writeRecord(path);

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
        IUser iUser = (IUser)subject.getSession().getAttribute("user");
        String operator = iUser.getAccount()+"("+iUser.getName()+")";
        String action = "退出系统";
        Date now = new Date();
        String record = sdf.format(now);
        Path path = new Path();
        path.setOperator(operator);
        path.setAction(action);
        path.setRecord(record);
        path.setCreateTime(now);
        pathService.writeRecord(path);
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

    @RequestMapping("/savePersonInfo")
    @ResponseBody
    public String saveInfo(@RequestParam("account")String account , @RequestParam("name")String name , @RequestParam("company")String company ,
                        @RequestParam("phone")String phone , @RequestParam("email")String email , @RequestParam("password")String password){
        IUser iUser = userService.findUserByAccount(account);
        if(!name.isEmpty()){
            iUser.setName(name);
        }
        if(!company.isEmpty() && !iUser.getCompany().equals(company)){
            iUser.setCompany(company);
        }
        System.out.println(iUser.getPhone().trim());
        System.out.println(phone.trim());
        if(!phone.isEmpty() && !iUser.getPhone().trim().equals(phone.trim())){
            if(userService.phoneUsed(phone)){
                return "该手机号已被注册";
            }else {
                iUser.setPhone(phone);
            }
        }
        if(!email.isEmpty() && !iUser.getEmail().trim().equals(email.trim())){
            if(userService.emailUsed(email)){
                return "该邮箱已被注册";
            }else{
                iUser.setEmail(email);
            }
//            iUser.setEmail(email);
        }
        System.out.println(password);
        if (!password.isEmpty()){
            iUser.setPassword(MD5Utils.addMD5(password).toString());
        }

        try{
            userService.updatePassword(iUser);

            String operator = iUser.getAccount()+"("+iUser.getName()+")";
            String action = "修改个人信息";
            Date now = new Date();
            String record = sdf.format(now);
            Path path = new Path();
            path.setOperator(operator);
            path.setAction(action);
            path.setRecord(record);
            path.setCreateTime(now);
            pathService.writeRecord(path);
        }catch (Exception e){
            return e.getMessage();
        }
        return "数据保存成功";
    }

    @RequestMapping("/addAccount")
    @ResponseBody
    public String addNewAccount(@RequestParam("account") String userAccount , @RequestParam("password") String userPassword , @RequestParam("email") String userEmail , @RequestParam("phone") String userPhone , @RequestParam("company") String userCompany , @RequestParam("roles[]") int[] userRoleCheck){
        if(!userService.AccountExist(userAccount)){
            if(userService.phoneUsed(userPhone)){
                return "该手机号已被注册";
            }else{
                if(!userCompany.isEmpty()){
                    if(companyInfoService.completeSurvey(userCompany) == 0){
                        return "您还未完成在线尽调";
                    }else{
                        IUser user = new IUser();
                        user.setAccount(userAccount);

                        userPassword = MD5Utils.addMD5(userPassword).toString();
                        user.setName("用户"+userAccount);
                        user.setPassword(userPassword);
                        user.setSalt("abc");
                        user.setPhone(userPhone);
                        user.setCompany(userCompany);
                        user.setCreateTime(new Date());
                        user.setStatus("O");
                        user.setEmail(userEmail);

                        userService.createAccount(user);
                        for(int i = 0 ; i < userRoleCheck.length ; i++){
                            userService.setRole(userAccount , userRoleCheck[i]);
                        }
                        return "注册成功";
                    }
                }else{
                    IUser user = new IUser();
                    user.setAccount(userAccount);

                    userPassword = MD5Utils.addMD5(userPassword).toString();
                    user.setName("用户"+userAccount);
                    user.setPassword(userPassword);
                    user.setSalt("abc");
                    user.setPhone(userPhone);
                    user.setCreateTime(new Date());
                    user.setStatus("O");
                    user.setEmail(userEmail);

                    userService.createAccount(user);
                    for(int i = 0 ; i < userRoleCheck.length ; i++){
                        userService.setRole(userAccount , userRoleCheck[i]);
                    }
                    return "注册成功";
                }
            }
        }else{
            return "该账号已被使用";
        }
    }

    @RequestMapping("/registerNewAccount")
    @Transactional
    @ResponseBody
    public String newAccountRegister(@RequestParam("account")String account , @RequestParam("email")String email , @RequestParam("phone")String phone , @RequestParam("vcode")String vcode , @RequestParam("company")String company , @RequestParam("password")String password , @RequestParam("identity")int identity){
        String sessionVcode = "111111";
        if(!vcode.equals(sessionVcode)){
            return "验证码填写错误";
        }else{
           if(userService.AccountExist(account)){
               return "该账号已被注册";
           } else{
               if(userService.phoneUsed(phone)){
                   return "该手机号已被使用";
               }else{
                   if(roleService.isCustomer(identity)){
                       if("".equals(company)){
                           return "公司未填写";
                       }else{
                           /**
                            * 注册客户
                            */
                           try{
                               IUser user = new IUser();
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
                               userService.setRole(account , identity);
                           }catch (Exception e){
                               return e.getMessage();
                           }
                           return "OK";
                       }
                   }else{
                       /**
                        * 注册非客户
                        */
                       try{
                           IUser user = new IUser();
                           user.setAccount(account);
                           password = MD5Utils.addMD5(password).toString();
                           user.setName("用户"+account);
                           user.setPassword(password);
                           user.setSalt("abc");
                           user.setPhone(phone);
//                       user.setCompany(company);
                           user.setCreateTime(new Date());
                           user.setStatus("O");
                           user.setEmail(email);
                           userService.createAccount(user);
                           userService.setRole(account , identity);
                       }catch (Exception e){
                           return e.getMessage();
                       }
                        return "OK";
                   }
               }
           }
        }
    }

    /**
     *登录进入主页后
     * 首先判断登录账号的角色是否是客户角色
     * 若是客户角色，判断是否完成在线尽调
     * 当中包含未做在线尽调、完成部分以及完成在线尽调
     */
    @RequestMapping("/checkCompleteSurvey")
    @ResponseBody
    public int checkSurvey(HttpSession session){
        IUser iUser = (IUser)session.getAttribute("user");
        String account = iUser.getAccount();
        String company = iUser.getCompany();
        if(userService.sureCustomer(account)){
            int result = companyInfoService.sureSurvey(company);
            if(result != 1 && result != 9){
                int cid = companyInfoService.getCid(company);
                session.setAttribute("cid" , cid);
            }
            return result;
        }else{
            return 9;
        }
    }

}
