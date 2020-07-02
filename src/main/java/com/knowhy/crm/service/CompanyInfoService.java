package com.knowhy.crm.service;

import com.knowhy.crm.dao.CompanyInfoDAO;
import com.knowhy.crm.dao.DataDetailDAO;
import com.knowhy.crm.dao.KnowMoreDAO;
import com.knowhy.crm.dao.ManufactureDAO;
import com.knowhy.crm.pojo.CompanyInfo;
import com.knowhy.crm.pojo.DataDetail;
import com.knowhy.crm.pojo.KnowMore;
import com.knowhy.crm.pojo.Manufacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyInfoService {
    @Autowired
    CompanyInfoDAO companyInfoDAO;
    @Autowired
    KnowMoreDAO knowMoreDAO;
    @Autowired
    ManufactureDAO manufactureDAO;
    @Autowired
    DataDetailDAO dataDetailDAO;

    public int completeSurvey(String companyName){
        List<CompanyInfo> companyInfoList = companyInfoDAO.findByCompanyName(companyName);
        if(companyInfoList == null){
            return 0;
        }else{
            return companyInfoList.size();
        }
    }

    public int sureSurvey(String company){
        List<CompanyInfo> companyInfoList = companyInfoDAO.findByCompanyName(company);
        if(companyInfoList == null || companyInfoList.size() == 0){
            return 1;
        }else{
            CompanyInfo companyInfo = companyInfoList.get(0);
            int cid = companyInfo.getId();
            String companyNature = companyInfo.getBussinessNature();
            if(companyNature == null){
                return 2;
            }else{
                String industryNature = companyInfo.getIndustryNature();
                if(industryNature == null){
                    return 3;
                }else{
                    KnowMore knowMore = knowMoreDAO.findByCid(cid);
                    if(knowMore == null ){
                        return 4;
                    }else{
                        List<Manufacture> manufactureList = manufactureDAO.findByCid(cid);
                        if( manufactureList == null || manufactureList.size() == 0){
                            return 5;
                        }else{
                            Manufacture manufacture = manufactureList.get(0);
                            String assetSource = manufacture.getResource();
                            if( assetSource == null){
                                return 5;
                            }else{
                                String buySource = manufacture.getBuySource();
                                if(buySource == null){
                                    return 5;
                                }else{
                                    List<DataDetail> dataDetailList = dataDetailDAO.findByCid(cid);
                                    if(dataDetailList == null || dataDetailList.size() == 0){
                                        return 6;
                                    }else{
                                        DataDetail dataDetail = dataDetailList.get(0);
                                        String appeal = dataDetail.getAppeal();
                                        if(appeal == null){
                                            return 6;
                                        }else{
                                            return 9;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getCid(String company){
        List<CompanyInfo> companyInfoList =companyInfoDAO.findByCompanyName(company);
        return companyInfoList.get(0).getId();
    }
}

