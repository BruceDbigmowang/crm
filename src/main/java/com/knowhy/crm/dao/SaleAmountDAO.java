package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SaleAmount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleAmountDAO extends JpaRepository<SaleAmount , Integer> {

    List<SaleAmount> findBySalePlanID(String salePlanID);
}
