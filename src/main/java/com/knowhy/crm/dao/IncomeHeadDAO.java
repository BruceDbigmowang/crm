package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.IncomeHead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeHeadDAO extends JpaRepository<IncomeHead , Integer> {

    List<IncomeHead> findByCreaterAndCheckStatus(String creater , String status);
}
