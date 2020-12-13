package com.knowhy.crm.controller;

import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.ExtraContract;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.service.ExtraContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@RestController
public class ExtraContractController {

    @Autowired
    ExtraContractService extraContractService;
    @Autowired
    SalesPlanDAO salesPlanDAO;

    @RequestMapping("/saveContractExtra")
    public String saveExtraFile(HttpSession session , String salePlanID , String contractCode , String contractName , String fileName , String note){
        IUser user = (IUser)session.getAttribute("user");
        ExtraContract contract = new ExtraContract();
        contract.setSalePlanID(salePlanID);
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        String customerCode = salesPlan.getCustomerCode();
        String customerName = salesPlan.getCustomerName();
        contract.setCustomerCode(customerCode);
        contract.setCustomerName(customerName);
        if(contractCode == null || "".equals(contractCode)){
            return "请填写补充合同编号";
        }else{
            contract.setContractCode(contractCode);
        }
        if(contractName == null || "".equals(contractName)){
            return "请填写补充合同名称";
        }else{
            contract.setContractName(contractName);
        }
        if(fileName == null || "".equals(fileName)){
            return "请选择补充合同附件";
        }else{
            contract.setFileName(fileName);
        }
        contract.setAccount(user.getAccount());
        contract.setName(user.getName());
        contract.setCreateDate(LocalDate.now());
        if(note == null || "".equals(note)){
            return "请填写合同说明";
        }else{
            contract.setNote(note);
        }
        extraContractService.saveextraFile(contract);
        return "OK";
    }
}
