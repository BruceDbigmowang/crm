package com.knowhy.crm.service;

import com.knowhy.crm.dao.ContractRecordDAO;
import com.knowhy.crm.dao.RecordDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.ContractRecord;
import com.knowhy.crm.pojo.Record;
import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContractFileService {

    @Autowired
    ContractRecordDAO contractRecordDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    RecordDAO recordDAO;

    @Transactional
    public void saveContractRecord(ContractRecord contractRecord , Record record, String salePlanID){
        String operator = contractRecord.getSteward();
        String assistant = contractRecord.getServer();
        contractRecordDAO.save(contractRecord);
        recordDAO.save(record);
        LocalDate lastTime = contractRecord.getDeadline();
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        salesPlan.setPlanStatus("ninth");
        salesPlan.setUpdateDate(LocalDate.now());
        salesPlan.setSpendTime(1);
        salesPlan.setDeadline(LocalDate.now().plusDays(1));
        salesPlan.setLastTime(lastTime);
        salesPlan.setOperator(operator);
        salesPlan.setAssistant(assistant);
        salesPlan.setOperateArrange("O");
        salesPlanDAO.save(salesPlan);
    }

    public ContractRecord findBySalePlan(String salePlanID){
        System.out.println(salePlanID);
        List<ContractRecord> contractRecordList = contractRecordDAO.findBySalePlanID(salePlanID);
        if(contractRecordList == null || contractRecordList.size() == 0){
            return null;
        }else{
            return contractRecordList.get(0);
        }
    }
}
