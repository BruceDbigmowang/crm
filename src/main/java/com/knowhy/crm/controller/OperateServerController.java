package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.*;
import com.knowhy.crm.entity.CrmSalesplan;
import com.knowhy.crm.mapper.CrmSalesplanMapper;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.OperateServerService;
import com.knowhy.crm.service.TaskService;
import com.knowhy.crm.service.UserService;
import com.knowhy.crm.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class OperateServerController {
    @Autowired
    TaskListDAO taskListDAO;
    @Autowired
    OperateTaskDAO operateTaskDAO;
    @Autowired
    ContractRecordDAO contractRecordDAO;
    @Autowired
    OperateServerService operateServerService;
    @Resource
    CrmSalesplanMapper crmSalesplanMapper;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    UserService userService;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RoleFuncDAO roleFuncDAO;
    @Autowired
    FuncDAO funcDAO;
    @Autowired
    RolesDAO rolesDAO;
    @Autowired
    SatisfactionDAO satisfactionDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    PrincipalListDAO principalListDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");


    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/saveTimeArrange")
    public String arrangeTime(String salePlanID , String spendTime , String[] bdates , String[] edates , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        if("0".equals(spendTime)){
            return "请选择紧急程度";
        }
        int spend = Integer.parseInt(spendTime);

        List<OperateTask> tasks = operateTaskDAO.findAll();
        if(tasks.size() != bdates.length){
            return "数据出错";
        }

        List<TaskList> lists = new ArrayList<>();
        List<LocalDate> bdateList = new ArrayList<>();
        List<LocalDate> edateList = new ArrayList<>();

        String operator = null;

        for(int i = 0 ; i < tasks.size() ; i++){
            OperateTask task = tasks.get(i);
            String stepCode = task.getId();
            String taskName = task.getStepName();
            String customerTask = task.getCustomerTask();
            String selfTask = task.getSelfTask();

            TaskList taskList = new TaskList();
            taskList.setSalePlanID(salePlanID);
            taskList.setStepCode(stepCode);
            taskList.setStepName(taskName);
            taskList.setCustomerTask(customerTask);
            taskList.setSelfTask(selfTask);
            LocalDate start;
            LocalDate end;
            if("".equals(bdates[i])){
                continue;
            }
            try{
                start = LocalDate.parse(bdates[i] , fmt);
                bdateList.add(start);
                taskList.setBdate(start);
            }catch (Exception e){
                return taskName+"的开始时间维护出错";
            }
            try{
                end = LocalDate.parse(edates[i] , fmt);
                if(!end.isAfter(start)){
                    return taskName+"的截止时间应晚于开始时间";
                }
                edateList.add(end);
                taskList.setEdate(end);
            }catch (Exception e){
                return taskName+"的截止时间维护出错";
            }
            Long rest = end.toEpochDay() - start.toEpochDay();
            int restTime = rest.intValue();
            taskList.setRestTime(restTime);
            taskList.setSaleManAccount(user.getAccount());
            taskList.setSaleManName(user.getName());
            List<ContractRecord> contractRecords = contractRecordDAO.findBySalePlanID(salePlanID);
            if(contractRecords == null || contractRecords.size() == 0){
                return "未查到归档信息";
            }
            operator = contractRecords.get(0).getServer();
            taskList.setOperatorAccount(operator);
            taskList.setTaskStatus("O");

            lists.add(taskList);
        }
        /**
         * 找出跨度最大的时间，判断是否符合要求
         */
        LocalDate earliest = bdateList.get(0);
        LocalDate latest = edateList.get(0);
        for(int i = 1 ; i < bdateList.size() ; i++){
            LocalDate start = bdateList.get(i);
            if(start.isBefore(earliest)){
                earliest = start;
            }
            LocalDate end = edateList.get(i);
            if(end.isAfter(latest)){
                latest = end;
            }
        }
        Long bigest = latest.toEpochDay() - earliest.toEpochDay();
        int bigestDay = bigest.intValue();
        if(spend < bigestDay){
            return "计划排程时间超过预期时间";
        }
        operateServerService.saveTaskList(lists , salePlanID , spend , user.getAccount() , user.getName() , latest , operator);
        return "OK";
    }

    /**
     * 根据账号判断是拥有运营导入查看的权限还是运营导入操作的权限
     */
    @RequestMapping("/getAccountOperate")
    public Map<String , Object>getIdentity(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        List<String> perms = userService.findAllFunc(user.getAccount());
        Map<String , Object> map = new HashMap<>();
        if(perms.contains("operateLook")){
            map.put("look" , 1);
        }else{
            map.put("look" , 0);
        }
        if(perms.contains("operateDo")){
            map.put("do" , 1);
        }else{
            map.put("do" , 0);
        }
        return map;

    }

    @RequestMapping("/selectTenthByPageOne")
    public Map<String , Object> findOperateByPage(String status , HttpSession session){
        Map<String , Object>map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> salesplanList = salesPlanDAO.findByPlanStatusAndAssistant(status , user.getAccount());
        map.put("customers" , salesplanList);
        List<List<TaskList>> allTask = new ArrayList<>();
        for(int i = 0 ; i < salesplanList.size() ; i++){
            String salePlanID = salesplanList.get(i).getId();
            List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
            allTask.add(taskLists);
        }
        map.put("allTask" , allTask);
        return map;
    }

    @RequestMapping("/selectRelatedOperate")
    public Map<String , Object> findRealtedOperate(String status , HttpSession session){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        if(roles.getRoleName().equals("销售")){
            List<SalesPlan> salesPlanList = salesPlanDAO.findByPrincipalAndPlanStatusAndSaleArrange(user.getAccount() , status , "C");
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);

        }else if(roles.getRoleName().equals("技术人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByTechnicist(user.getAccount());
            List<SalesPlan> salesPlanList = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    salesPlanList.add(salesPlan);
                }
            }
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);

        }else if(roles.getRoleName().equals("供应链人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByOperator(user.getAccount());
            List<SalesPlan> salesPlanList = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    salesPlanList.add(salesPlan);
                }
            }
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);
        }else{
            List<SalesPlan> salesPlanList = salesPlanDAO.findByPlanStatusAndOperator(status , user.getAccount());
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);
        }
        return map;
    }

    @RequestMapping("/selectTenthByPageTwo")
    public Map<String , Object> findOperateByPageTwo(String status , HttpSession session){
        Map<String , Object>map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> salesplanList = salesPlanDAO.findByPlanStatus(status);
        map.put("customers" , salesplanList);
        List<List<TaskList>> allTask = new ArrayList<>();
        for(int i = 0 ; i < salesplanList.size() ; i++){
            String salePlanID = salesplanList.get(i).getId();
            List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
            allTask.add(taskLists);
        }
        map.put("allTask" , allTask);
        return map;
    }

    @RequestMapping("/selectTenthByPageOneAndName")
    public Map<String , Object> findOperateByPageAndName(String customerName , String status , HttpSession session){
        customerName = "%" + customerName + "%";
        Map<String , Object>map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> salesplanList = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistant(customerName , status , user.getAccount());
        map.put("customers" , salesplanList);
        List<List<TaskList>> allTask = new ArrayList<>();
        for(int i = 0 ; i < salesplanList.size() ; i++){
            String salePlanID = salesplanList.get(i).getId();
            List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
            allTask.add(taskLists);
        }
        map.put("allTask" , allTask);
        return map;
    }

    @RequestMapping("/selectRelatedOperateByName")
    public Map<String , Object> findRealtedOperate(String customerName , String status , HttpSession session){
        customerName = "%"+customerName+"%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        if(roles.getRoleName().equals("销售")){
            List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndSaleArrange(customerName , user.getAccount() , status , "C");
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);

        }else if(roles.getRoleName().equals("技术人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndTechnicist(customerName , user.getAccount());
            List<SalesPlan> salesPlanList = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    salesPlanList.add(salesPlan);
                }
            }
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);

        }else if(roles.getRoleName().equals("供应链人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndOperator(customerName , user.getAccount());
            List<SalesPlan> salesPlanList = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    salesPlanList.add(salesPlan);
                }
            }
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);
        }else{
            List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndOperator(customerName , status , user.getAccount());
            map.put("customers" , salesPlanList);
            List<List<TaskList>> allTask = new ArrayList<>();
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String salePlanID = salesPlanList.get(i).getId();
                List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
                allTask.add(taskLists);
            }
            map.put("allTask" , allTask);
        }
        return map;
    }

    @RequestMapping("/selectTenthByPageTwoAndName")
    public Map<String , Object> findOperateByPageTwoAndName(String customerName , String status , HttpSession session){
        customerName = "%"+customerName+"%";
        Map<String , Object>map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> salesplanList = salesPlanDAO.findByCustomerNameLikeAndPlanStatus(customerName , status);
        map.put("customers" , salesplanList);
        List<List<TaskList>> allTask = new ArrayList<>();
        for(int i = 0 ; i < salesplanList.size() ; i++){
            String salePlanID = salesplanList.get(i).getId();
            List<TaskList> taskLists = taskListDAO.findBySalePlanID(salePlanID);
            allTask.add(taskLists);
        }
        map.put("allTask" , allTask);
        return map;
    }

    /**
     * 点击某一事件节点完成：
     * 1、根据销售订单编号及节点编号修改记录状态
     * 2、判断是否是所有节点都已完成
     *    若都已完成，修改销售计划状态  进入下一阶段
     *    若没有，无操作
     */
    @RequestMapping("/completeOneTask")
    @Transactional
    public String completeTask(String salePlanID , String stepCode , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        List<TaskList> taskLists = taskListDAO.findBySalePlanIDAndStepCode(salePlanID , stepCode);
        if(taskLists == null || taskLists.size() == 0){
            return "数据出错";
        }
        TaskList task = taskLists.get(0);
        task.setTaskStatus("C");
        taskListDAO.save(task);
        String job = "运营导入——"+task.getStepName();
        taskDAO.deleteBySalePlanIDAndJobName(salePlanID , job);
        taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , job);

        List<TaskList> taskListList = taskListDAO.findBySalePlanIDAndTaskStatus(salePlanID , "O");
        if(taskListList.size() == 0){
            System.out.println("OperateServerController");
            taskDAO.deleteBySalePlanID(salePlanID);
            taskSumDAO.deleteBySalePlanID(salePlanID);
            SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
            salesPlan.setPlanStatus("eleventh");
            salesPlan.setSpendTime(30);
            salesPlanDAO.save(salesPlan);
            taskService.createTechnicalTask(salePlanID , user.getAccount());
            taskService.createReportTask(salePlanID , user.getAccount());

            String customerCode = salesPlan.getCustomerCode();

            /**
             * 运营导入完成之后，将客户数据写入到客户满意度调查表中
             */
            Customer customer = customerDAO.getOne(customerCode);
            List<PrincipalList> principalLists = principalListDAO.findByCustomerCodeAndMainPrincipal(customerCode , "Y");
            if(principalLists.size() > 0){
                PrincipalList principalList = principalLists.get(0);
                Satisfaction satisfaction = new Satisfaction();
                satisfaction.setCustomerCode(customer.getId());
                satisfaction.setCustomerName(customer.getName());
                satisfaction.setPrincipal(principalList.getName());
                satisfaction.setPhone(principalList.getPhone());
                satisfaction.setEmail(principalList.getEmail());
                satisfaction.setWechat(principalList.getWechat());
                Date now = new Date();
                satisfaction.setYearMonth(sdf.format(now));
                satisfaction.setComplete("N");
                satisfaction.setStatus("N");
                satisfaction.setUsing("Y");
                satisfactionDAO.save(satisfaction);
            }else{

                Satisfaction satisfaction = new Satisfaction();
                satisfaction.setCustomerCode(customer.getId());
                satisfaction.setCustomerName(customer.getName());
//                satisfaction.setPrincipal(principalList.getName());
//                satisfaction.setPhone(principalList.getPhone());
//                satisfaction.setEmail(principalList.getEmail());
//                satisfaction.setWechat(principalList.getWechat());
                Date now = new Date();
                satisfaction.setYearMonth(sdf.format(now));
                satisfaction.setComplete("N");
                satisfaction.setStatus("N");
                satisfaction.setUsing("Y");
                satisfactionDAO.save(satisfaction);
            }


        }
        return "OK";
    }
}
