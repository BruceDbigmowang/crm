package com.knowhy.crm.service;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.pojo.Task;
import com.knowhy.crm.pojo.TaskSum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    SalesPlanDAO planDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskService taskService;

    public void saveCustomer(Customer customer){
        customerDAO.save(customer);
    }

    public List<Customer> getAllCustomer(){
        return customerDAO.findAll();
    }

    public List<Customer> getTodayCustomer(LocalDate start , LocalDate end){
        return customerDAO.findByCreateDateBetween(start , end);
    }

    public List<Customer> getCustomerByName(String name){
        String customerName = "%"+name+"%";
        List<Customer> customerList = customerDAO.findByNameLike(customerName);
        return customerList;
    }

    public List<Customer> getCustomerByPhone(String phone){
        String customerPhone = "%"+phone+"%";
        List<Customer> customerList = customerDAO.findByPhoneLike(customerPhone);
        return customerList;
    }

    @Transactional
    public void arrangeCustomer(String[] ids , String saleManAccount , String saleManName , String creater){
        for(int i = 0 ; i < ids.length ; i++){
            //修改客户表中的数据
            String customerID = ids[i];
            Customer customer = customerDAO.getOne(customerID);
            customer.setFollowStatus("C");
            customer.setFollowDate(LocalDate.now());
            customer.setFollowPerson(saleManAccount);
            customer.setFollowName(saleManName);
            customerDAO.save(customer);
            //修改销售计划表中的数据
            SalesPlan salesPlan = salesPlanDAO.findByCustomerCode(customerID).get(0);
            String planStatus = salesPlan.getPlanStatus();
            salesPlan.setUpdateDate(LocalDate.now());
            int rest = 0;
            if(planStatus.equals("fifth")){
                rest = 14;

            }else if(planStatus.equals("sixth") || planStatus.equals("seventh")){
                rest = 21;
            }else{
                rest = 7;
            }

            salesPlan.setSpendTime(rest);
            salesPlan.setDeadline(LocalDate.now().plusDays(rest));
            String follow = salesPlan.getAllOperator();
            if(follow != null && !"".equals(follow)){
                follow = follow+","+saleManAccount;
            }else{
                follow = saleManAccount;
            }
            salesPlan.setPrincipal(saleManAccount);
            salesPlan.setPrincipalName(saleManName);
            salesPlan.setAllOperator(follow);
//            salesPlan.setPlanStatus("first");
            salesPlanDAO.save(salesPlan);

            /**
             * 新增任务
             */
            Task task = new Task();
            task.setSalePlanID(salesPlan.getId());
            task.setCustomerCode(salesPlan.getCustomerCode());
            task.setCustomerName(salesPlan.getCustomerName());
            task.setPrincipal(saleManAccount);
            task.setJobName("销售排程");
            task.setDescription("一级预警提醒");
            task.setJobLevel(1);
            task.setDeadline(LocalDate.now());
            task.setRemainTime(0);
            task.setReceiver(saleManAccount);
            task.setExceedTime(0);
            task.setCreater(creater);
            task.setCreateDate(LocalDate.now());
            task.setAuthority("sb");
            taskDAO.save(task);

            taskService.saveTask(salesPlan.getId() , "销售排程" , "foreArrangeSale", LocalDate.now() , 1);


//            if(planStatus.equals("first")){
//                Task task = new Task();
//                task.setSalePlanID(salesPlan.getId());
//                task.setCustomerCode(salesPlan.getCustomerCode());
//                task.setCustomerName(salesPlan.getCustomerName());
//                task.setPrincipal(saleManAccount);
//                task.setJobName("介绍交流");
//                task.setDescription("二级预警提醒");
//                task.setJobLevel(2);
//                task.setDeadline(LocalDate.now().plusDays(4));
//                task.setRemainTime(3);
//                task.setReceiver(saleManAccount);
//                task.setExceedTime(0);
//                task.setCreater(creater);
//                task.setCreateDate(LocalDate.now());
//                task.setAuthority("sb");
//                taskDAO.save(task);
//
//                Task task2 = new Task();
//                task2.setSalePlanID(salesPlan.getId());
//                task2.setCustomerCode(salesPlan.getCustomerCode());
//                task2.setCustomerName(salesPlan.getCustomerName());
//                task2.setPrincipal(saleManAccount);
//                task2.setJobName("介绍交流");
//                task2.setDescription("一级预警提醒");
//                task2.setJobLevel(1);
//                task2.setDeadline(LocalDate.now().plusDays(6));
//                task2.setRemainTime(1);
//                task2.setReceiver(saleManAccount);
//                task2.setExceedTime(0);
//                task2.setCreater(creater);
//                task2.setCreateDate(LocalDate.now());
//                task2.setAuthority("sb");
//                taskDAO.save(task2);
//
//                taskService.saveTask(salesPlan.getId() , "介绍交流" , "foreIntroduce" , LocalDate.now().plusDays(7) , 3);
//
//            }else if(planStatus.equals("second")){
//                Task task = new Task();
//                task.setSalePlanID(salesPlan.getId());
//                task.setCustomerCode(salesPlan.getCustomerCode());
//                task.setCustomerName(salesPlan.getCustomerName());
//                task.setPrincipal(saleManAccount);
//                task.setJobName("保密协议签订");
//                task.setDescription("二级预警提醒");
//                task.setJobLevel(2);
//                task.setDeadline(LocalDate.now().plusDays(4));
//                task.setRemainTime(3);
//                task.setReceiver(saleManAccount);
//                task.setExceedTime(0);
//                task.setCreater(creater);
//                task.setCreateDate(LocalDate.now());
//                task.setAuthority("sb");
//                taskDAO.save(task);
//
//                Task task2 = new Task();
//                task2.setSalePlanID(salesPlan.getId());
//                task2.setCustomerCode(salesPlan.getCustomerCode());
//                task2.setCustomerName(salesPlan.getCustomerName());
//                task2.setPrincipal(saleManAccount);
//                task2.setJobName("保密协议签订");
//                task2.setDescription("一级预警提醒");
//                task2.setJobLevel(1);
//                task2.setDeadline(LocalDate.now().plusDays(6));
//                task2.setRemainTime(1);
//                task2.setReceiver(saleManAccount);
//                task2.setExceedTime(0);
//                task2.setCreater(creater);
//                task2.setCreateDate(LocalDate.now());
//                task2.setAuthority("sb");
//                taskDAO.save(task2);
//
//                taskService.saveTask(salesPlan.getId() , "保密协议签订" , "foreSecret" , LocalDate.now().plusDays(7) , 3);
//            }else if(planStatus.equals("third")){
//                Task task = new Task();
//                task.setSalePlanID(salesPlan.getId());
//                task.setCustomerCode(salesPlan.getCustomerCode());
//                task.setCustomerName(salesPlan.getCustomerName());
//                task.setPrincipal(saleManAccount);
//                task.setJobName("线上尽调");
//                task.setDescription("二级预警提醒");
//                task.setJobLevel(2);
//                task.setDeadline(LocalDate.now().plusDays(4));
//                task.setRemainTime(3);
//                task.setReceiver(saleManAccount);
//                task.setExceedTime(0);
//                task.setCreater(creater);
//                task.setCreateDate(LocalDate.now());
//                task.setAuthority("sb");
//                taskDAO.save(task);
//
//                Task task2 = new Task();
//                task2.setSalePlanID(salesPlan.getId());
//                task2.setCustomerCode(salesPlan.getCustomerCode());
//                task2.setCustomerName(salesPlan.getCustomerName());
//                task2.setPrincipal(saleManAccount);
//                task2.setJobName("线上尽调");
//                task2.setDescription("一级预警提醒");
//                task2.setJobLevel(1);
//                task2.setDeadline(LocalDate.now().plusDays(6));
//                task2.setRemainTime(1);
//                task2.setReceiver(saleManAccount);
//                task2.setExceedTime(0);
//                task2.setCreater(creater);
//                task2.setCreateDate(LocalDate.now());
//                task2.setAuthority("sb");
//                taskDAO.save(task2);
//
//                taskService.saveTask(salesPlan.getId() , "线上尽调" , "foreSurveyOnline" , LocalDate.now().plusDays(7) , 3);
//            }else if(planStatus.equals("fourth")){
//                Task task = new Task();
//                task.setSalePlanID(salesPlan.getId());
//                task.setCustomerCode(salesPlan.getCustomerCode());
//                task.setCustomerName(salesPlan.getCustomerName());
//                task.setPrincipal(saleManAccount);
//                task.setJobName("现场尽调");
//                task.setDescription("二级预警提醒");
//                task.setJobLevel(2);
//                task.setDeadline(LocalDate.now().plusDays(4));
//                task.setRemainTime(3);
//                task.setReceiver(saleManAccount);
//                task.setExceedTime(0);
//                task.setCreater(creater);
//                task.setCreateDate(LocalDate.now());
//                task.setAuthority("sb");
//                taskDAO.save(task);
//
//                Task task2 = new Task();
//                task2.setSalePlanID(salesPlan.getId());
//                task2.setCustomerCode(salesPlan.getCustomerCode());
//                task2.setCustomerName(salesPlan.getCustomerName());
//                task2.setPrincipal(saleManAccount);
//                task2.setJobName("现场尽调");
//                task2.setDescription("一级预警提醒");
//                task2.setJobLevel(1);
//                task2.setDeadline(LocalDate.now().plusDays(6));
//                task2.setRemainTime(1);
//                task2.setReceiver(saleManAccount);
//                task2.setExceedTime(0);
//                task2.setCreater(creater);
//                task2.setCreateDate(LocalDate.now());
//                task2.setAuthority("sb");
//                taskDAO.save(task2);
//
//                taskService.saveTask(salesPlan.getId() , "现场尽调"  , "foreSurveyOffline" , LocalDate.now().plusDays(7) , 3);
//            }else if(planStatus.equals("fifth")){
//                Task task = new Task();
//                task.setSalePlanID(salesPlan.getId());
//                task.setCustomerCode(salesPlan.getCustomerCode());
//                task.setCustomerName(salesPlan.getCustomerName());
//                task.setPrincipal(saleManAccount);
//                task.setJobName("方案交流");
//                task.setDescription("二级预警提醒");
//                task.setJobLevel(2);
//                task.setDeadline(LocalDate.now().plusDays(7));
//                task.setRemainTime(7);
//                task.setReceiver(saleManAccount);
//                task.setExceedTime(0);
//                task.setCreater(creater);
//                task.setCreateDate(LocalDate.now());
//                task.setAuthority("sb");
//                taskDAO.save(task);
//
//                Task task2 = new Task();
//                task2.setSalePlanID(salesPlan.getId());
//                task2.setCustomerCode(salesPlan.getCustomerCode());
//                task2.setCustomerName(salesPlan.getCustomerName());
//                task2.setPrincipal(saleManAccount);
//                task2.setJobName("现场尽调");
//                task2.setDescription("一级预警提醒");
//                task2.setJobLevel(1);
//                task2.setDeadline(LocalDate.now().plusDays(11));
//                task2.setRemainTime(3);
//                task2.setReceiver(saleManAccount);
//                task2.setExceedTime(0);
//                task2.setCreater(creater);
//                task2.setCreateDate(LocalDate.now());
//                task2.setAuthority("sb");
//                taskDAO.save(task2);
//
//                taskService.saveTask(salesPlan.getId() , "方案交流"  , "foreScheme" , LocalDate.now().plusDays(14) , 3);
//            }else if(planStatus.equals("sixth")){
//                Task task2 = new Task();
//                task2.setSalePlanID(salesPlan.getId());
//                task2.setCustomerCode(salesPlan.getCustomerCode());
//                task2.setCustomerName(salesPlan.getCustomerName());
//                task2.setPrincipal(saleManAccount);
//                task2.setJobName("合同交流");
//                task2.setDescription("一级预警提醒");
//                task2.setJobLevel(1);
//                task2.setDeadline(LocalDate.now().plusDays(14));
//                task2.setRemainTime(7);
//                task2.setReceiver(saleManAccount);
//                task2.setExceedTime(0);
//                task2.setCreater(creater);
//                task2.setCreateDate(LocalDate.now());
//                task2.setAuthority("sb");
//                taskDAO.save(task2);
//
//                taskService.saveTask(salesPlan.getId() , "合同交流"  , "foreContractPrevious" , LocalDate.now().plusDays(14) , 3);
//            }else if(planStatus.equals("seventh")){
//                Task task2 = new Task();
//                task2.setSalePlanID(salesPlan.getId());
//                task2.setCustomerCode(salesPlan.getCustomerCode());
//                task2.setCustomerName(salesPlan.getCustomerName());
//                task2.setPrincipal(saleManAccount);
//                task2.setJobName("合同签订");
//                task2.setDescription("一级预警提醒");
//                task2.setJobLevel(1);
//                task2.setDeadline(LocalDate.now().plusDays(14));
//                task2.setRemainTime(7);
//                task2.setReceiver(saleManAccount);
//                task2.setExceedTime(0);
//                task2.setCreater(creater);
//                task2.setCreateDate(LocalDate.now());
//                task2.setAuthority("sb");
//                taskDAO.save(task2);
//
//                taskService.saveTask(salesPlan.getId() , "合同签订" , "foreContractLater" , LocalDate.now().plusDays(21) , 3);
//            }




        }
    }

    @Transactional
    public void deleteCustomer(String customerID){
        customerDAO.deleteById(customerID);
        salesPlanDAO.deleteByCustomerCode(customerID);
    }
}
