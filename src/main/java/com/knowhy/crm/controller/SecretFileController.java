package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.TaskService;
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
import java.util.List;

@RestController
public class SecretFileController {
    @Autowired
    SecretFileDAO secretFileDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    ArrangeRecordDAO arrangeRecordDAO;

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
        salesPlan.setSpendTime(7);
        salesPlan.setUpdateDate(LocalDate.now());
        salesPlan.setDeadline(LocalDate.now().plusDays(7));

        SecretFile file = new SecretFile();
        file.setSalePlanId(salePlanID);
        file.setCustomerCode(customerCode);
        file.setCustomerName(customerName);
        if(fileName == null || "".equals(fileName.trim())){
            return "请选择文件";
        }else{
            file.setFileName(fileName);
        }
        file.setMaker(maker);
        file.setMakerName(makerName);
        file.setMakeDate(LocalDate.now());
//        try{

        List<ArrangeRecord> arrangeRecordList = arrangeRecordDAO.findBySalePlanIDAndStepAndType(salePlanID , "保密协议签订" , "sale");
        if(!arrangeRecordList.isEmpty()){
            ArrangeRecord arrangeRecord = arrangeRecordList.get(0);
            arrangeRecord.setCompleteStatus("C");
            arrangeRecordDAO.save(arrangeRecord);
        }

            List<TaskSum> sumList = taskSumDAO.findBySalePlanIDAndTask(salePlanID , "在线尽调");
            salesPlan.setDeadline(sumList.get(0).getDeadline());
            secretFileDAO.save(file);
            taskDAO.deleteBySalePlanIDAndJobName(salePlanID , "保密协议签订");
            taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , "保密协议签订");
            salesPlanDAO.save(salesPlan);
//        }catch (Exception e){
//            return e.getMessage();
//        }
        return "OK";
    }

    @RequestMapping("/getSecretFile")
    public SecretFile getSecretFileById(String salePlanID){
        System.out.println(salePlanID);
        List<SecretFile> secretFileList = secretFileDAO.findBySalePlanId(salePlanID);
        if(!secretFileList.isEmpty()){
            return secretFileList.get(0);
        }else{
            return null;
        }
    }

}
