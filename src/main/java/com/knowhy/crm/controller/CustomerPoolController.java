package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.CustomerService;
import com.knowhy.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerPoolController {

    @Autowired
    SaleManDAO saleManDAO;
    @Autowired
    CustomerService customerService;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskSumDAO taskSumDAO;

    @RequestMapping("/loadAllSaleMan")
    public List<SaleMan> getAllSaleMan(){
        return saleManDAO.findAll();
    }

    @RequestMapping("/selectSaleManByCondition")
    public List<SaleMan> selectSaleMan(String condition){
        condition = "%"+condition+"%";
        List<SaleMan> saleManList = new ArrayList<>();
        List<SaleMan> saleManList1 = saleManDAO.findByAccountLike(condition);
        for(SaleMan saleMan : saleManList1){
            saleManList.add(saleMan);
        }
        List<SaleMan> saleManList2 = saleManDAO.findByNameLike(condition);
        for(SaleMan saleMan : saleManList2){
            saleManList.add(saleMan);
        }
        return saleManList;
    }

    @RequestMapping("/arrangeCustomer")
    public synchronized String toArrange(String ids , String account , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        if(account.equals("0")){
            return "请选择销售人员";
        }
        String name = saleManDAO.getOne(account).getName();
        if(ids == null || "".equals(ids)){
            return "请选择客户";
        }else{
            String[] IDS = ids.split(",");
            try{
                customerService.arrangeCustomer(IDS , account , name , user.getAccount());
            }catch (Exception e){
                return e.getMessage();
            }
        }
        return "分配成功";
    }

    @RequestMapping("/followSalePlan")
    @Transactional
    public String toFollow(String salePlanID , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        String name = user.getName();
        Customer customer = customerDAO.getOne(salePlanID);
        customer.setFollowStatus("C");
        customer.setFollowDate(LocalDate.now());
        customer.setFollowPerson(account);
        customer.setFollowName(name);

        //修改销售计划表中的数据
        SalesPlan salesPlan = salesPlanDAO.findByCustomerCode(salePlanID).get(0);
        salesPlan.setUpdateDate(LocalDate.now());
        salesPlan.setSpendTime(7);
        salesPlan.setDeadline(LocalDate.now().plusDays(7));
        String follow = salesPlan.getAllOperator();
        if(follow != null && !"".equals(follow)){
            follow = follow+","+account;
        }else{
            follow = account;
        }
        salesPlan.setPrincipal(account);
        salesPlan.setPrincipalName(name);
        salesPlan.setAllOperator(follow);
        salesPlan.setSaleArrange("O");
//            salesPlan.setPlanStatus("first");
        try{
            customerDAO.save(customer);
            salesPlanDAO.save(salesPlan);

            Task task = new Task();
            task.setSalePlanID(salesPlan.getId());
            task.setCustomerCode(salesPlan.getCustomerCode());
            task.setCustomerName(salesPlan.getCustomerName());
            task.setPrincipal(user.getAccount());
            task.setJobName("销售排程");
            task.setDescription("一级预警提醒");
            task.setJobLevel(1);
            task.setDeadline(LocalDate.now());
            task.setRemainTime(0);
            task.setReceiver(user.getAccount());
            task.setExceedTime(0);
            task.setCreater(user.getAccount());
            task.setCreateDate(LocalDate.now());
            task.setAuthority("sb");
            taskDAO.save(task);

            taskService.saveTask(salePlanID , "销售排程"  , "foreArrangeSale" , LocalDate.now() , 1);

        }catch (Exception e){
            return e.getMessage();
        }

        return "OK";
    }

    /**
     * 当销售人员将该客户挂起时
     * 需要修改客户表与销售计划表中的数据
     * 1、销售计划表：清空销售人员信息
     * 2、客户表：清空销售人员信息，修改跟进状态
     */
    @RequestMapping("/giveUpCustomer")
    @Transactional
    public String giveup(String reqNum){
        try{
            SalesPlan salePlan = salesPlanDAO.getOne(reqNum);
            String customerID = salePlan.getCustomerCode();
            salePlan.setPrincipal(null);
            salePlan.setPrincipalName(null);
            salePlan.setUpdateDate(LocalDate.now());
            salesPlanDAO.save(salePlan);
            Customer customer = customerDAO.getOne(customerID);
            customer.setFollowStatus("O");
            customer.setFollowPerson(null);
            customer.setFollowName(null);
            customerDAO.save(customer);
            //删除客户的同时，删除与之相关的所有任务信息
            System.out.println(reqNum);
            taskDAO.deleteBySalePlanID(reqNum);
            System.out.println("删除任务表1");
            taskSumDAO.deleteBySalePlanID(reqNum);
            System.out.println("删除任务表2");

        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/deleteCustomer")
    public String toDeleteCustomer(String[] customerIDs){
        for(String customerID : customerIDs){
            try{
                customerService.deleteCustomer(customerID);
                SalesPlan salesPlan = salesPlanDAO.findByCustomerCode(customerID).get(0);
                taskDAO.deleteBySalePlanID(salesPlan.getId());
                System.out.println("CustomerPoolController");
                taskSumDAO.deleteBySalePlanID(salesPlan.getId());
            }catch (Exception e){
                return e.getMessage();
            }
        }
        return "OK";
    }
}
