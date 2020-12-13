package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SaleMan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleManDAO extends JpaRepository<SaleMan , String> {

    List<SaleMan> findByAccountLike(String account);
    List<SaleMan> findByNameLike(String name);
}
