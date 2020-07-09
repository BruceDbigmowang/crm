package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SalesPlanDAO extends JpaRepository<SalesPlan, String> {

    List<SalesPlan> findByCompany(String company);

    List<SalesPlan> findByCreateTimeBetween(Date start , Date end);
}
