package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.dao.SaleManDAO;
import com.knowhy.crm.entity.CrmCustomer;
import com.knowhy.crm.mapper.CrmCustomerMapper;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.service.CustomerService;
import com.knowhy.crm.service.SalePlanService;
import com.knowhy.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Resource
    CrmCustomerMapper crmCustomerMapper;
    @Autowired
    SaleManDAO saleManDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SalePlanService salePlanService;
    @Autowired
    UserService userService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    @RequestMapping("/saveCustomer")
    @Transactional
    public String saveInfo(HttpSession session , String customerName ,String customerType , String area , String decisionMaker , String decisionPhone , String principal , String phone ,
                           String superior , String email , String wechat , String industry ,  String companyAddress ,String saleAmount ,  String useAmount ){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        String makerName = userService.getUserName(account);
        Customer customer = new Customer();
        customer.setCreater(account);
        customer.setCreaterName(makerName);
        customer.setCreateDate(LocalDate.now());
        customer.setFollowStatus("O");

        //系统自动生成客户编号
        String customerID = "knowhy"+sdf2.format(new Date());
        List<Customer> customerList = customerDAO.findByCreateDate(LocalDate.now());
        int customerLength = customerList.size() + 1;
        String customers = String.valueOf(customerLength);
        for(int i = customers.length() ; i < 4 ; i++){
            customers = "0"+customers;
        }
        customerID = customerID + customers;

        customer.setId(customerID);


        if(customerName == null || "".equals(customerName)){
            return "请填写客户名称";
        }else{
            customer.setName(customerName);
        }

        if(customerType != null && !"0".equals(customerType)){
            customer.setCustomerType(customerType);
        }else{
            return "请选择客户类型";
        }

        if(area == null || "0".equals(area)){
            return "请选择地区";
        }else{
            customer.setArea(area);
        }

        if(decisionMaker == null || "".equals(decisionMaker)){
            return "请填写决策者";
        }else{
            customer.setDecisionMaker(decisionMaker);
        }
        if(decisionPhone == null || "".equals(decisionPhone)){
            return "请填写决策者联系方式";
        }else{
            customer.setDecisionPhone(decisionPhone);
        }
        if(principal != null && !"".equals(principal)){
            customer.setPrincipal(principal);
        }else{
            return "请填写项目负责人";
        }
        if(phone != null && !"".equals(phone)){
            customer.setPhone(phone);
        }else{
            return "请填写项目负责人联系方式";
        }

        if(superior != null && !"".equals(superior)){
            customer.setSuperiorCustomer(superior);
        }
        if(email != null && !"".equals(email)){
            if(isEmail(email)){
                customer.setEmail(email);
            }else{
                return "邮箱格式不正确";
            }
        }
        if(wechat != null && !"".equals(wechat)){
            customer.setWechat(wechat);
        }
        if(industry != null && !"".equals(industry)){
            customer.setIndustry(industry);
        }
        if(companyAddress != null && !"".equals(companyAddress)){
            customer.setCompanyAddress(companyAddress);
        }

        if(saleAmount != null && !"".equals(saleAmount)){
            try{
                BigDecimal sale = new BigDecimal(saleAmount).setScale(4,BigDecimal.ROUND_HALF_UP);
                customer.setSaleAmount(sale);
            }catch (Exception e){
                return "年度销售额填写错误（只能填写数字）";
            }
        }

        if(useAmount != null && !"".equals(useAmount)){
            try{
                BigDecimal use = new BigDecimal(useAmount).setScale(4 , BigDecimal.ROUND_HALF_UP);
                customer.setUseAmount(use);
            }catch (Exception e){
                return "年度刀具用量填写错误（只能填写数字）";
            }
        }
        try{
            customerService.saveCustomer(customer);
        }catch (Exception e){
            return e.getMessage();
        }

        //创建销售计划

        SalesPlan salesPlan= new SalesPlan();
        String salePlanId = salePlanService.generateNumber();
        salesPlan.setId(salePlanId);
        salesPlan.setCustomerCode(customerID);
        salesPlan.setCustomerName(customerName);
        salesPlan.setCreater(account);
        salesPlan.setCreaterName(makerName);
        salesPlan.setDescribe(makerName+"创建客户");
        salesPlan.setPlanStatus("first");
        salesPlan.setMakeDate(LocalDate.now());
        salePlanService.createSalePlan(salesPlan);
        return "客户创建成功";
    }

    @RequestMapping("/getAllCustomer")
    public List<Customer> finAll(){
        return customerService.getAllCustomer();
    }

    @RequestMapping("/selectCustomerByPage")
    public Map<String , Object> getCustomerByPage(@RequestParam("start")int start){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<CrmCustomer> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("createDate");
        Page<CrmCustomer> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmCustomer> iPage = crmCustomerMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }

        List<CrmCustomer> crmCustomerList = iPage.getRecords();

        map.put("customers", crmCustomerList);
        map.put("pages" , size);
        return map;
    }
    @RequestMapping("/selectCustomerByPageAndCondition")
    public Map<String , Object> getCustomerByPageCondition(@RequestParam("start")int start){
        Map<String,Object> map = new HashMap<>();

        QueryWrapper<CrmCustomer> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("createDate");
        queryWrapper.eq("followStatus" , "O");
        Page<CrmCustomer> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmCustomer> iPage = crmCustomerMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }

        List<CrmCustomer> crmCustomerList = iPage.getRecords();

        map.put("customers", crmCustomerList);
        map.put("pages" , size);
        return map;
    }

    @RequestMapping("/getTodayCustomer")
    public Object getToday(String start , String end){
        Map<String , Object> map = new HashMap<>();
        try{
            System.out.println(start);
            LocalDate startTime = LocalDate.parse(start , fmt);
            System.out.println(startTime);
            System.out.println(end);
            LocalDate endTime = LocalDate.parse(end , fmt);
            System.out.println(endTime);
            List<Customer> customerList = customerService.getTodayCustomer(startTime , endTime);
            map.put("result","OK");
            map.put("customers" , customerList);
        } catch (Exception e) {
            map.put("result",e.getMessage());
        }
        return map;
    }

    @RequestMapping("/findCustomerByName")
    public Map<String  , Object> findByNameLike(String name){
        Map<String , Object> map = new HashMap<>();
        List<Customer> customerList = customerService.getCustomerByName(name);
        map.put("customers" , customerList);
        return map;
    }

    @RequestMapping("/findCustomerByPhone")
    public Map<String  , Object> findByPhoneLike(String phone){
        Map<String , Object> map = new HashMap<>();
        List<Customer> customerList = customerService.getCustomerByPhone(phone);
        map.put("customers" , customerList);
        return map;
    }

    @RequestMapping("/findByCistomerName")
    public List<Customer> findByName(String customerName){
        customerName = "%"+customerName+"%";
        return customerDAO.findByNameLike(customerName);
    }

}