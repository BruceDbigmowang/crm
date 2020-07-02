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

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaveDataService {

    @Autowired
    CompanyInfoDAO companyInfoDAO;
    @Autowired
    KnowMoreDAO knowMoreDAO;
    @Autowired
    ManufactureDAO manufactureDAO;
    @Autowired
    DataDetailDAO dataDetailDAO;


    public void saveCompany(CompanyInfo companyInfo){
        System.out.println(companyInfo.getCompanyName());
        System.out.println(companyInfo.getCreateTime());
        companyInfoDAO.save(companyInfo);

    }

    public int getCid(CompanyInfo companyInfo){
        return companyInfoDAO.findByCompanyNameAndCreateDate(companyInfo.getCompanyName() , companyInfo.getCreateDate()).get(0).getId();
    }

    public void updateCompanyInfo(int cid , BigDecimal registerMoney , String establishTime , String bussinessNature , String sonCompanyNum , String employeeNum){
        CompanyInfo companyInfo = companyInfoDAO.getOne(cid);
        companyInfo.setRegisterMoney(registerMoney);
        companyInfo.setEstablishTime(establishTime);
        companyInfo.setBussinessNature(bussinessNature);
        companyInfo.setSonCompanyNum(sonCompanyNum);
        companyInfo.setEmployeeNum(employeeNum);
        companyInfoDAO.save(companyInfo);
    }

    public void updateCompanyTwice(int cid , String industry , String industryNature , String product , String picture){
        CompanyInfo companyInfo = companyInfoDAO.getOne(cid);
        companyInfo.setIndustry(industry);
        companyInfo.setIndustryNature(industryNature);
        companyInfo.setProduct(product);
        companyInfo.setPicture(picture);
        companyInfoDAO.save(companyInfo);
    }

    public void saveKnowMore(int cid , String means , String toolManage , String facilitator , String problem , String consume , String principle , String mobile , String email , String ask){
        KnowMore knowMore = new KnowMore();
        knowMore.setCid(cid);
        knowMore.setMeans(means);
        knowMore.setToolManage(toolManage);
        knowMore.setFacilitatorName(facilitator);
        knowMore.setProblem(problem);
        knowMore.setConsume(consume);
        knowMore.setPrinciple(principle);
        knowMore.setMobile(mobile);
        knowMore.setEmail(email);
        knowMore.setProgressAsk(ask);
        knowMoreDAO.save(knowMore);
    }

    public Manufacture findByCid(int cid){
        List<Manufacture> manufactureList = manufactureDAO.findByCid(cid);
        if(manufactureList == null || manufactureList.size() == 0){
            return null;
        }else{
            return manufactureList.get(0);
        }
    }

    public void save(Manufacture manufacture){
        manufactureDAO.save(manufacture);
    }

    public Manufacture findById(int mid){
        return manufactureDAO.getOne(mid);
    }

    public void saveOrUpdate(DataDetail dataDetail){
        dataDetailDAO.save(dataDetail);
    }

    public DataDetail getByCid(int cid){
        return dataDetailDAO.findByCid(cid).get(0);
    }

    public DataDetail getById(int did){
        return dataDetailDAO.getOne(did);
    }
}
