package com.konwhy.crm.dao;

import com.konwhy.crm.pojo.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyInfoDAO extends JpaRepository<CompanyInfo, Integer> {
    List<CompanyInfo> findByCompanyName(String companyName);
}
