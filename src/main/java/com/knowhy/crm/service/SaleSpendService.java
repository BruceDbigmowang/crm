package com.knowhy.crm.service;

import com.knowhy.crm.dao.IUserDAO;
import com.knowhy.crm.dao.SaleSpendDAO;
import com.knowhy.crm.pojo.SaleSpend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleSpendService {
    @Autowired
    SaleSpendDAO saleSpendDAO;

    public void saveSaleSpend(SaleSpend saleSpend){
        saleSpendDAO.save(saleSpend);
    }

    public List<SaleSpend> findSpendByParams(String type , String principal){
        type = "%"+type+"%";
        principal = "%"+principal+"%";
        return saleSpendDAO.findByCostTypeLikeAndPrincipalLike(type , principal);
    }
}
