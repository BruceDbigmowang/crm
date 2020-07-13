package com.knowhy.crm.service;

import com.knowhy.crm.dao.ExpenseDetailDAO;
import com.knowhy.crm.dao.ExpenseHeadDAO;
import com.knowhy.crm.pojo.ExpenseDetail;
import com.knowhy.crm.pojo.ExpenseHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    ExpenseDetailDAO expenseDetailDAO;
    @Autowired
    ExpenseHeadDAO expenseHeadDAO;

    public void saveExpanse(ExpenseHead expenseHead ){
        expenseHeadDAO.save(expenseHead);
    }

    public List<ExpenseHead> findAllHead(){
        return expenseHeadDAO.findAll();
    }

    public void saveExpenseMore(ExpenseDetail expenseDetail){
        expenseDetailDAO.save(expenseDetail);
    }

    public ExpenseHead getOne(int hId){
        return expenseHeadDAO.getOne(hId);
    }

    public List<ExpenseHead> getAllApprove(){
        String status = "O";
        return expenseHeadDAO.findByCheckStatus(status);
    }

    public List<ExpenseHead> findHeadByAccount(String account){
        return expenseHeadDAO.findByCreater(account);
    }
    public List<ExpenseHead> findHeadByAccountAndStatus(String account , String status){
        return expenseHeadDAO.findByCreaterAndCheckStatus(account , status);
    }

    public ExpenseDetail findDetailByHID(int hId){
        List<ExpenseDetail> expenseDetailList = expenseDetailDAO.findByHeadId(hId);
        if(expenseDetailList == null || expenseDetailList.size() == 0){
            return null;
        }else{
            return expenseDetailList.get(0);
        }
    }
}
