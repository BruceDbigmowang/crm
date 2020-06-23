package com.knowhy.crm.service;

import com.knowhy.crm.dao.CompanyInfoDAO;
import com.knowhy.crm.pojo.CompanyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyInfoService {
    @Autowired
    CompanyInfoDAO companyInfoDAO;

    public int completeSurvey(String companyName){
        List<CompanyInfo> companyInfoList = companyInfoDAO.findByCompanyName(companyName);
        if(companyInfoList == null){
            return 0;
        }else{
            return companyInfoList.size();
        }
    }
}
