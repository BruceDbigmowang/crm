package com.knowhy.crm.service;

import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.dao.TravelPlanDAO;
import com.knowhy.crm.dao.TravelReqDAO;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.SalesPlan;
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
    public void arrangeCustomer(String[] ids , String saleManAccount , String saleManName){
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
        }
    }
}
