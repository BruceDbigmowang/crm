package com.knowhy.crm.service;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.util.SendEmail;
import com.knowhy.crm.util.SendRemind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskDAO taskDAO;
    @Autowired
    IUserDAO iUserDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    UserService userService;
    @Autowired
    ContractRecordDAO contractRecordDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SatisfactionDAO satisfactionDAO;
    @Autowired
    EmailService emailService;

    /**
     * 定时每天早晨9点钟执行该任务
     * 任务：
     * 遍历任务表中所有数据
     * 循环中{
     *
     *     获取某一任务的截止时间，并与当前时间相比较
     *     1、若与当前时间相等，向相关人员发送提醒信息
     *     并判断该任务的预警等级
     *     若是二级提醒，直接删除任务
     *     若是一级提醒，删除当天任务，并写入第二天任务
     *
     *     2、若晚于当前时间，不做任何处理
     *
     *     3、若早于当前时间（该任务必定是一级提醒）
     *     发送提醒消息，删除当天任务，写入第二天的任务
     *
     * }
     */

    public void doTask(){
        List<Task> taskList = taskDAO.findAll();
        for(Task task : taskList){
            LocalDate deadline = task.getDeadline();
            if(deadline.isAfter(LocalDate.now())){ //截止时间大于当前时间


            }else if(deadline.isEqual(LocalDate.now())){ //截止时间等于当前时间

                int jobLevel = task.getJobLevel();
                if(jobLevel == 2){
                    //发送消息
                    String[] receivers = task.getReceiver().split(",");
                    for(String receive : receivers){
                        System.out.println(receivers.length);
                        System.out.println(receive);
                        String phone = iUserDAO.getOne(receive).getPhone();
                        String message = task.getCustomerName()+"("+task.getSalePlanID()+")"+"处于"+task.getJobName()+"阶段,剩余"+task.getRemainTime()+"天";
//                        SendRemind.getPhonemsg(phone , task.getCustomerName() , task.getJobName() , task.getRemainTime());
                        System.out.println("向"+phone+"发送："+message);
                    }
                    //二级预警任务执行过之后 从任务表中删除
                    int id = task.getId();
                    taskDAO.deleteById(id);
                }else if(jobLevel == 1){
                    //发送消息
                    int id = task.getId();
                    String[] receivers = task.getReceiver().split(",");
                    for(String receive : receivers){
                        String phone = iUserDAO.getOne(receive).getPhone();
                        String message = task.getCustomerName()+"("+task.getSalePlanID()+")"+"处于"+task.getJobName()+"阶段,剩余"+task.getRemainTime()+"天";
//                        SendRemind.getPhonemsg(phone , task.getCustomerName() , task.getJobName() , task.getRemainTime());
                        System.out.println("向"+phone+"发送："+message);
                    }
                    //一级预警提醒任务执行完之后，更新任务于明天再次执行
                    int remainTime = task.getRemainTime() - 1;
                    if(remainTime < 0){
                        task.setExceedTime(1);
                        taskDAO.save(task);
                    }else{
                        task.setRemainTime(remainTime);
                        taskDAO.save(task);
                    }
                }


            }else{ // 截止时间小于当前时间
                int jobLevel = task.getJobLevel();
                //发送消息
                if(jobLevel == 1){
                    String[] receivers = task.getReceiver().split(",");
                    for(String receive : receivers){
                        String phone = iUserDAO.getOne(receive).getPhone();
                        String message = task.getCustomerName()+"("+task.getSalePlanID()+")"+"处于"+task.getJobName()+"阶段,剩余"+task.getRemainTime()+"天";
                        System.out.println("向"+phone+"发送："+message);
                    }
                }
                //一级预警任务更新任务明天执行
                int remain = task.getRemainTime();
                if(remain == 0){
                    int exceed = task.getExceedTime() + 1;
                    task.setExceedTime(exceed);
                    taskDAO.save(task);
                }else if(remain > 0){
                    task.setRemainTime(remain - 1);
                    taskDAO.save(task);
                }
            }
        }
    }

    /**
     * 预警提醒分为：一级预警 和 二级预警
     * 二级预警：
     * 提醒相关人员并删除该任务记录
     * 一级提醒：
     * 若该任务一直未完成，当天发送消息提醒相关人员，并更新该条任务
     * 第二天继续执行
     * 剩余天数 > 0 ,剩余多少天
     * 剩余天数 = 0 ,今天是最后期限
     * 剩余天数 < 0 ,已超期多少天
     */
    public void doTaskTwice(){
        List<Task> taskList = taskDAO.findByDeadline(LocalDate.now());
        List<Task> taskList1 = taskDAO.findByDeadlineBefore(LocalDate.now());
        for(Task task1 : taskList1){
            taskList.add(task1);
        }
        for (Task task : taskList){
            String salePlanID = task.getSalePlanID();
            int level = task.getJobLevel();
            String taskName = task.getJobName();
            if(level == 2){

                //发送消息
                String authority = task.getAuthority();
                if(authority.equals("sb")){
                    String[] receivers = task.getReceiver().split(",");
                    for(String receive : receivers){
                        String phone = iUserDAO.getOne(receive).getPhone();
                        String message = task.getCustomerName()+"("+task.getSalePlanID()+")"+"处于"+task.getJobName()+"阶段,剩余"+task.getRemainTime()+"天";
                        /**
                         * 获取相关人员的邮箱地址，发送邮件
                         */
                        List<String> receiverList = emailService.getReceiveFirst(task.getSalePlanID());
                        for(int i = 0 ; i < receiverList.size() ; i++){
                            String receiver = receiverList.get(i);
                            String result = new SendEmail().SendEmailMessage(receiver , message);
                            System.out.println("向-"+receiver+"-发送："+message+"->"+new Date());
                            continue;
                        }


                    }
                }else if(authority.equals("all")){
                    List<String> accountList = userService.findFinanceAccount();
                    for(String account : accountList){
                        String phone = iUserDAO.getOne(account).getPhone();
                        String message = task.getCustomerName()+"("+task.getSalePlanID()+")"+"处于"+task.getJobName()+"阶段,剩余"+task.getRemainTime()+"天";
//                        SendRemind.getPhonemsg(phone , task.getCustomerName() , task.getJobName() , task.getRemainTime());
                        System.out.println("向"+phone+"发送："+message+"->"+new Date());
                    }
                }

                //二级预警任务执行过之后 从任务表中删除
                int id = task.getId();
                taskDAO.deleteById(id);

                updateTask(salePlanID , taskName , 2);

            }else if(level == 1){
                //发送消息
                String authority = task.getAuthority();
                if(authority.equals("sb")){
                    String[] receivers = task.getReceiver().split(",");
                    for(String receive : receivers){
                        String phone = iUserDAO.getOne(receive).getPhone();
                        String message = task.getCustomerName()+"("+task.getSalePlanID()+")"+"处于"+task.getJobName()+"阶段,剩余"+task.getRemainTime()+"天";

                        /**
                         * 一级预警，还需要发送邮件给领导
                         */
                        List<String> receiverList = emailService.getReceiveSecond(task.getSalePlanID());
                        for(int i = 0 ; i < receiverList.size() ; i++){
                            String receiver = receiverList.get(i);
                            String result = new SendEmail().SendEmailMessage(receiver , message);
                            System.out.println("向-"+receiver+"-发送："+message+"->"+new Date());
                            continue;
                        }


                    }
                }else if(authority.equals("all")){
                    List<String> accountList = userService.findFinanceAccount();
                    for(String account : accountList){
                        String phone = iUserDAO.getOne(account).getPhone();
                        String message = task.getCustomerName()+"("+task.getSalePlanID()+")"+"处于"+task.getJobName()+"阶段,剩余"+task.getRemainTime()+"天";
//                        SendRemind.getPhonemsg(phone , task.getCustomerName() , task.getJobName() , task.getRemainTime());
                        System.out.println("向"+phone+"发送："+message+"->"+new Date());
                    }
                }

                //一级预警提醒任务执行完之后，更新任务于明天再次执行
                int remainTime = task.getRemainTime() - 1;
                task.setRemainTime(remainTime);
                LocalDate deadline = task.getDeadline().plusDays(1);
                task.setDeadline(deadline);
                taskDAO.save(task);

                updateTask(salePlanID , taskName , 1);
            }
        }
    }

    public void saveTask(String salePlanID , String task  , String jobUrl , LocalDate deadline , int level){
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        TaskSum taskSum = new TaskSum();
        taskSum.setSalePlanID(salePlanID);
        taskSum.setCustomerCode(salesPlan.getCustomerCode());
        taskSum.setCustomerName(salesPlan.getCustomerName());
        taskSum.setTask(task);
        taskSum.setJobUrl(jobUrl);
        taskSum.setDeadline(deadline);
        taskSum.setPrincipal(salesPlan.getPrincipal());
        taskSum.setTaskLevel(level);
        taskSum.setAuthority("sb");
        taskSumDAO.save(taskSum);
    }

    //创建任务共财务人员查看
    public void saveTaskForAll(String salePlanID , String task  , String jobUrl , LocalDate deadline , int level){
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        TaskSum taskSum = new TaskSum();
        taskSum.setSalePlanID(salePlanID);
        taskSum.setCustomerCode(salesPlan.getCustomerCode());
        taskSum.setCustomerName(salesPlan.getCustomerName());
        taskSum.setTask(task);
        taskSum.setJobUrl(jobUrl);
        taskSum.setDeadline(deadline);
        taskSum.setPrincipal(salesPlan.getPrincipal());
        taskSum.setTaskLevel(level);
        taskSum.setAuthority("sb");
        taskSumDAO.save(taskSum);
    }

    //创建任务供销售助理查看
    public void saveTaskForAss(String salePlanID , String task  , String jobUrl , LocalDate deadline , int level){
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        TaskSum taskSum = new TaskSum();
        taskSum.setSalePlanID(salePlanID);
        taskSum.setCustomerCode(salesPlan.getCustomerCode());
        taskSum.setCustomerName(salesPlan.getCustomerName());
        taskSum.setTask(task);
        taskSum.setJobUrl(jobUrl);
        taskSum.setDeadline(deadline);
        taskSum.setPrincipal(salesPlan.getAssistant());
        taskSum.setTaskLevel(level);
        taskSum.setAuthority("sb");
        taskSumDAO.save(taskSum);
    }

    //创建任务供运营人员查看
    public void saveTaskForOperator(String salePlanID , String task  , String jobUrl , LocalDate deadline , int level){
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        TaskSum taskSum = new TaskSum();
        taskSum.setSalePlanID(salePlanID);
        taskSum.setCustomerCode(salesPlan.getCustomerCode());
        taskSum.setCustomerName(salesPlan.getCustomerName());
        taskSum.setTask(task);
        taskSum.setJobUrl(jobUrl);
        taskSum.setDeadline(deadline);
        taskSum.setPrincipal(salesPlan.getOperator());
        taskSum.setTaskLevel(level);
        taskSum.setAuthority("sb");
        taskSumDAO.save(taskSum);
    }

    public void updateTask(String salePlanID , String taskName , int level){
        List<TaskSum> sumList = taskSumDAO.findBySalePlanIDAndTask(salePlanID , taskName);
        TaskSum sum = sumList.get(0);
        sum.setTaskLevel(level);
        taskSumDAO.save(sum);
    }

    @Transactional
    public void updateTaskPrincipal(String salePlanID , String principal){
        List<Task> taskList = taskDAO.findBySalePlanID(salePlanID);
        for(Task task : taskList){
            task.setPrincipal(principal);
            taskDAO.save(task);
        }
        List<TaskSum> sumList = taskSumDAO.findBySalePlanID(salePlanID);
        for(TaskSum sum : sumList){
            sum.setPrincipal(principal);
            taskSumDAO.save(sum);
        }
    }

    /**
     * 当运营导入完成之后，根据归档录入的信息
     * 自动生成所有的技术服务与月度汇报任务
     * 从 运营导入完成之后的一个月开始
     * 第一个月（第一次技术服务 第一次月度汇报）
     * 之后，月度汇报每个月进行一次，技术服务按合同归档记录的周期进行
     * 直至合同失效
     */
    @Transactional
    public void createTechnicalTask(String salePlanID , String userAccount){
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        Customer customer = customerDAO.getOne(salesPlan.getCustomerCode());
        List<ContractRecord> contractRecords = contractRecordDAO.findBySalePlanID(salePlanID);
        int cycle = contractRecords.get(0).getServerAsk();
        String operator = contractRecords.get(0).getSteward();
        LocalDate deadline = contractRecords.get(0).getDeadline();
        LocalDate now = LocalDate.now();
        LocalDate nextMonth = LocalDate.of(now.getYear(), now.getMonthValue() + 1, 1);//下个月的第一天
        LocalDate lastTime = LocalDate.of(now.getYear(), now.getMonthValue() + 1, 15);
        LocalDate lastDay = nextMonth.with(TemporalAdjusters.lastDayOfMonth()); //下个月最后一天

        /**
         * 设定下个月的1号为第一次提醒技术服务（将任务等级提升为2级）
         * 下个月的15号为第二次提醒技术服务（将任务等级提升为1级）
         *
         * 设定下个月月末前7天为月度汇报第一次提醒（将任务等级提升为2级）
         * 下个月的最后一天为第二次提醒（将任务等级提升为1级）
         */
        Task task1 = new Task();
        task1.setSalePlanID(salesPlan.getId());
        task1.setCustomerCode(salesPlan.getCustomerCode());
        task1.setCustomerName(salesPlan.getCustomerName());
        task1.setPrincipal(salesPlan.getPrincipal());
        task1.setJobName("技术服务");
        task1.setDescription("二级预警提醒");
        task1.setJobLevel(2);
        task1.setDeadline(nextMonth);
        task1.setRemainTime(30);
        task1.setReceiver(salesPlan.getPrincipal());
        task1.setExceedTime(0);
        task1.setCreater(userAccount);
        task1.setCreateDate(LocalDate.now());
        task1.setAuthority("sb");

        Task task2 = new Task();
        task2.setSalePlanID(salesPlan.getId());
        task2.setCustomerCode(salesPlan.getCustomerCode());
        task2.setCustomerName(salesPlan.getCustomerName());
        task2.setPrincipal(salesPlan.getPrincipal());
        task2.setJobName("技术服务");
        task2.setDescription("一级预警提醒");
        task2.setJobLevel(1);
        task2.setDeadline(lastTime);
        task2.setRemainTime(15);
        task2.setReceiver(salesPlan.getPrincipal());
        task2.setExceedTime(0);
        task2.setCreater(userAccount);
        task2.setCreateDate(LocalDate.now());
        task2.setAuthority("sb");

        TaskSum taskSum1 = new TaskSum();
        taskSum1.setSalePlanID(salePlanID);
        taskSum1.setCustomerCode(salesPlan.getCustomerCode());
        taskSum1.setCustomerName(salesPlan.getCustomerName());
        taskSum1.setTask("技术服务");
        taskSum1.setJobUrl("foreTechnicalService");
        taskSum1.setDeadline(nextMonth.with(TemporalAdjusters.lastDayOfMonth()));
        taskSum1.setPrincipal(operator);
        taskSum1.setTaskLevel(3);
        taskSum1.setAuthority("sb");
        taskSum1.setCity(customer.getCity());

        taskDAO.save(task1);
        taskDAO.save(task2);
        taskSumDAO.save(taskSum1);


        LocalDate middleDate = nextMonth;
        int condition = middleDate.getMonthValue() + cycle;
        if(condition > 12){
            nextMonth = LocalDate.of(middleDate.getYear() + 1, condition - 12 , 1);
            lastTime = LocalDate.of(middleDate.getYear() + 1, condition - 12 , 15);
        }else{
            nextMonth = LocalDate.of(middleDate.getYear(), condition , 1);
            lastTime = LocalDate.of(middleDate.getYear(), condition , 15);
        }
        middleDate = nextMonth;
        System.out.println("nextMonth:"+nextMonth+";middleDate"+middleDate);


        int i = 2;
        while(nextMonth.isBefore(deadline)){
            System.out.println("当前日期："+lastDay +"----截止日期："+deadline);
            System.out.println("技术服务");
            Task task3 = new Task();
            task3.setSalePlanID(salesPlan.getId());
            task3.setCustomerCode(salesPlan.getCustomerCode());
            task3.setCustomerName(salesPlan.getCustomerName());
            task3.setPrincipal(salesPlan.getPrincipal());
            task3.setJobName("技术服务");
            task3.setDescription("二级预警提醒");
            task3.setJobLevel(2);
            task3.setDeadline(nextMonth);
            task3.setRemainTime(30);
            task3.setReceiver(salesPlan.getPrincipal());
            task3.setExceedTime(0);
            task3.setCreater(userAccount);
            task3.setCreateDate(LocalDate.now());
            task3.setAuthority("sb");

            Task task4 = new Task();
            task4.setSalePlanID(salesPlan.getId());
            task4.setCustomerCode(salesPlan.getCustomerCode());
            task4.setCustomerName(salesPlan.getCustomerName());
            task4.setPrincipal(salesPlan.getPrincipal());
            task4.setJobName("技术服务");
            task4.setDescription("一级预警提醒");
            task4.setJobLevel(1);
            task4.setDeadline(lastTime);
            task4.setRemainTime(15);
            task4.setReceiver(salesPlan.getPrincipal());
            task4.setExceedTime(0);
            task4.setCreater(userAccount);
            task4.setCreateDate(LocalDate.now());
            task4.setAuthority("sb");

            TaskSum taskSum2 = new TaskSum();
            taskSum2.setSalePlanID(salePlanID);
            taskSum2.setCustomerCode(salesPlan.getCustomerCode());
            taskSum2.setCustomerName(salesPlan.getCustomerName());
            taskSum2.setTask("技术服务");
            taskSum2.setJobUrl("foreTechnicalService");
            taskSum2.setDeadline(nextMonth.with(TemporalAdjusters.lastDayOfMonth()));
            taskSum2.setPrincipal(operator);
            taskSum2.setTaskLevel(3);
            taskSum2.setAuthority("sb");
            taskSum2.setCity(customer.getCity());

            taskDAO.save(task3);
            taskDAO.save(task4);
            taskSumDAO.save(taskSum2);


            int condition2 = middleDate.getMonthValue() + cycle;
            if(condition2 > 12){
                nextMonth = LocalDate.of(middleDate.getYear() + 1, condition2 - 12 , 1);
                lastTime = LocalDate.of(middleDate.getYear() + 1, condition2 - 12 , 15);
            }else{
                nextMonth = LocalDate.of(middleDate.getYear(), condition2 , 1);
                lastTime = LocalDate.of(middleDate.getYear(), condition2 , 15);
            }
            middleDate = nextMonth;
            i = i + 1;
        }
    }

    @Transactional
    public void createReportTask(String salePlanID , String userAccount){
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        Customer customer = customerDAO.getOne(salesPlan.getCustomerCode());
        List<ContractRecord> contractRecords = contractRecordDAO.findBySalePlanID(salePlanID);
        int cycle = contractRecords.get(0).getServerAsk();
        String operator = contractRecords.get(0).getSteward();
        LocalDate deadline = contractRecords.get(0).getDeadline();
        LocalDate now = LocalDate.now();
        LocalDate nextMonth = LocalDate.of(now.getYear(), now.getMonthValue() + 1, 1);//下个月的第一天
        LocalDate lastDay = nextMonth.with(TemporalAdjusters.lastDayOfMonth()); //下个月最后一天
        LocalDate firstTime = lastDay.minusDays(7);

        /**
         * 设定下个月的1号为第一次提醒技术服务（将任务等级提升为2级）
         * 下个月的15号为第二次提醒技术服务（将任务等级提升为1级）
         *
         * 设定下个月月末前7天为月度汇报第一次提醒（将任务等级提升为2级）
         * 下个月的最后一天为第二次提醒（将任务等级提升为1级）
         */
        Task task1 = new Task();
        task1.setSalePlanID(salesPlan.getId());
        task1.setCustomerCode(salesPlan.getCustomerCode());
        task1.setCustomerName(salesPlan.getCustomerName());
        task1.setPrincipal(salesPlan.getPrincipal());
        task1.setJobName("月度汇报");
        task1.setDescription("二级预警提醒");
        task1.setJobLevel(2);
        task1.setDeadline(firstTime);
        task1.setRemainTime(7);
        task1.setReceiver(salesPlan.getPrincipal());
        task1.setExceedTime(0);
        task1.setCreater(userAccount);
        task1.setCreateDate(LocalDate.now());
        task1.setAuthority("sb");

        Task task2 = new Task();
        task2.setSalePlanID(salesPlan.getId());
        task2.setCustomerCode(salesPlan.getCustomerCode());
        task2.setCustomerName(salesPlan.getCustomerName());
        task2.setPrincipal(salesPlan.getPrincipal());
        task2.setJobName("月度汇报");
        task2.setDescription("一级预警提醒");
        task2.setJobLevel(1);
        task2.setDeadline(lastDay);
        task2.setRemainTime(0);
        task2.setReceiver(salesPlan.getPrincipal());
        task2.setExceedTime(0);
        task2.setCreater(userAccount);
        task2.setCreateDate(LocalDate.now());
        task2.setAuthority("sb");

        TaskSum taskSum1 = new TaskSum();
        taskSum1.setSalePlanID(salePlanID);
        taskSum1.setCustomerCode(salesPlan.getCustomerCode());
        taskSum1.setCustomerName(salesPlan.getCustomerName());
        taskSum1.setTask("月度汇报");
        taskSum1.setJobUrl("foreMonthlyReport");
        taskSum1.setDeadline(nextMonth.with(TemporalAdjusters.lastDayOfMonth()));
        taskSum1.setPrincipal(operator);
        taskSum1.setTaskLevel(3);
        taskSum1.setAuthority("sb");
        taskSum1.setCity(customer.getCity());

        taskDAO.save(task1);
        taskDAO.save(task2);
        taskSumDAO.save(taskSum1);

        LocalDate middleDate = nextMonth;
        int condition = middleDate.getMonthValue()+1;
        if( condition > 12){
            nextMonth = LocalDate.of(middleDate.getYear() + 1, condition - 12 , 1);
            lastDay = middleDate.with(TemporalAdjusters.lastDayOfMonth()); //下个月最后一天
            firstTime = lastDay.minusDays(7);
        }else{
            nextMonth = LocalDate.of(middleDate.getYear(), condition , 1);
            lastDay = middleDate.with(TemporalAdjusters.lastDayOfMonth()); //下个月最后一天
            firstTime = lastDay.minusDays(7);
        }

        middleDate = nextMonth;
        int i = 2;
        while(nextMonth.isBefore(deadline)){
            System.out.println("月度汇报"+i);
            Task task3 = new Task();
            task3.setSalePlanID(salesPlan.getId());
            task3.setCustomerCode(salesPlan.getCustomerCode());
            task3.setCustomerName(salesPlan.getCustomerName());
            task3.setPrincipal(salesPlan.getPrincipal());
            task3.setJobName("月度汇报");
            task3.setDescription("二级预警提醒");
            task3.setJobLevel(2);
            task3.setDeadline(firstTime);
            task3.setRemainTime(7);
            task3.setReceiver(salesPlan.getPrincipal());
            task3.setExceedTime(0);
            task3.setCreater(userAccount);
            task3.setCreateDate(LocalDate.now());
            task3.setAuthority("sb");

            Task task4 = new Task();
            task4.setSalePlanID(salesPlan.getId());
            task4.setCustomerCode(salesPlan.getCustomerCode());
            task4.setCustomerName(salesPlan.getCustomerName());
            task4.setPrincipal(operator);
            task4.setJobName("月度汇报");
            task4.setDescription("一级预警提醒");
            task4.setJobLevel(1);
            task4.setDeadline(lastDay);
            task4.setRemainTime(0);
            task4.setReceiver(salesPlan.getPrincipal());
            task4.setExceedTime(0);
            task4.setCreater(userAccount);
            task4.setCreateDate(LocalDate.now());
            task4.setAuthority("sb");

            TaskSum taskSum2 = new TaskSum();
            taskSum2.setSalePlanID(salePlanID);
            taskSum2.setCustomerCode(salesPlan.getCustomerCode());
            taskSum2.setCustomerName(salesPlan.getCustomerName());
            taskSum2.setTask("月度汇报");
            taskSum2.setJobUrl("foreMonthlyReport");
            taskSum2.setDeadline(nextMonth.with(TemporalAdjusters.lastDayOfMonth()));
            taskSum2.setPrincipal(salesPlan.getPrincipal());
            taskSum2.setTaskLevel(3);
            taskSum2.setAuthority("sb");
            taskSum2.setCity(customer.getCity());

            taskDAO.save(task3);
            taskDAO.save(task4);
            taskSumDAO.save(taskSum2);

            int condition2 = middleDate.getMonthValue()+1;
            if( condition2 > 12){
                nextMonth = LocalDate.of(middleDate.getYear() + 1, condition2 - 12 , 1);
                lastDay = middleDate.with(TemporalAdjusters.lastDayOfMonth()); //下个月最后一天
                firstTime = lastDay.minusDays(7);
            }else{
                nextMonth = LocalDate.of(middleDate.getYear(), condition2 , 1);
                lastDay = middleDate.with(TemporalAdjusters.lastDayOfMonth()); //下个月最后一天
                firstTime = lastDay.minusDays(7);
            }
            i = i + 1;
            middleDate = nextMonth;
        }
    }

    public void deleteTargetTecTask(String salePlanID){
        List<Task> taskList = taskDAO.findBySalePlanIDAndJobNameOrderByDeadlineAsc(salePlanID , "技术服务");
        int taskId = taskList.get(0).getId();
        taskDAO.deleteById(taskId);
        int taskId2 = taskList.get(1).getId();
        taskDAO.deleteById(taskId2);
        List<TaskSum> taskSumList = taskSumDAO.findBySalePlanIDAndTaskOrderByDeadlineAsc(salePlanID , "技术服务");
        int sumId = taskSumList.get(0).getId();
        taskSumDAO.deleteById(sumId);
    }

    public void  deleteTargetReportTask(String salePlanID){
        List<Task> taskList = taskDAO.findBySalePlanIDAndJobNameOrderByDeadlineAsc(salePlanID , "月度汇报");
        if(taskList != null && taskList.size() > 1){
            int taskId = taskList.get(0).getId();
            taskDAO.deleteById(taskId);
            int taskId2 = taskList.get(1).getId();
            taskDAO.deleteById(taskId2);
        }
        List<TaskSum> taskSumList = taskSumDAO.findBySalePlanIDAndTaskOrderByDeadlineAsc(salePlanID , "月度汇报");
        if(taskSumList != null && taskSumList.size() > 0){
            int sumId = taskSumList.get(0).getId();
            taskSumDAO.deleteById(sumId);
        }
    }

    /**
     *每个月月初，重置满意度调查表中的数据
     * 发送状态、完成状态、年月
     *
     * 重置技术服务与月度汇报填写状态
     */
    public void restSatisfaction(){

        List<Satisfaction> satisfactionList = satisfactionDAO.findAll();
        for(int i=0; i < satisfactionList.size() ; i++){
            Satisfaction satisfaction = satisfactionList.get(i);
            satisfaction.setComplete("N");
            satisfaction.setStatus("N");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            satisfaction.setYearMonth(sdf.format(new Date()));

            satisfactionDAO.save(satisfaction);
        }

        /**
         * 技术服务与月度汇报当月填写完成之后，下个月才可重新提交
         */

        List<SalesPlan> planList = salesPlanDAO.findAll();
        for(int i = 0 ; i < planList.size() ; i++){
            SalesPlan salesPlan = planList.get(i);
            salesPlan.setServiceWrite("O");
            salesPlan.setReportWrite("O");
            salesPlanDAO.save(salesPlan);
        }
    }
}
