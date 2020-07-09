package com.knowhy.crm.service;

import com.knowhy.crm.dao.CompanyInfoDAO;
import com.knowhy.crm.dao.DataDetailDAO;
import com.knowhy.crm.dao.KnowMoreDAO;
import com.knowhy.crm.dao.ManufactureDAO;
import com.knowhy.crm.entity.CustomerDetail;
import com.knowhy.crm.pojo.CompanyInfo;
import com.knowhy.crm.pojo.DataDetail;
import com.knowhy.crm.pojo.KnowMore;
import com.knowhy.crm.pojo.Manufacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerDetailService {
    @Autowired
    CompanyInfoDAO companyInfoDAO;
    @Autowired
    KnowMoreDAO knowMoreDAO;
    @Autowired
    ManufactureDAO manufactureDAO;
    @Autowired
    DataDetailDAO dataDetailDAO;

    public CustomerDetail findDetailByCid(int cid){
        CompanyInfo companyInfo = companyInfoDAO.getOne(cid);
        CustomerDetail customerDetail = new CustomerDetail();
        String companyName = companyInfo.getCompanyName();
        if(companyName != null){
            customerDetail.setCompanyName(companyName);
        }
        String contact = companyInfo.getContact();
        if(contact != null){
            customerDetail.setContact(contact);
        }
        String contactWay = companyInfo.getContactWay();
        if(contactWay != null){
            customerDetail.setContactWay(contactWay);
        }
        String wechatNum = companyInfo.getWechatNum();
        if(wechatNum != null){
            customerDetail.setWechatNum(wechatNum);
        }
        BigDecimal registerMoney = companyInfo.getRegisterMoney();
        if(registerMoney != null){
            customerDetail.setRegisterMoney(registerMoney);
        }
        String establishTime = companyInfo.getEstablishTime();
        if(establishTime != null){
            customerDetail.setEstablishTime(establishTime);
        }
        String bussinessNature = companyInfo.getBussinessNature();
        if(bussinessNature != null){
            customerDetail.setBussniessNature(bussinessNature);
        }
        String sonCompanyNum = companyInfo.getSonCompanyNum();
        if(sonCompanyNum != null){
            customerDetail.setSonCompanyNum(sonCompanyNum);
        }
        String employeeNum = companyInfo.getEmployeeNum();
        if(employeeNum != null){
            customerDetail.setEmployeeNum(employeeNum);
        }
        String industry = companyInfo.getIndustry();
        if(industry != null){
            customerDetail.setIndustry(industry);
        }
        String industryNature = companyInfo.getIndustryNature();
        if(industryNature != null){
            customerDetail.setIndustryNature(industryNature);
        }
        String product = companyInfo.getProduct();
        if(product != null){
            customerDetail.setProduct(product);
        }
        List<KnowMore> knowMoreList = knowMoreDAO.findByCid(cid);
        if(knowMoreList != null && knowMoreList.size() != 0){
            KnowMore knowMore = knowMoreList.get(0);
            String means = knowMore.getMeans();
            if(means != null){
                customerDetail.setMeans(means);
            }
            String toolManage = knowMore.getToolManage();
            if(toolManage != null){
                customerDetail.setToolManage(toolManage);
            }
            String facilitatorName = knowMore.getFacilitatorName();
            if(facilitatorName != null){
                customerDetail.setFacilitatorName(facilitatorName);
            }
            String problem = knowMore.getProblem();
            if(problem != null){
                customerDetail.setProblem(problem);
            }
            String consume = knowMore.getConsume();
            if(consume != null){
                customerDetail.setConsume(consume);
            }
            String principle = knowMore.getPrinciple();
            if(principle != null){
                customerDetail.setPrinciple(principle);
            }
            String mobile = knowMore.getMobile();
            if(mobile != null){
                customerDetail.setMobile(mobile);
            }
            String email = knowMore.getEmail();
            if(email != null){
                customerDetail.setEmail(email);
            }
            String progressAsk = knowMore.getProgressAsk();
            if(progressAsk != null){
                customerDetail.setProgressAsk(progressAsk);
            }
        }
        List<Manufacture> manufactureList = manufactureDAO.findByCid(cid);
        if(manufactureList != null && manufactureList.size() != 0){
            Manufacture manufacture = manufactureList.get(0);
            String production = manufacture.getProduction();
            if(production != null){
                customerDetail.setProduction(production);
            }
            String market = manufacture.getMarket();
            if(market != null){
                customerDetail.setMarket(market);
            }
            String competitor = manufacture.getCompetitor();
            if(competitor != null){
                customerDetail.setCompetitor(competitor);
            }
            String project = manufacture.getProject();
            if(project != null){
                customerDetail.setProject(project);
            }
            String assetType = manufacture.getAssetType();
            if(assetType != null){
                customerDetail.setAssetType(assetType);
            }
            String resource = manufacture.getResource();
            if(resource != null){
                customerDetail.setResource(resource);
            }
            String brand = manufacture.getBrand();
            if(brand != null){
                customerDetail.setBrand(brand);
            }
            String amount = manufacture.getAmount();
            if(amount != null){
                customerDetail.setAmount(amount);
            }
            String life = manufacture.getLife();
            if(life != null){
                customerDetail.setLife(life);
            }
            String form = manufacture.getForm();
            if(form != null){
                customerDetail.setForm(form);
            }
            String buyMoney = manufacture.getBuyMoney();
            if(buyMoney != null){
                customerDetail.setBuyMoney(buyMoney);
            }
            String buySource = manufacture.getBuySource();
            if(buySource != null){
                customerDetail.setBuySource(buySource);
            }
            String buyModel = manufacture.getBuyModel();
            if(buyModel != null){
                customerDetail.setBuyModel(buyModel);
            }
            String buyBrand = manufacture.getBuyBrand();
            if(buyBrand != null){
                customerDetail.setBuyBrand(buyBrand);
            }
            String manageModel = manufacture.getManageModel();
            if(manageModel != null){
                customerDetail.setManageModel(manageModel);
            }
        }
        List<DataDetail> dataDetailList = dataDetailDAO.findByCid(cid);
        if(dataDetailList != null && dataDetailList.size() != 0){
            DataDetail dataDetail = dataDetailList.get(0);
            BigDecimal ztzb = dataDetail.getZtzb();
            if(ztzb != null){
                customerDetail.setZtzb(ztzb);
            }
            BigDecimal xdzb = dataDetail.getXdzb();
            if(xdzb != null){
                customerDetail.setXdzb(xdzb);
            }
            BigDecimal szzb = dataDetail.getSzzb();
            if(szzb != null){
                customerDetail.setSzzb(szzb);
            }
            BigDecimal qtOne = dataDetail.getQtOne();
            if(qtOne != null){
                customerDetail.setQtOne(qtOne);
            }
            BigDecimal cdpzb = dataDetail.getCdpzb();
            if(cdpzb != null){
                customerDetail.setCdpzb(cdpzb);
            }
            BigDecimal xdpzb = dataDetail.getXdpzb();
            if(xdpzb != null){
                customerDetail.setXdpzb(xdpzb);
            }
            BigDecimal tdpzb = dataDetail.getTdpzb();
            if(tdpzb != null){
                customerDetail.setTdpzb(tdpzb);
            }
            BigDecimal qtTwo = dataDetail.getQtTwo();
            if(qtTwo != null){
                customerDetail.setQtTwo(qtTwo);
            }
            BigDecimal cbn = dataDetail.getCbn();
            if(cbn != null){
                customerDetail.setCbn(cbn);
            }
            BigDecimal pcd = dataDetail.getPcd();
            if(pcd != null){
                customerDetail.setPcd(pcd);
            }
            BigDecimal hjzb = dataDetail.getHjzb();
            if(hjzb != null){
                customerDetail.setHjzb(hjzb);
            }
            String hasGSG = dataDetail.getHasGSG();
            if(hasGSG != null){
                customerDetail.setHasGSG(hasGSG);
            }
            String gsgBrand = dataDetail.getGsgBrand();
            if(gsgBrand != null ){
                customerDetail.setGsgBrand(gsgBrand);
            }
            String stockMoney = dataDetail.getStockMoney();
            if(stockMoney != null){
                customerDetail.setStockMoney(stockMoney);
            }
            BigDecimal stockOne = dataDetail.getStockOne();
            if(stockOne != null){
                customerDetail.setStockOne(stockOne);
            }
            BigDecimal stockTwo = dataDetail.getStockTwo();
            if(stockTwo != null){
                customerDetail.setStockTwo(stockTwo);
            }
            BigDecimal stockThree = dataDetail.getStockThree();
            if(stockThree != null){
                customerDetail.setStockThree(stockThree);
            }
            BigDecimal normTool = dataDetail.getNormTool();
            if(normTool != null){
                customerDetail.setNormTool(normTool);
            }
            BigDecimal unnormTool = dataDetail.getUnnormTool();
            if(unnormTool != null){
                customerDetail.setUnnormTool(unnormTool);
            }
            String hasERP = dataDetail.getHasERP();
            if(hasERP != null){
                customerDetail.setHasERP(hasERP);
            }
            String erpBrand = dataDetail.getErpBrand();
            if(erpBrand!=null){
                customerDetail.setErpBrand(erpBrand);
            }
            String hasMES = dataDetail.getHasMES();
            if(hasMES != null ){
                customerDetail.setHasMES(hasMES);
            }
            String mesBrand = dataDetail.getMesBrand();
            if(mesBrand != null){
                customerDetail.setMesBrand(mesBrand);
            }
            String shiftManage = dataDetail.getShiftManage();
            if(shiftManage != null){
                customerDetail.setShiftManage(shiftManage);
            }
            String productRest = dataDetail.getProductRest();
            if(productRest != null){
                customerDetail.setProductRest(productRest);
            }
            Integer productNum = dataDetail.getProductNum();
            if(productNum != null){
                customerDetail.setProductNum(productNum);
            }
            Integer stockPerson = dataDetail.getStockPerson();
            if(stockPerson != null){
                customerDetail.setStockPerson(stockPerson);
            }
            Integer grantPerson = dataDetail.getGrantPerson();
            if(grantPerson != null){
                customerDetail.setGrantPerson(grantPerson);
            }
            String grantWay = dataDetail.getGrantWay();
            if(grantWay != null){
                customerDetail.setGrantWay(grantWay);
            }
            String returnWay = dataDetail.getReturnWay();
            if(returnWay != null){
                customerDetail.setReturnWay(returnWay);
            }
            String payCycle = dataDetail.getPayCycle();
            if(payCycle != null){
                customerDetail.setPayCycle(payCycle);
            }
            String payWay = dataDetail.getPayWay();
            if(payWay != null){
                customerDetail.setPayWay(payWay);
            }
            String repair = dataDetail.getRepair();
            if (repair != null){
                customerDetail.setRepair(repair);
            }
            BigDecimal repairSpend = dataDetail.getRepairSpend();
            if(repairSpend != null){
                customerDetail.setRepairSpend(repairSpend);
            }
            String optimize = dataDetail.getOptimize();
            if(optimize != null){
                customerDetail.setOptimize(optimize);
            }
            String appeal = dataDetail.getAppeal();
            if(appeal != null){
                customerDetail.setAppeal(appeal);
            }
        }
        return customerDetail;
    }
}
