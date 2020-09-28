package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyInfoDAO extends JpaRepository<CompanyInfo, Integer> {
    List<CompanyInfo> findByCompanyName(String companyName);

    List<CompanyInfo> findByCompanyNameAndCreateDate(String companyName , String createDate);

    List<CompanyInfo> findBySalePlanID(String salePlanID);
}
