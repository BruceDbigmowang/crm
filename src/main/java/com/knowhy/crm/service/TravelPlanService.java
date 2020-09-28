package com.knowhy.crm.service;

import com.knowhy.crm.dao.TravelPlanDAO;
import com.knowhy.crm.pojo.TravelPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelPlanService {

    @Autowired
    TravelPlanDAO travelPlanDAO;

    public void save(TravelPlan travelPlan){
        travelPlanDAO.save(travelPlan);
    }

    public List<TravelPlan> findAll(){
        return travelPlanDAO.findAll();
    }

    public List<TravelPlan> findBySalePlanNum(String salePlanNum){
        return travelPlanDAO.findBySalePlanNumber(salePlanNum);
    }

    public List<TravelPlan> findByCustomerName(String customer){
        return travelPlanDAO.findByCustomer(customer);
    }
}
