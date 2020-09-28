package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SalesPlanDAO extends JpaRepository<SalesPlan, String> {

    List<SalesPlan> findByMakeDate(LocalDate makeDate);
    List<SalesPlan> findByCustomerName(String customerName);

    List<SalesPlan> findByPrincipalAndPlanStatus(String account , String status);
    List<SalesPlan> findByPlanStatus(String status);
    List<SalesPlan> findByCustomerCode(String customer);
}
