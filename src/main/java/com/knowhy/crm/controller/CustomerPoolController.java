package com.knowhy.crm.controller;

import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.dao.SaleManDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.SaleMan;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/loadAllSaleMan")
    public List<SaleMan> getAllSaleMan(){
        return saleManDAO.findAll();
    }

    @RequestMapping("/arrangeCustomer")
    public String toArrange(String ids , String account){
        if(account.equals("0")){
            return "请选择销售人员";
        }
        String name = saleManDAO.getOne(account).getName();
        if(ids == null || "".equals(ids)){
            return "请选择客户";
        }else{
            String[] IDS = ids.split(",");
//            try{
                customerService.arrangeCustomer(IDS , account , name);
//            }catch (Exception e){
//                return e.getMessage();
//            }
        }
        return "分配成功";
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
            salesPlanDAO.save(salePlan);
            Customer customer = customerDAO.getOne(customerID);
            customer.setFollowStatus("O");
            customer.setFollowPerson(null);
            customer.setFollowName(null);
            customerDAO.save(customer);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }
}
