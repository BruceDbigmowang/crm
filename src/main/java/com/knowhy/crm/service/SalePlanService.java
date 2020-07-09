package com.knowhy.crm.service;

import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalePlanService {

    @Autowired
    SalesPlanDAO salesPlanDAO;

    public void createSalePlan(SalesPlan salesPlan){
        salesPlanDAO.save(salesPlan);
    }

    public SalesPlan findById(String salePlanNumber){
        return salesPlanDAO.getOne(salePlanNumber);
    }

    public List<SalesPlan> findAllSalePlan(){
        return salesPlanDAO.findAll();
    }

    public List<SalesPlan> findByCompany(String company){
        return salesPlanDAO.findByCompany(company);
    }

    public List<SalesPlan> findByCreateTime(Date start , Date end){
        return salesPlanDAO.findByCreateTimeBetween(start , end);
    }

}
