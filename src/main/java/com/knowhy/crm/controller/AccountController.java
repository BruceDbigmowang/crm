package com.knowhy.crm.controller;

import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.service.*;
import com.knowhy.crm.util.MD5Utils;
import com.knowhy.crm.util.Result;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.Path;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SalePlanService salePlanService;



    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

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
                        IUser user = (IUser)subject.getPrincipal();
                        subject.getSession().setAttribute("user",user);
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

                return Result.success("重置成功");
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
        if(iUser == null){
            return "redirect:loginPage";
        }
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
        String account = iUser.getAccount();
        IUser user = userService.findUserByAccount(account);
        Map<String , Object>map = new HashMap<>();
        map.put("user" , user);
        return map;
    }

    @RequestMapping("/savePersonInfo")
    @ResponseBody
    public String saveInfo(@RequestParam("account")String account , @RequestParam("name")String name , @RequestParam("company")String company ,@RequestParam("wechatNum")String wechatNum,
                        @RequestParam("email")String email , @RequestParam("dept")String dept , @RequestParam("job")String job , @RequestParam("qqNum")String qqNumber , @RequestParam("sex")String sex){
        IUser iUser = userService.findUserByAccount(account);
        if(!name.isEmpty()){
            iUser.setName(name);
        }
        if(!company.isEmpty() && !iUser.getCompany().equals(company)){
            iUser.setCompany(company);
        }

        if(sex != null && !"".equals(sex)){
            iUser.setSex(sex.trim());
        }

        if(!email.isEmpty() && !iUser.getEmail().trim().equals(email.trim())){
            if(userService.emailUsed(email)){
                return "该邮箱已被注册";
            }else{
                iUser.setEmail(email);
            }
        }
        if(dept != null && !"".equals(dept)){
            iUser.setDept(dept);
        }
        if(job != null && !"".equals(job)){
            iUser.setJob(job);
        }
        if(qqNumber != null && !"".equals(qqNumber)){
            iUser.setQqNum(qqNumber);
        }
        if(wechatNum != null && !"".equals(wechatNum)){
            iUser.setWechatNum(wechatNum);
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
        return "OK";
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
    public String newAccountRegister(HttpSession session , @RequestParam("account")String account , @RequestParam("email")String email , @RequestParam("phone")String phone , @RequestParam("vcode")String vcode , @RequestParam("company")String company , @RequestParam("password")String password , @RequestParam("identity")int identity){
        String sessionVcode = (String)session.getAttribute("code");
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

                               Customer customer = new Customer();
                               String customerID = "knowhy"+sdf2.format(new Date());
                               List<Customer> customerList = customerDAO.findByCreateDate(LocalDate.now());
                               int customerLength = customerList.size() + 1;
                               String customers = String.valueOf(customerLength);
                               for(int i = customers.length() ; i < 4 ; i++){
                                   customers = "0"+customers;
                               }
                               customerID = customerID + customers;

                               customer.setId(customerID);
                               customer.setName(company);
                               customer.setPrincipal(account);
                               customer.setPhone(phone);
                               customer.setEmail(email);
                               customer.setCreater(account);
                               customer.setCreaterName("客户"+account);
                               customer.setCreateDate(LocalDate.now());
                               customer.setFollowStatus("O");
                               customerDAO.save(customer);

                               //创建销售计划

                               SalesPlan salesPlan= new SalesPlan();
                               String salePlanId = salePlanService.generateNumber();
                               salesPlan.setId(salePlanId);
                               salesPlan.setCustomerCode(customerID);
                               salesPlan.setCustomerName(company);
                               salesPlan.setCreater(account);
                               salesPlan.setCreaterName("客户"+account);
                               salesPlan.setDescribe("客户"+account+"创建该订单");
                               salesPlan.setPlanStatus("first");
                               salePlanService.createSalePlan(salesPlan);

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

    @RequestMapping("/sureIdentity")
    @ResponseBody
    public int getIdentity(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        return userService.getIdentity(account);
    }

    @RequestMapping("/onlyChangePhone")
    @ResponseBody
    public String changephone(HttpSession session , String phone){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        if(userService.phoneUsed(phone)){
            return "该手机号已被使用";
        }
        try{
            IUser person = userService.findUserByAccount(account);
            person.setPhone(phone);
            userService.updatePassword(person);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/onlyChangePassword")
    @ResponseBody
    public String changePassword(HttpSession session , String password){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        try{
            IUser person = userService.findUserByAccount(account);
            password = MD5Utils.addMD5(password).toString();
            person.setPassword(password);
            userService.updatePassword(person);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/getHeadPic")
    @ResponseBody
    public String getPicName(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        IUser person = userService.findUserByAccount(account);
        String headPic = person.getHeadPic();
        if(headPic == null || "".equals(headPic)){
            return "NO";
        }else{
            if("".equals(headPic.trim())){
                return "NO";
            }else{
                return headPic;
            }
        }
    }

    @RequestMapping("/headPicExist")
    @ResponseBody
    public String picExist(HttpServletRequest request , String picName){
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        String url_path = "image" + File.separator +"headPic";
        String path = staticPath + File.separator + url_path;
        File file = new File(path, picName);
        if (!file.getParentFile().exists()) {
            return "NO";
        }else{
            return "have";
        }
    }

    @RequestMapping("/uploadHeadPic")
    @ResponseBody
    public String saveUploadPic(@RequestParam("demo") MultipartFile file , HttpServletRequest request , HttpSession session) throws IOException {
        String filename ;
        if (!file.isEmpty()) {
            filename = file.getOriginalFilename();

            String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            String url_path = "image" + File.separator +"headPic";
            String path = staticPath + File.separator + url_path;
            System.out.println("路径："+path);

            File filepath = new File(path, filename);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            file.transferTo(new File(path + File.separator + filename));
            try{
                IUser user = (IUser)session.getAttribute("user");
                String account = user.getAccount();
                IUser person = userService.findUserByAccount(account);
                person.setHeadPic(filename);
                userService.updatePassword(person);
            }catch (Exception e){
                return e.getMessage();
            }
        }
        return "OK";
    }
}
