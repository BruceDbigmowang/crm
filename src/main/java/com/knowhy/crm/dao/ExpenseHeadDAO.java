package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ExpenseHead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseHeadDAO extends JpaRepository<ExpenseHead , Integer> {

    List<ExpenseHead> findByCheckStatus(String status);
    List<ExpenseHead> findByCreater(String account);
    List<ExpenseHead> findByCreaterAndCheckStatus(String account , String status);
}
