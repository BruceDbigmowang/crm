package com.knowhy.crm.service;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.ArrangeRecord;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.pojo.Task;
import com.knowhy.crm.pojo.TaskList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class OperateServerService {
    @Autowired
    TaskListDAO taskListDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    ArrangeRecordDAO arrangeRecordDAO;

    @Transactional
    public void saveTaskList(List<TaskList> tasks , String salePlanID , int spendTime , String creater , String createrName , LocalDate end , String operator){

        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        salesPlan.setPlanStatus("tenth");
        salesPlan.setSpendTime(spendTime);
        salesPlan.setUpdateDate(LocalDate.now());
        salesPlan.setDeadline(LocalDate.now().plusDays(spendTime));
        salesPlan.setOperateArrange("C");
        salesPlanDAO.save(salesPlan);

        taskDAO.deleteBySalePlanID(salePlanID);
        taskSumDAO.deleteBySalePlanID(salePlanID);

        for(TaskList task : tasks){
            taskListDAO.save(task);
            String taskName = task.getStepName();
            LocalDate deadline = task.getEdate();


            Task task2 = new Task();
            task2.setSalePlanID(salesPlan.getId());
            task2.setCustomerCode(salesPlan.getCustomerCode());
            task2.setCustomerName(salesPlan.getCustomerName());
            task2.setPrincipal(salesPlan.getAssistant());
            task2.setJobName("运营导入——"+taskName);
            task2.setDescription("一级预警提醒");
            task2.setJobLevel(1);
            task2.setDeadline(deadline.minusDays(1));
            task2.setRemainTime(1);
            task2.setReceiver(salesPlan.getAssistant());
            task2.setExceedTime(0);
            task2.setCreater(creater);
            task2.setCreateDate(LocalDate.now());
            task2.setAuthority("sb");
            taskDAO.save(task2);
            taskService.saveTaskForAss(salePlanID , "运营导入——"+taskName , "foreOperate", deadline , 3);

            ArrangeRecord record = new ArrangeRecord();
            record.setSalePlanID(salesPlan.getId());
            record.setCustomerName(salesPlan.getCustomerName());
            record.setStep("运营导入——"+taskName);
            record.setBdate(task.getBdate());

            record.setSpendTime(task.getEdate().toEpochDay() - task.getBdate().toEpochDay()+"天");
            record.setCreater(creater);
            record.setCreaterName(createrName);
            record.setCreateDate(LocalDate.now());
            record.setType("operate");
            arrangeRecordDAO.save(record);

        }
        //运营导入期间需要完善现场尽调阶段中所需要的收集的数据

//        TaskList taskList = new TaskList();
//        taskList.setSalePlanID(salePlanID);
//        taskList.setStepName("信息收集");
//        taskList.setCustomerTask("");
//        taskList.setSelfTask("现场尽调信息导入");
//        taskList.setBdate(LocalDate.now());
//        taskList.setEdate(end);
//        taskList.setSaleManAccount(creater);
//        taskList.setSaleManName(createrName);
//        taskList.setTaskStatus("O");
//        taskList.setOperatorAccount(operator);
//        taskListDAO.save(taskList);
    }
}
