package com.knowhy.crm.controller;

import com.knowhy.crm.dao.CustomerDetailDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.CustomerDetail;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class IntroduceController {

    @Autowired
    CustomerDetailDAO customerDetailDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;

    /**
     * 该阶段 以进一步收集填写客户信息为主
     * 若数据填写并保存成功 则进入下一阶段
     * 主要任务：
     * 1、填写并保存客户信息
     * 2、修改销售订单的状态，进入下一阶段
     * @return
     */
    @RequestMapping("/saveIntroduceTask")
    @Transactional
    public String saveIntroduce(String salePlanID , String address , String condition , String bussinessNature , String product , String competitor , String useAmount , String contract , String phone , String email , String wechat ,
                                String sex , String position , String character , String hobby , String relation , String birthday , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String maker = user.getAccount();
        String makerName = user.getName();
        CustomerDetail customer = new CustomerDetail();
        if(salePlanID == null || "".equals(salePlanID)){
            return "程序出错";
        }else{
            customer.setSalePlanID(salePlanID);
        }
        SalesPlan plan = salesPlanDAO.getOne(salePlanID);
        String customerCode = plan.getCustomerCode();
        String customerName = plan.getCustomerName();
        customer.setCustomerID(customerCode);
        customer.setCustomerName(customerName);

        if(address == null || "".equals(address)){
            return "请输入客户地址";
        }else{
            customer.setAddress(address);
        }
        if(condition == null || "".equals(condition)){
            return "请输入基本情况";
        }else{
            customer.setCondition(condition);
        }
        if(bussinessNature != null && !"".equals(bussinessNature)){
            customer.setNature(bussinessNature);
        }
        if(product != null && !"".equals(product)){
            customer.setProduct(product);
        }
        if(competitor != null && !"".equals(competitor)){
            customer.setCompetitor(competitor);
        }
        if(useAmount != null && !"".equals(useAmount)){
            try{
                BigDecimal used = new BigDecimal(useAmount).setScale(4,BigDecimal.ROUND_HALF_UP);
                customer.setUseAmount(used);
            }catch (Exception e){
                return "年刀具用量输入错误（只能填写数字）";
            }
        }
        if(contract == null || "".equals(contract)){
            return "请输入联系人";
        }else{
            customer.setContract(contract);
        }
        if(phone == null || "".equals(phone)){
            return "请输入联系人电话";
        }else{
            customer.setPhone(phone);
        }
        if(email == null || "".equals(email)){
            return "请输入邮箱";
        }else{
            customer.setEmail(email);
        }
        if(wechat != null && !"".equals(wechat)){
            customer.setWechat(wechat);
        }
        if(sex != null && !"0".equals(sex)){
            customer.setSex(sex);
        }
        if(position == null || "".equals(position)){
            return "请输入职位";
        }else{
            customer.setPosition(position);
        }
        if(character != null && !"".equals(character)){
            customer.setCharacter(character);
        }
        if(hobby != null && !"".equals(hobby)){
            customer.setHobby(hobby);
        }
        if(relation != null && !"".equals(relation)){
            customer.setRelation(relation);
        }
        if(birthday != null && !"".equals(birthday)){
            LocalDate birth = LocalDate.parse(birthday , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            customer.setBirthday(birth);
        }
        customer.setMakeDate(LocalDate.now());
        customer.setMaker(maker);
        customer.setMakerName(makerName);
        try{
            customerDetailDAO.save(customer);
            plan.setPlanStatus("second");
            salesPlanDAO.save(plan);
        }catch (Exception e){
            return e.getMessage();
        }

        return "OK";
    }
}
