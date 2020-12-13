package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockDAO extends JpaRepository<Stock , Integer> {

    List<Stock> findBySalePlanID(String salePlanID);
}
