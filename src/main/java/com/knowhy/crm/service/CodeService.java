package com.knowhy.crm.service;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CodeService {
    @Autowired
    BussinessTravelDAO bussinessTravelDAO;
    @Autowired
    TravelReqDAO travelReqDAO;
    @Autowired
    ReimburseDAO reimburseDAO;
    @Autowired
    SchemeFlowDAO schemeFlowDAO;
    @Autowired
    ContractFlowDAO contractFlowDAO;
    @Autowired
    PostponeDAO postponeDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * 自动生成出差申请单号
     * @return
     */
    public String generateTravelId(){
        String travel = "knowhy0T"+sdf.format(new Date());
        List<BussinessTravel> travels = bussinessTravelDAO.findByMakeDate(LocalDate.now());
        String len = String.valueOf(travels.size() + 1);
        for(int i = len.length() ; i < 4 ; i++){
            len = "0"+len;
        }
        travel = travel+len;
        return travel;
    }

    /**
     * 自动生成审批流程单号
     * @param type
     * @return
     */
    public String generateReqId(String type){
        String code = "knowhy1"+type+sdf.format(new Date());
        List<TravelReq> reqs = travelReqDAO.findByMakeDate(LocalDate.now());
        String len = String.valueOf(reqs.size() + 1);
        for(int i = len.length() ; i < 4 ; i++){
            len = "0"+len;
        }
        code = code + len;
        return code;
    }

    /**
     * 自动生成报销ID
     */
    public String generateReimburseID(){
        String rId = "knowhy0R"+sdf.format(new Date());
        List<Reimburse> reimburses = reimburseDAO.findByMakeDate(LocalDate.now());
        String len = String.valueOf(reimburses.size() + 1);
        for(int i = len.length() ; i < 4 ; i++){
            len = "0"+len;
        }
        rId = rId + len;
        return rId;
    }

    /**
     * 自动生成方案流程的ID
     */
    public String generateSchemeFlowID(){
        String schemeID = "knowhy0S"+sdf.format(new Date());
        List<SchemeFlow> flows = schemeFlowDAO.findByMakeDate(LocalDate.now());
        String len = String.valueOf(flows.size() + 1);
        for(int i = len.length() ; i < 4 ; i++){
            len = "0"+len;
        }
        schemeID = schemeID + len;
        return schemeID;
    }

    /**
     * 自动生成合同流程的ID
     */
    public String generateContractFlowID(){
        String contract = "knowhy0C"+sdf.format(new Date());
        List<ContractFlow> flows = contractFlowDAO.findByMakeDate(LocalDate.now());
        String len = String.valueOf(flows.size() + 1);
        for(int i = len.length() ; i < 4 ; i++){
            len = "0"+len;
        }
        contract = contract + len;
        return contract;
    }

    /**
     * 自动生成延期流程的ID
     */
    public String generatePostponseFlowID(){
        String postPonseCode = "knowhy0P"+sdf.format(new Date());
        List<Postpone> pones = postponeDAO.findByMakeDate(LocalDate.now());
        String len = String.valueOf(pones.size() + 1);
        for(int i = len.length() ; i < 4 ; i++){
            len = "0"+len;
        }
        postPonseCode = postPonseCode + len;
        return postPonseCode;
    }
}
