package com.knowhy.crm.controller;

import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.dao.SecretFileDAO;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.pojo.SecretFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;

@RestController
public class SecretFileController {
    @Autowired
    SecretFileDAO secretFileDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;

    @RequestMapping("/saveSecretFile")
    @Transactional
    public String saveSecret(String salePlanID , String fileName , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String maker = user.getAccount();
        String makerName = user.getName();

        if(salePlanID == null || "".equals(salePlanID)){
            return "程序出错";
        }
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        String customerCode = salesPlan.getCustomerCode();
        String customerName = salesPlan.getCustomerName();
        salesPlan.setPlanStatus("third");

        SecretFile file = new SecretFile();
        file.setSalePlanId(salePlanID);
        file.setCustomerCode(customerCode);
        file.setCustomerName(customerName);
        file.setFileName(fileName);
        file.setMaker(maker);
        file.setMakerName(makerName);
        file.setMakeDate(LocalDate.now());
        try{
            salesPlanDAO.save(salesPlan);
            secretFileDAO.save(file);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

}
