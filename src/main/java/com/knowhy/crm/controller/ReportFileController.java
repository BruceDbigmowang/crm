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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ReportFileController {

    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    ReportFileDAO reportFileDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    ContractRecordDAO contractRecordDAO;

    @RequestMapping("/saveReportFile")
    @Transactional
    public String recordReport(String salePlanID , String fileName , String note , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        ReportFile reportFile = new ReportFile();
        if(salePlanID == null || "".equals(salePlanID)){
            return "程序出错，未获得客户信息";
        }else{
            reportFile.setSalePlanID(salePlanID);
        }
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        reportFile.setCustomerCode(salesPlan.getCustomerCode());
        reportFile.setCustomerName(salesPlan.getCustomerName());
        if(fileName == null || "".equals(fileName)){
            return "请选择汇报附件";
        }else{
            reportFile.setFileName(fileName);
        }
        if(note != null && !"".equals(note)){
            reportFile.setNote(note);
        }
        reportFile.setCreaterAccount(user.getAccount());
        reportFile.setCreaterName(user.getName());
        reportFile.setCreateDate(LocalDate.now());

        reportFileDAO.save(reportFile);

//        taskService.deleteTargetTecTask(salePlanID);
        taskService.deleteTargetReportTask(salePlanID);

        ContractRecord contractRecord = contractRecordDAO.findBySalePlanID(salePlanID).get(0);

        LocalDate deadline = contractRecord.getDeadline();
        LocalDate nextTime = TimeUtil.getReportTimeSecond();

        salesPlan.setReportWrite("C");
        salesPlanDAO.save(salesPlan);
        taskDAO.deleteBySalePlanIDAndJobName(salePlanID , "月度汇报");
        taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , "月度汇报");


        if(deadline.isAfter(nextTime)){
//            Task task4 = new Task();
//            task4.setSalePlanID(salesPlan.getId());
//            task4.setCustomerCode(salesPlan.getCustomerCode());
//            task4.setCustomerName(salesPlan.getCustomerName());
//            task4.setPrincipal(salesPlan.getAssistant());
//            task4.setJobName("月度汇报");
//            task4.setDescription("二级预警提醒");
//            task4.setJobLevel(2);
//            task4.setDeadline(TimeUtil.getReportTimeFirst());
//            task4.setRemainTime(7);
//            task4.setReceiver(salesPlan.getOperator());
//            task4.setExceedTime(0);
//            task4.setCreater(user.getAccount());
//            task4.setCreateDate(LocalDate.now());
//            task4.setAuthority("sb");
//            taskDAO.save(task4);
//
//            Task task5 = new Task();
//            task5.setSalePlanID(salesPlan.getId());
//            task5.setCustomerCode(salesPlan.getCustomerCode());
//            task5.setCustomerName(salesPlan.getCustomerName());
//            task5.setPrincipal(salesPlan.getAssistant());
//            task5.setJobName("月度汇报");
//            task5.setDescription("一级预警提醒");
//            task5.setJobLevel(1);
//            task5.setDeadline(TimeUtil.getReportTimeSecond());
//            task5.setRemainTime(0);
//            task5.setReceiver(salesPlan.getOperator());
//            task5.setExceedTime(0);
//            task5.setCreater(user.getAccount());
//            task5.setCreateDate(LocalDate.now());
//            task5.setAuthority("sb");
//            taskDAO.save(task5);
//
//            taskService.saveTaskForOperator(salePlanID , "月度汇报" , "foreMonthlyReport", TimeUtil.getReportTimeSecond() , 3);
        }else{
//            /**
//             * 当下一次的月度汇报时间超过合同截止日期
//             * 则销售订单关闭
//             * 之后无需再对该客户做技术服务以及月度汇报
//             * -------------------------------------
//             * 由于月度汇报是每个月都做，技术服务不一定是每个月都做
//             * 所以以月度汇报的下次任务时间与合同截止日期相比较判断是否需要关闭销售订单
//             */
            salesPlan.setPlanStatus("over");
            salesPlanDAO.save(salesPlan);
        }
        return "OK";
    }

    @RequestMapping("/showReportFileByCustomer")
    public Map<String , Object> getReportByCustomer(String salePlanID){
        Map<String , Object> map = new HashMap<>();
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        map.put("salePlan" , salesPlan);
        List<ReportFile> reportFileList = reportFileDAO.findBySalePlanID(salePlanID);
        map.put("files" , reportFileList);
        return map;
    }
}
