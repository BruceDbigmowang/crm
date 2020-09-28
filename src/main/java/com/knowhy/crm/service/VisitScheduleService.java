package com.knowhy.crm.service;

import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.dao.VisitScheduleDAO;
import com.knowhy.crm.mapper.CrmVisitscheduleMapper;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.pojo.VisitSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class VisitScheduleService {

    @Autowired
    VisitScheduleDAO visitScheduleDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;

    public void save(VisitSchedule visitSchedule){
        visitScheduleDAO.save(visitSchedule);
    }

    public String checkSalePlanNo(String salePlanNo){
        boolean exist = salesPlanDAO.existsById(salePlanNo);
        if(!exist){
            return "销售计划编号不存在";
        }else{
            SalesPlan salesPlan = salesPlanDAO.getOne(salePlanNo);
            if(salesPlan.getPlanStatus() != null && salesPlan.getPlanStatus().equals("E")){
                return "该销售计划已完成";
            }else{
                return "OK";
            }
        }
    }

    public boolean isSalePlanNum(String salePlanNum){
        return salesPlanDAO.existsById(salePlanNum);
    }

    public List<VisitSchedule> findByNumber(String num){
        return visitScheduleDAO.findByNumber(num);
    }

    public List<VisitSchedule> findByName(String name){
        return visitScheduleDAO.findByVname(name);
    }


}
