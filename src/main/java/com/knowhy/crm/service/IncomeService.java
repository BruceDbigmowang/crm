package com.knowhy.crm.service;

import com.knowhy.crm.dao.IncomeDetailDAO;
import com.knowhy.crm.dao.IncomeHeadDAO;
import com.knowhy.crm.pojo.IncomeDetail;
import com.knowhy.crm.pojo.IncomeHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    @Autowired
    IncomeHeadDAO incomeHeadDAO;
    @Autowired
    IncomeDetailDAO incomeDetailDAO;

    public void wirteIncomeHead(IncomeHead incomeHead){
        incomeHeadDAO.save(incomeHead);
    }

    public List<IncomeHead> findByAccountAndStatus(String account , String status){
        return incomeHeadDAO.findByCreaterAndCheckStatus(account , status);
    }

    public void writeIncomeDetail(IncomeDetail incomeDetail){
        incomeDetailDAO.save(incomeDetail);
    }

    public IncomeHead findHeadById(int hid){
        return incomeHeadDAO.getOne(hid);
    }

    public IncomeDetail findDetailByHId(int hid){
        List<IncomeDetail> incomeDetailList = incomeDetailDAO.findByHid(hid);
        if(incomeDetailList == null || incomeDetailList.size() == 0){
            return null;
        }else{
            return incomeDetailList.get(0);
        }
    }
}
