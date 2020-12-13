package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class SaleArrangeController {
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    ArrangeRecordDAO arrangeRecordDAO;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("/getAllSaleArrange")
    public Map<String , Object> getAll(HttpSession session){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<SalesPlan> salesPlanList = salesPlanDAO.findByPrincipalAndSaleArrange(account , "O");
        List<SalesPlan> salesPlanList1 = salesPlanDAO.findByPrincipalAndSaleArrange(account , "C");
        map.put("notArrange" , salesPlanList);
        map.put("arranged" , salesPlanList1);
        return map;
    }

    @RequestMapping("/getAllSaleArrangeByAdmin")
    public Map<String , Object> getAllByAdmin(HttpSession session){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<SalesPlan> salesPlanList = salesPlanDAO.findBySaleArrange( "O");
        List<SalesPlan> salesPlanList1 = salesPlanDAO.findBySaleArrange( "C");
        map.put("notArrange" , salesPlanList);
        map.put("arranged" , salesPlanList1);
        return map;
    }

    @RequestMapping("/getAllOperateArrange")
    public Map<String , Object> getAllOperate(HttpSession session , String status){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<SalesPlan> salesPlanList = salesPlanDAO.findByPlanStatusAndAssistantAndOperateArrange(status , account , "O");
        List<SalesPlan> salesPlanList1 = salesPlanDAO.findByAssistantAndOperateArrange(account , "C");
        map.put("notArrange" , salesPlanList);
        map.put("arranged" , salesPlanList1);
        return map;
    }

    @RequestMapping("/getAllOperateArrangeByName")
    public Map<String , Object> getAllOperate(HttpSession session , String status , String customerName){
        customerName = "%"+customerName+"%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistantAndOperateArrange(customerName , status , account , "O");
        List<SalesPlan> salesPlanList1 = salesPlanDAO.findByCustomerNameLikeAndAssistantAndOperateArrange(customerName , account , "C");
        map.put("notArrange" , salesPlanList);
        map.put("arranged" , salesPlanList1);
        return map;
    }

    @RequestMapping("/showSalePlanByName")
    public Map<String , Object> getSalePlanByName(HttpSession session , String customerName){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        String name = "%"+customerName+"%";
        List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndSaleArrange(name , account , "O");
        List<SalesPlan> salesPlanList1 = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndSaleArrange(name , account , "C");
        map.put("notArrange" , salesPlanList);
        map.put("arranged" , salesPlanList1);
        return map;
    }

    @RequestMapping("/showSalePlanByNameAndAdmin")
    public Map<String , Object> getSalePlanByNameAndAdmin(HttpSession session , String customerName){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        String name = "%"+customerName+"%";
        List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndSaleArrange(name , "O");
        List<SalesPlan> salesPlanList1 = salesPlanDAO.findByCustomerNameLikeAndSaleArrange(name , "C");
        map.put("notArrange" , salesPlanList);
        map.put("arranged" , salesPlanList1);
        return map;
    }

    @RequestMapping("/showOnCalendar")
    public Map<String , Object> showCity(Date time , HttpSession session , String salePlanID){
        String targettime = sdf.format(time);
        IUser user = (IUser)session.getAttribute("user");
        Map<String , Object> map = new HashMap<>();
        LocalDate now = null;
        try{
            now = LocalDate.parse(targettime , fmt);
        }catch (Exception e){
            map.put("info" , "日期格式错误"+time);
            return map;
        }
        List<TaskSum> taskSumList = taskSumDAO.findByPrincipalAndBdate(user.getAccount() , now);
        List<TaskSum>taskSumList2 = taskSumList;
        for(TaskSum taskSum : taskSumList2){
            String id = taskSum.getSalePlanID();
            if(salePlanID.equals(id)){
                taskSumList.remove(taskSum);
            }
        }
        if(taskSumList == null || taskSumList.size() == 0){
            map.put("info" , "no");
        }else{
            String city = taskSumList.get(0).getCity();
            if(city != null){
                map.put("info" , "OK");
                map.put("city" , city);
                if(taskSumList.size() > 1){
                    String city2 = taskSumList.get(1).getCity();
                    if(city2 != null){
                        map.put("city2" , city2);
                    }else{
                        map.put("city2","");
                    }
                }else{
                    map.put("city2" , "");
                }
            }else{
                map.put("info" , "no");
            }
        }


        return map;
    }

    @RequestMapping("/saveSaleTask")
    @Transactional
    public String saveSaleArrange(String salePlanID , String[] bdates , HttpSession session){
        if(bdates.length != 7){
            return "请填写全部阶段的计划开始时间";
        }else{
            for(String bdate : bdates){
                if("".equals(bdate)){
                    return "请填写全部阶段的计划开始时间";
                }
            }
        }
        IUser user = (IUser)session.getAttribute("user");
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        String customerCode = salesPlan.getCustomerCode();
        Customer customer = customerDAO.getOne(customerCode);
        salesPlan.setSaleArrange("C");

        List<LocalDate> starts = new ArrayList<>();
        for(String bdate : bdates){
            try{
                LocalDate start = LocalDate.parse(bdate , fmt);
                starts.add(start);
            }catch (Exception e){
                return e.getMessage();
            }
        }

        for(int i = 0 ; i < 6 ; i++){
            LocalDate pervious = starts.get(i);
            LocalDate later = starts.get(i + 1);
            int tartget = i;
            if(pervious.isBefore(later)){

            }else{
                String result = "";
                switch (tartget){
                    case 0:
                        result = "保密协议签订时间应晚于介绍交流时间";
                        break;
                    case 1:
                        result = "在线尽调时间应晚于保密协议时间";
                        break;
                    case 2:
                        result = "现场尽调时间应晚于在线尽调时间";
                        break;
                    case 3:
                        result = "方案交流时间应晚于现场尽调时间";
                        break;
                    case 4:
                        result = "合同交流时间应晚于方案交流时间";
                        break;
                    case 5:
                        result = "合同签订时间应晚于合同交流时间";
                        break;
                }
                return result;
            }
        }

        List<String> steps = new ArrayList<>();
        steps.add("介绍交流");
        steps.add("保密协议签订");
        steps.add("在线尽调");
        steps.add("现场尽调");
        steps.add("方案交流");
        steps.add("合同交流");
        steps.add("合同签订");

        List<String> spend = new ArrayList<>();
        spend.add("7天");
        spend.add("7天");
        spend.add("7天");
        spend.add("7天");
        spend.add("14天");
        spend.add("14天");
        spend.add("21天");

        for(int i = 0 ; i < starts.size() ; i++){
            ArrangeRecord record = new ArrangeRecord();
            record.setSalePlanID(salesPlan.getId());
            record.setCustomerName(salesPlan.getCustomerName());
            record.setStep(steps.get(i));
            record.setBdate(starts.get(i));
            record.setSpendTime(spend.get(i));
            record.setCreater(user.getAccount());
            record.setCreaterName(user.getName());
            record.setCreateDate(LocalDate.now());
            record.setType("sale");
            record.setCompleteStatus("O");
            arrangeRecordDAO.save(record);
        }

        //Task表--介绍交流
        Task task1 = new Task();
        task1.setSalePlanID(salesPlan.getId());
        task1.setCustomerCode(salesPlan.getCustomerCode());
        task1.setCustomerName(salesPlan.getCustomerName());
        task1.setPrincipal(salesPlan.getPrincipal());
        task1.setJobName("介绍交流");
        task1.setDescription("二级预警提醒");
        task1.setJobLevel(2);
        task1.setDeadline(starts.get(0).plusDays(4));
        task1.setRemainTime(3);
        task1.setReceiver(salesPlan.getPrincipal());
        task1.setExceedTime(0);
        task1.setCreater(user.getAccount());
        task1.setCreateDate(LocalDate.now());
        task1.setAuthority("sb");

        Task task2 = new Task();
        task2.setSalePlanID(salesPlan.getId());
        task2.setCustomerCode(salesPlan.getCustomerCode());
        task2.setCustomerName(salesPlan.getCustomerName());
        task2.setPrincipal(salesPlan.getPrincipal());
        task2.setJobName("介绍交流");
        task2.setDescription("一级预警提醒");
        task2.setJobLevel(1);
        task2.setDeadline(starts.get(0).plusDays(6));
        task2.setRemainTime(1);
        task2.setReceiver(salesPlan.getPrincipal());
        task2.setExceedTime(0);
        task2.setCreater(user.getAccount());
        task2.setCreateDate(LocalDate.now());
        task2.setAuthority("sb");

        //Task表--保密协议签订
        Task task3 = new Task();
        task3.setSalePlanID(salesPlan.getId());
        task3.setCustomerCode(salesPlan.getCustomerCode());
        task3.setCustomerName(salesPlan.getCustomerName());
        task3.setPrincipal(user.getAccount());
        task3.setJobName("保密协议签订");
        task3.setDescription("二级预警提醒");
        task3.setJobLevel(2);
        task3.setDeadline(starts.get(1).plusDays(4));
        task3.setRemainTime(3);
        task3.setReceiver(user.getAccount());
        task3.setExceedTime(0);
        task3.setCreater(user.getAccount());
        task3.setCreateDate(LocalDate.now());
        task3.setAuthority("sb");

        Task task4 = new Task();
        task4.setSalePlanID(salesPlan.getId());
        task4.setCustomerCode(salesPlan.getCustomerCode());
        task4.setCustomerName(salesPlan.getCustomerName());
        task4.setPrincipal(user.getAccount());
        task4.setJobName("保密协议签订");
        task4.setDescription("一级预警提醒");
        task4.setJobLevel(1);
        task4.setDeadline(starts.get(1).plusDays(6));
        task4.setRemainTime(1);
        task4.setReceiver(user.getAccount());
        task4.setExceedTime(0);
        task4.setCreater(user.getAccount());
        task4.setCreateDate(LocalDate.now());
        task4.setAuthority("sb");

        //Task表--线上尽调
        Task task5 = new Task();
        task5.setSalePlanID(salesPlan.getId());
        task5.setCustomerCode(salesPlan.getCustomerCode());
        task5.setCustomerName(salesPlan.getCustomerName());
        task5.setPrincipal(user.getAccount());
        task5.setJobName("线上尽调");
        task5.setDescription("二级预警提醒");
        task5.setJobLevel(2);
        task5.setDeadline(starts.get(2).plusDays(4));
        task5.setRemainTime(3);
        task5.setReceiver(user.getAccount());
        task5.setExceedTime(0);
        task5.setCreater(user.getAccount());
        task5.setCreateDate(LocalDate.now());
        task5.setAuthority("sb");

        Task task6 = new Task();
        task6.setSalePlanID(salesPlan.getId());
        task6.setCustomerCode(salesPlan.getCustomerCode());
        task6.setCustomerName(salesPlan.getCustomerName());
        task6.setPrincipal(user.getAccount());
        task6.setJobName("线上尽调");
        task6.setDescription("一级预警提醒");
        task6.setJobLevel(1);
        task6.setDeadline(starts.get(2).plusDays(6));
        task6.setRemainTime(1);
        task6.setReceiver(user.getAccount());
        task6.setExceedTime(0);
        task6.setCreater(user.getAccount());
        task6.setCreateDate(LocalDate.now());
        task6.setAuthority("sb");

        //Task表--现场尽调
        Task task7 = new Task();
        task7.setSalePlanID(salesPlan.getId());
        task7.setCustomerCode(salesPlan.getCustomerCode());
        task7.setCustomerName(salesPlan.getCustomerName());
        task7.setPrincipal(user.getAccount());
        task7.setJobName("现场尽调");
        task7.setDescription("二级预警提醒");
        task7.setJobLevel(2);
        task7.setDeadline(starts.get(3).plusDays(4));
        task7.setRemainTime(3);
        task7.setReceiver(user.getAccount());
        task7.setExceedTime(0);
        task7.setCreater(user.getAccount());
        task7.setCreateDate(LocalDate.now());
        task7.setAuthority("sb");

        Task task8 = new Task();
        task8.setSalePlanID(salesPlan.getId());
        task8.setCustomerCode(salesPlan.getCustomerCode());
        task8.setCustomerName(salesPlan.getCustomerName());
        task8.setPrincipal(user.getAccount());
        task8.setJobName("现场尽调");
        task8.setDescription("一级预警提醒");
        task8.setJobLevel(1);
        task8.setDeadline(starts.get(3).plusDays(6));
        task8.setRemainTime(1);
        task8.setReceiver(user.getAccount());
        task8.setExceedTime(0);
        task8.setCreater(user.getAccount());
        task8.setCreateDate(LocalDate.now());
        task8.setAuthority("sb");

        //Task表--方案交流
        Task task9 = new Task();
        task9.setSalePlanID(salesPlan.getId());
        task9.setCustomerCode(salesPlan.getCustomerCode());
        task9.setCustomerName(salesPlan.getCustomerName());
        task9.setPrincipal(user.getAccount());
        task9.setJobName("方案交流");
        task9.setDescription("二级预警提醒");
        task9.setJobLevel(2);
        task9.setDeadline(starts.get(4).plusDays(7));
        task9.setRemainTime(7);
        task9.setReceiver(user.getAccount());
        task9.setExceedTime(0);
        task9.setCreater(user.getAccount());
        task9.setCreateDate(LocalDate.now());
        task9.setAuthority("sb");

        Task task10 = new Task();
        task10.setSalePlanID(salesPlan.getId());
        task10.setCustomerCode(salesPlan.getCustomerCode());
        task10.setCustomerName(salesPlan.getCustomerName());
        task10.setPrincipal(salesPlan.getPrincipal());
        task10.setJobName("方案交流");
        task10.setDescription("一级预警提醒");
        task10.setJobLevel(1);
        task10.setDeadline(starts.get(4).plusDays(11));
        task10.setRemainTime(3);
        task10.setReceiver(user.getAccount());
        task10.setExceedTime(0);
        task10.setCreater(user.getAccount());
        task10.setCreateDate(LocalDate.now());
        task10.setAuthority("sb");

        //Task表--合同交流
        Task task11 = new Task();
        task11.setSalePlanID(salesPlan.getId());
        task11.setCustomerCode(salesPlan.getCustomerCode());
        task11.setCustomerName(salesPlan.getCustomerName());
        task11.setPrincipal(salesPlan.getPrincipal());
        task11.setJobName("合同交流");
        task11.setDescription("一级预警提醒");
        task11.setJobLevel(1);
        task11.setDeadline(starts.get(5).plusDays(14));
        task11.setRemainTime(7);
        task11.setReceiver(user.getAccount());
        task11.setExceedTime(0);
        task11.setCreater(user.getAccount());
        task11.setCreateDate(LocalDate.now());
        task11.setAuthority("sb");

        Task task12 = new Task();
        task12.setSalePlanID(salesPlan.getId());
        task12.setCustomerCode(salesPlan.getCustomerCode());
        task12.setCustomerName(salesPlan.getCustomerName());
        task12.setPrincipal(salesPlan.getPrincipal());
        task12.setJobName("合同签订");
        task12.setDescription("一级预警提醒");
        task12.setJobLevel(1);
        task12.setDeadline(starts.get(6).plusDays(14));
        task12.setRemainTime(7);
        task12.setReceiver(user.getAccount());
        task12.setExceedTime(0);
        task12.setCreater(user.getAccount());
        task12.setCreateDate(LocalDate.now());
        task12.setAuthority("sb");

        //TaskSum表 -- 介绍交流
        TaskSum taskSum1 = new TaskSum();
        taskSum1.setSalePlanID(salePlanID);
        taskSum1.setCustomerCode(salesPlan.getCustomerCode());
        taskSum1.setCustomerName(salesPlan.getCustomerName());
        taskSum1.setTask("介绍交流");
        taskSum1.setJobUrl("foreIntroduce");
        taskSum1.setBdate(starts.get(0));
        taskSum1.setDeadline(starts.get(0).plusDays(7));
        taskSum1.setPrincipal(salesPlan.getPrincipal());
        taskSum1.setTaskLevel(3);
        taskSum1.setAuthority("sb");
        taskSum1.setCity(customer.getCity());
        //TaskSum表 -- 保密协议签订
        TaskSum taskSum2 = new TaskSum();
        taskSum2.setSalePlanID(salePlanID);
        taskSum2.setCustomerCode(salesPlan.getCustomerCode());
        taskSum2.setCustomerName(salesPlan.getCustomerName());
        taskSum2.setTask("保密协议签订");
        taskSum2.setJobUrl("foreSecret");
        taskSum2.setBdate(starts.get(1));
        taskSum2.setDeadline(starts.get(1).plusDays(7));
        taskSum2.setPrincipal(salesPlan.getPrincipal());
        taskSum2.setTaskLevel(3);
        taskSum2.setAuthority("sb");
        taskSum2.setCity(customer.getCity());
        //TaskSum表 -- 在线尽调
        TaskSum taskSum3 = new TaskSum();
        taskSum3.setSalePlanID(salePlanID);
        taskSum3.setCustomerCode(salesPlan.getCustomerCode());
        taskSum3.setCustomerName(salesPlan.getCustomerName());
        taskSum3.setTask("在线尽调");
        taskSum3.setJobUrl("foreSurveyOnline");
        taskSum3.setBdate(starts.get(2));
        taskSum3.setDeadline(starts.get(2).plusDays(7));
        taskSum3.setPrincipal(salesPlan.getPrincipal());
        taskSum3.setTaskLevel(3);
        taskSum3.setAuthority("sb");
        taskSum3.setCity(customer.getCity());
        //TaskSum表 -- 现场尽调
        TaskSum taskSum4 = new TaskSum();
        taskSum4.setSalePlanID(salePlanID);
        taskSum4.setCustomerCode(salesPlan.getCustomerCode());
        taskSum4.setCustomerName(salesPlan.getCustomerName());
        taskSum4.setTask("现场尽调");
        taskSum4.setJobUrl("foreSurveyOffline");
        taskSum4.setBdate(starts.get(3));
        taskSum4.setDeadline(starts.get(3).plusDays(7));
        taskSum4.setPrincipal(salesPlan.getPrincipal());
        taskSum4.setTaskLevel(3);
        taskSum4.setAuthority("sb");
        taskSum4.setCity(customer.getCity());
        //TaskSum表 -- 方案交流
        TaskSum taskSum5 = new TaskSum();
        taskSum5.setSalePlanID(salePlanID);
        taskSum5.setCustomerCode(salesPlan.getCustomerCode());
        taskSum5.setCustomerName(salesPlan.getCustomerName());
        taskSum5.setTask("方案交流");
        taskSum5.setJobUrl("foreScheme");
        taskSum5.setBdate(starts.get(4));
        taskSum5.setDeadline(starts.get(4).plusDays(14));
        taskSum5.setPrincipal(salesPlan.getPrincipal());
        taskSum5.setTaskLevel(3);
        taskSum5.setAuthority("sb");
        taskSum5.setCity(customer.getCity());
        //TaskSum表 -- 合同交流
        TaskSum taskSum6 = new TaskSum();
        taskSum6.setSalePlanID(salePlanID);
        taskSum6.setCustomerCode(salesPlan.getCustomerCode());
        taskSum6.setCustomerName(salesPlan.getCustomerName());
        taskSum6.setTask("合同交流");
        taskSum6.setJobUrl("foreContractPrevious");
        taskSum6.setBdate(starts.get(5));
        taskSum6.setDeadline(starts.get(5).plusDays(14));
        taskSum6.setPrincipal(salesPlan.getPrincipal());
        taskSum6.setTaskLevel(3);
        taskSum6.setAuthority("sb");
        taskSum6.setCity(customer.getCity());
        //TaskSum表 -- 合同签订
        TaskSum taskSum7 = new TaskSum();
        taskSum7.setSalePlanID(salePlanID);
        taskSum7.setCustomerCode(salesPlan.getCustomerCode());
        taskSum7.setCustomerName(salesPlan.getCustomerName());
        taskSum7.setTask("合同签订");
        taskSum7.setJobUrl("foreContractLater");
        taskSum7.setBdate(starts.get(6));
        taskSum7.setDeadline(starts.get(6).plusDays(21));
        taskSum7.setPrincipal(salesPlan.getPrincipal());
        taskSum7.setTaskLevel(3);
        taskSum7.setAuthority("sb");
        taskSum7.setCity(customer.getCity());

        //删除计划排程的任务
        taskDAO.deleteBySalePlanIDAndJobName(salePlanID , "销售排程");
        taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , "销售排程");
        System.out.println("删除相关销售排程");
//        taskDAO.deleteByJobName("销售排程");
        //创建所有销售过程中的任务
        taskDAO.save(task1);
        taskDAO.save(task2);
        taskDAO.save(task3);
        taskDAO.save(task4);
        taskDAO.save(task5);
        taskDAO.save(task6);
        taskDAO.save(task7);
        taskDAO.save(task8);
        taskDAO.save(task9);
        taskDAO.save(task10);
        taskDAO.save(task11);
        taskDAO.save(task12);

        taskSumDAO.save(taskSum1);
        taskSumDAO.save(taskSum2);
        taskSumDAO.save(taskSum3);
        taskSumDAO.save(taskSum4);
        taskSumDAO.save(taskSum5);
        taskSumDAO.save(taskSum6);
        taskSumDAO.save(taskSum7);
        //修改销售订单的状态
        salesPlan.setDeadline(starts.get(0));
        salesPlan.setSpendTime(7);
        salesPlanDAO.save(salesPlan);

        return "OK";
    }

    @RequestMapping("/getSaleArrange")
    public List<ArrangeRecord> showSaleArrange(String salePlanID){
        String type = "sale";
        return arrangeRecordDAO.findBySalePlanIDAndType(salePlanID , type);
    }

    @RequestMapping("/getOperateArrange")
    public List<ArrangeRecord> showOperateArrange(String salePlanID){
        String type="operate";
        return arrangeRecordDAO.findBySalePlanIDAndType(salePlanID , type);
    }

    @RequestMapping("/getShowAllArrangeTime")
    public List<ArrangeRecord> findHadArrange(String salePlanID){
        List<String> steps = new ArrayList<>();
        steps.add("介绍交流");
        steps.add("保密协议签订");
        steps.add("在线尽调");
        steps.add("现场尽调");
        steps.add("方案交流");
        steps.add("合同交流");
        steps.add("合同签订");

        List<ArrangeRecord> records = new ArrayList<>();
        for(int i = 0 ; i < steps.size() ; i++){
            String step = steps.get(i);
            List<ArrangeRecord> arrangeRecordList = arrangeRecordDAO.findBySalePlanIDAndStepAndType(salePlanID , step , "sale");
            if(arrangeRecordList != null && arrangeRecordList.size() != 0){
                records.add(arrangeRecordList.get(0));
            }else{
                ArrangeRecord arrangeRecord = new ArrangeRecord();
                arrangeRecord.setCompleteStatus("O");
                records.add(arrangeRecord);
            }
        }
        return records;
    }

    @RequestMapping("/updateArrangeRecord")
    @Transactional
    public String updateChanges(String[] bdates , String salePlanID){
        List<String> steps = new ArrayList<>();
        steps.add("介绍交流");
        steps.add("保密协议签订");
        steps.add("在线尽调");
        steps.add("现场尽调");
        steps.add("方案交流");
        steps.add("合同交流");
        steps.add("合同签订");

        List<Integer> days = new ArrayList<>();
        days.add(7);
        days.add(7);
        days.add(7);
        days.add(7);
        days.add(14);
        days.add(14);
        days.add(21);

        List<Integer> surplus = new ArrayList<>();
        surplus.add(3);
        surplus.add(3);
        surplus.add(3);
        surplus.add(2);
        surplus.add(7);
        surplus.add(7);
        surplus.add(7);

        List<Integer> surplus2 = new ArrayList<>();
        surplus2.add(1);
        surplus2.add(1);
        surplus2.add(1);
        surplus2.add(1);
        surplus2.add(3);
        surplus2.add(7);
        surplus2.add(7);

        for(int i = 0 ; i < bdates.length - 1 ; i++){
            String firstStep = steps.get(i);
            String secondStep = steps.get(i+1);
            String bdate = bdates[i];
            if(bdate.equals("0")){
                continue;
            }else{
                LocalDate previousTime = LocalDate.parse(bdate , fmt);
                LocalDate laterTime = LocalDate.parse(bdates[i + 1] , fmt);
                if(previousTime.isAfter(laterTime)){
                    return firstStep + "的开始时间应早于"+secondStep+"的开始时间";
                }
            }
        }



        for(int i = 0 ; i < steps.size() ; i++){
            String bdate = bdates[i];
            LocalDate btime = LocalDate.parse(bdate , fmt);
            String step = steps.get(i);
            int during = days.get(i);

            int rest = during - surplus.get(i);
            int rest2 = during - surplus2.get(i);

            /**
             * bdate == "0"
             * 即不可修改 则跳入到下一个循环
             * bdate == "yyyy-MM-dd"
             * 即可修改 则修改相应的数据
             */
            if("0".equals(bdate)){
                continue;
            }else{
                /**
                 * 排程修改（修改任务信息）：
                 * 1、ArrangeRecord
                 * 2、TaskSum
                 * 3、Task
                 */
                List<ArrangeRecord> arrangeRecordList = arrangeRecordDAO.findBySalePlanIDAndStepAndType(salePlanID , step , "sale");
                ArrangeRecord arrangeRecord = arrangeRecordList.get(0);
                arrangeRecord.setBdate(btime);
                arrangeRecordDAO.save(arrangeRecord);

                List<TaskSum> taskSumList = taskSumDAO.findBySalePlanIDAndTask(salePlanID , step);
                if(taskSumList != null && taskSumList.size() != 0){
                    TaskSum taskSum = taskSumList.get(0);
                    taskSum.setBdate(btime);
                    taskSum.setDeadline(btime.plusDays(during));
                    taskSumDAO.save(taskSum);
                }
                List<Task> taskList = taskDAO.findBySalePlanIDAndJobNameAndJobLevel(salePlanID , step , 2);
                if(taskList != null && taskList.size() != 0){
                    Task task = taskList.get(0);
                    task.setDeadline(btime.plusDays(rest));
                    taskDAO.save(task);
                }

                List<Task> taskList2 = taskDAO.findBySalePlanIDAndJobNameAndJobLevel(salePlanID , step , 1);
                if(taskList2 != null && taskList2.size() != 0){
                    Task task = taskList2.get(0);
                    task.setDeadline(btime.plusDays(rest2));
                    taskDAO.save(task);
                }

            }
        }

        /**
         * 修改销售订单中的的时间
         */
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        String planStatus = salesPlan.getPlanStatus();
        if(planStatus.equals("first")){
            LocalDate deadline = LocalDate.parse(bdates[0],fmt).plusDays(days.get(0));
            salesPlan.setDeadline(deadline);
            salesPlanDAO.save(salesPlan);
        }else if(planStatus.equals("second")){
            LocalDate deadline = LocalDate.parse(bdates[1],fmt).plusDays(days.get(1));
            salesPlan.setDeadline(deadline);
            salesPlanDAO.save(salesPlan);
        }else if(planStatus.equals("third")){
            LocalDate deadline = LocalDate.parse(bdates[2],fmt).plusDays(days.get(2));
            salesPlan.setDeadline(deadline);
            salesPlanDAO.save(salesPlan);
        }else if(planStatus.equals("fourth")){
            LocalDate deadline = LocalDate.parse(bdates[3],fmt).plusDays(days.get(3));
            salesPlan.setDeadline(deadline);
            salesPlanDAO.save(salesPlan);
        }else if(planStatus.equals("fifth")){
            LocalDate deadline = LocalDate.parse(bdates[4],fmt).plusDays(days.get(4));
            salesPlan.setDeadline(deadline);
            salesPlanDAO.save(salesPlan);
        }else if(planStatus.equals("sixth")){
            LocalDate deadline = LocalDate.parse(bdates[5],fmt).plusDays(days.get(5));
            salesPlan.setDeadline(deadline);
            salesPlanDAO.save(salesPlan);
        }else if(planStatus.equals("seventh")){
            LocalDate deadline = LocalDate.parse(bdates[6],fmt).plusDays(days.get(6));
            salesPlan.setDeadline(deadline);
            salesPlanDAO.save(salesPlan);
        }else{

        }

        return "OK";
    }

}
