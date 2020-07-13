package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.IncomeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeDetailDAO extends JpaRepository<IncomeDetail , Integer> {

    List<IncomeDetail> findByHid(int hid);
}
