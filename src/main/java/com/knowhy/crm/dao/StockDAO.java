package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockDAO extends JpaRepository<Stock , Integer> {
}
