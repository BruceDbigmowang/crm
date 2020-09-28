package com.knowhy.crm.service;

import com.knowhy.crm.dao.SaleCostDAO;
import com.knowhy.crm.pojo.SaleCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleCostService {
    @Autowired
    SaleCostDAO saleCostDAO;

    public void saveCost(SaleCost saleCost){
        saleCostDAO.save(saleCost);
    }

    public List<SaleCost> findByTypeAndPrincipal(String type , String principal){
        String costType = "%"+type+"%";
        String costPrincipal = "%"+principal+"%";
        return saleCostDAO.findByCostTypeLikeAndPrincipalLike(costType , costPrincipal);
    }
}
