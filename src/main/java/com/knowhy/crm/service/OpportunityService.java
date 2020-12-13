package com.knowhy.crm.service;

import com.knowhy.crm.dao.OpportunityDAO;
import com.knowhy.crm.pojo.Opportunity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpportunityService {
    @Autowired
    OpportunityDAO opportunityDAO;

    public void saveOpportunity(Opportunity opportunity){
        opportunityDAO.save(opportunity);
    }

    public List<Opportunity> getByAddress(String province , String city , String area){
        List<Opportunity> opportunityList = opportunityDAO.findByProvinceAndCityAndArea(province , city , area);
        return opportunityList;
    }

    public List<Opportunity> getByResource(String resource){
        String newResource = "%"+resource+"%";
        return opportunityDAO.findByResourceLike(newResource);
    }
}
