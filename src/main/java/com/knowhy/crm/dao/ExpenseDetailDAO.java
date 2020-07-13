package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ExpenseDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseDetailDAO extends JpaRepository<ExpenseDetail , Integer> {

    List<ExpenseDetail> findByHeadId(int hId);
}
