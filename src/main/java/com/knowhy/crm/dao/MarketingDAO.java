package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Marketing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketingDAO extends JpaRepository<Marketing , Integer> {

    List<Marketing> findByStatus(String status);
    List<Marketing> findByUseStatus(String useStatus);
}
