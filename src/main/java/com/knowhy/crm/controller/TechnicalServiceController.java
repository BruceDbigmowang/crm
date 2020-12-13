package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.TaskService;
import com.knowhy.crm.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TechnicalServiceController {
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    ServerLogDAO serverLogDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    ContractRecordDAO contractRecordDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    IUserDAO iUserDAO;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/saveServiceLog")
    @Transactional
    public String saveLog(String salePlanID , String title , String content , String problem , String support , String nextPlan , String serviceDate , HttpSession session){
        ServerLog serverLog = new ServerLog();
        if(salePlanID == null || "".equals(salePlanID)){
            return "程序出错，未获取客户信息";
        }else{
            serverLog.setSalePlanID(salePlanID);
        }
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        serverLog.setCustomerCode(salesPlan.getCustomerCode());
        serverLog.setCustomerName(salesPlan.getCustomerName());
        if(title == null || "".equals(title)){
            return "请填写标题";
        }else{
            serverLog.setTitle(title);
        }
        if(content == null || "".equals(content)){
            return "请填写服务内容";
        }else{
            serverLog.setMainContent(content);
        }
        if(problem != null && !"".equals(problem)){
            serverLog.setProblem(problem);
        }
        if(support != null && !"".equals(support)){
            serverLog.setSupport(support);
        }
        if(nextPlan == null && "".equals(nextPlan)){
            return "请填写下步计划";
        }else{
            serverLog.setNextPlan(nextPlan);
        }
        if(serviceDate == null || "".equals(serviceDate)){
            return "请选择服务日期";
        }else{
            try{
                LocalDate date = LocalDate.parse(serviceDate , fmt);
                serverLog.setServiceDate(date);
            }catch (Exception e){
                return "服务日期格式出错";
            }
        }
        IUser user = (IUser)session.getAttribute("user");
        serverLog.setCreaterAccount(user.getAccount());
        serverLog.setCreaterName(user.getName());
        serverLog.setCreateDate(LocalDate.now());

        salesPlan.setServiceWrite("C");
        salesPlanDAO.save(salesPlan);
        serverLogDAO.save(serverLog);
        taskService.deleteTargetTecTask(salePlanID);
//        taskService.deleteTargetReportTask(salePlanID);

//        ContractRecord contractRecord = contractRecordDAO.findBySalePlanID(salePlanID).get(0);
//        int cycle = contractRecord.getServerAsk();
//
//        LocalDate deadline = contractRecord.getDeadline();
//        LocalDate nextTime = TimeUtil.getTechnicalLastTime(cycle);
//
//        taskDAO.deleteBySalePlanIDAndJobName(salePlanID , "技术服务");
//        taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , "技术服务");
//
//        if(deadline.isAfter(nextTime)){
//            Task task2 = new Task();
//            task2.setSalePlanID(salesPlan.getId());
//            task2.setCustomerCode(salesPlan.getCustomerCode());
//            task2.setCustomerName(salesPlan.getCustomerName());
//            task2.setPrincipal(salesPlan.getAssistant());
//            task2.setJobName("技术服务");
//            task2.setDescription("二级预警提醒");
//            task2.setJobLevel(2);
//            task2.setDeadline(TimeUtil.getTechnicalFirstTime(cycle));
//            if(cycle > 1){
//                task2.setRemainTime(30);
//            }else{
//                task2.setRemainTime(14);
//            }
//            task2.setReceiver(salesPlan.getOperator());
//            task2.setExceedTime(0);
//            task2.setCreater(user.getAccount());
//            task2.setCreateDate(LocalDate.now());
//            task2.setAuthority("sb");
//            taskDAO.save(task2);
//
//            Task task3 = new Task();
//            task3.setSalePlanID(salesPlan.getId());
//            task3.setCustomerCode(salesPlan.getCustomerCode());
//            task3.setCustomerName(salesPlan.getCustomerName());
//            task3.setPrincipal(salesPlan.getAssistant());
//            task3.setJobName("技术服务");
//            task3.setDescription("一级预警提醒");
//            task3.setJobLevel(1);
//            task3.setDeadline(TimeUtil.getTechnicalFirstTime(cycle));
//            if(cycle > 1){
//                task3.setRemainTime(14);
//            }else{
//                task3.setRemainTime(7);
//            }
//            task3.setReceiver(salesPlan.getOperator());
//            task3.setExceedTime(0);
//            task3.setCreater(user.getAccount());
//            task3.setCreateDate(LocalDate.now());
//            task3.setAuthority("sb");
//            taskDAO.save(task3);
//
//            taskService.saveTaskForOperator(salePlanID , "技术服务" , "foreTechnicalService", TimeUtil.getTechnicalLastTime(cycle) , 3);
//        }

        return "OK";
    }

    @RequestMapping("/getTecRecord")
    public Map<String , Object> getRecordList(String salePlanID){
        Map<String , Object>map = new HashMap<>();
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        String operatorAccount = salesPlan.getOperator();
        String operatorName = iUserDAO.getOne(operatorAccount).getName();
        salesPlan.setOperator(operatorName);
        map.put("salePlan" , salesPlan);
        List<ServerLog> serverLogList = serverLogDAO.findBySalePlanID(salePlanID);
        map.put("logs" , serverLogList);
        return map;
    }

    @RequestMapping("/getTechnicalById")
    public ServerLog getOneByID(int tId){
        ServerLog serverLog = serverLogDAO.getOne(tId);
        return serverLog;
    }
}
