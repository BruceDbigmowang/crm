package com.knowhy.crm.controller;

import com.knowhy.crm.dao.DeptGroupDAO;
import com.knowhy.crm.dao.IUserDAO;
import com.knowhy.crm.dao.TaskDAO;
import com.knowhy.crm.dao.TaskSumDAO;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.ContractFileService;
import com.knowhy.crm.service.SalePlanService;
import com.knowhy.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class FileRecordController {

    @Autowired
    ContractFileService contractFileService;
    @Autowired
    SalePlanService salePlanService;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    DeptGroupDAO deptGroupDAO;
    @Autowired
    IUserDAO iUserDAO;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/saveAddFile")
    @Transactional
    public String saveFile(HttpSession session , String salePlanID , String contractFileID , String contractFileName  , String fileName, String productCode , String productName , String serverContent , String trainAsk , String serverAsk , String clearWay , String currency , String parities , String annualMoney , String concludeDate , String effectiveDate , String deadline , String contractExplain , String price , String stewards , String technicist , String operator, String servers , String principals , String cabinetAmount , String cabinetNum , String cabinetSpend , String useTDMP , String TDMPSpend , String payment , String otherServer , String remind , String dispose){
        IUser user = (IUser)session.getAttribute("user");
        ContractRecord contract = new ContractRecord();
        contract.setSalePlanID(salePlanID);
        SalesPlan salesPlan = salePlanService.findById(salePlanID);
        String customerCode = salesPlan.getCustomerCode();
        String customerName = salesPlan.getCustomerName();
        contract.setCustomerCode(customerCode);
        contract.setCustomerName(customerName);
        if(contractFileID == null || "".equals(contractFileID)){
            return "请填写合同编号";
        }else{
            contract.setContractFileID(contractFileID);
        }
        if(contractFileName == null || "".equals(contractFileName)){
            return "请填写合同名称";
        }else{
            contract.setContractFileName(contractFileName);
        }
        if(fileName == null || "".equals(fileName)){
            return "请选择合同附件";
        }else{
            contract.setFileName(fileName);
        }
        if(productCode == null || "".equals(productCode)){
            return "请填写服务产品编码";
        }else{
            contract.setProductCode(productCode);
        }
        if(productName == null || "".equals(productName)){
            return "请填写服务产品名称";
        }else{
            contract.setProductName(productName);
        }
        if(serverContent.equals("0")){
            return "请选择服务内容";
        }else{
            contract.setServerContent(serverContent);
        }
        if(trainAsk == null || "".equals(trainAsk)){
            return "请选择技术培训周期";
        }else{
            int train = Integer.parseInt(trainAsk);
            contract.setTrainAsk(train);
        }
        if(serverAsk == null || "".equals(serverAsk)){
            return "请选择技术到场服务周期";
        }else{
            int service = Integer.parseInt(serverAsk);
            contract.setServerAsk(service);
        }
        if(clearWay.equals("0")){
            return "请选择结算方式";
        }else{
            contract.setClearWay(clearWay);
        }
        if(currency == null || "".equals(currency)){
            return "请填写币种";
        }else{
            contract.setCurrency(currency);
        }
        if(parities == null || "".equals(parities)){
            return "请填写汇率";
        }else{
            try{
                BigDecimal parity = new BigDecimal(parities);
                contract.setParities(parity);
            }catch (Exception e){
                return "汇率填写出错（只能填写数字）";
            }
        }
        if(annualMoney == null || "".equals(annualMoney)){
            return "请填写合同年度金额";
        }else{
            try{
                BigDecimal contractMoney = new BigDecimal(annualMoney);
                contract.setAnnualMoney(contractMoney);
            }catch (Exception e){
                return "合同年度金额填写出错（只能填写数字）";
            }
        }
        LocalDate writeDate = null;
        if(concludeDate == null || "".equals(concludeDate)){
            return "请选择合同签订日期";
        }else{
            try{
                writeDate = LocalDate.parse(concludeDate , fmt);
                contract.setConcludeDate(writeDate);
            }catch (Exception e){
                return "签订日期格式错误";
            }
        }
        LocalDate effective = null;
        if(effectiveDate == null || "".equals(effectiveDate)){
            return "请选择合同生效日期";
        }else{
            try{
                effective = LocalDate.parse(effectiveDate , fmt);
                if(effective.isBefore(writeDate)){
                    return "合同生效日期应晚于合同签订日期";
                }
                contract.setEffectiveDate(effective);
            }catch (Exception e){
                return "生效日期格式出错";
            }
        }
        LocalDate dead = null;
        LocalDate lastTime = writeDate.plusYears(1);
        if(deadline == null || "".equals(deadline)){
            return "请选择合同截止日期";
        }else{
            try{
                dead = LocalDate.parse(deadline , fmt);
                if(lastTime.isAfter(dead)){
                    return "合同签订的有效期最低一年";
                }
                contract.setDeadline(dead);
            }catch (Exception e){
                return "截止日期格式出错";
            }
        }
        contract.setAccount(user.getAccount());
        contract.setName(user.getName());
        contract.setContractStatus("Y");
        if(contractExplain == null || "".equals(contractExplain)){
            return "请填写合同说明";
        }else{
            contract.setContractExplain(contractExplain);
        }
        contract.setCreateDate(LocalDate.now());
        if(price == null || "".equals(price)){
            return "请填写价格约定";
        }else{
            contract.setPrice(price);
        }
        if(stewards == null || "".equals(stewards)){
            return "请选择供应链管家";
        }else{
            contract.setOperator(stewards);
        }
        if(technicist == null || "".equals(technicist)){
            return "请选择技术人员";
        }else{
            contract.setTechnicist(technicist);
        }

        if(operator == null || "".equals(operator)){
            return "请选择运营人员";
        }else{
            contract.setSteward(operator);
        }
        if(servers == null || "".equals(servers)){
            return "请选择销售助理";
        }else{
            contract.setServer(servers);
        }
        if(principals == null || "".equals(principals)){
            return "请选择区域负责人";
        }else{
            contract.setPrincipal(principals);
        }
        if(cabinetAmount == null || "".equals(cabinetAmount)){
            return "请填写刀具柜数量";
        }else{
            int amount = Integer.parseInt(cabinetAmount);
            contract.setCabineAmount(amount);
            if(amount > 0){
                if(cabinetNum == null || "".equals(cabinetNum)){
                    return "请填写关联刀具柜编号";
                }else{
                    contract.setCabineNum(cabinetNum);
                }
                if(cabinetSpend == null || "".equals(cabinetSpend)){
                    return "请填写刀具柜费用";
                }else{
                    try{
                        BigDecimal spend = new BigDecimal(cabinetSpend);
                        contract.setCabineSpend(spend);
                    }catch (Exception e){
                        return "刀具柜费用填写错误（只能填写数字）";
                    }
                }
            }
        }
        if(useTDMP == null || "".equals(useTDMP)){
            return "请选择是否使用TDMP";
        }else{
            contract.setUseTDMP(useTDMP);
            if(useTDMP.equals("是")){
                if(TDMPSpend == null || "".equals(TDMPSpend)){
                    return "请输入TDMP费用";
                }else{
                    try{
                        BigDecimal spend = new BigDecimal(TDMPSpend);
                        contract.setTdmpSpend(spend);
                    }catch (Exception e){
                        return "TDMP费用填写出错（只能填写数字）";
                    }
                }
            }
        }
        if(payment == null || "".equals(payment)){
            return "请填写账期";
        }else{
            try{
                int pay = Integer.parseInt(payment);
                contract.setPayment(pay);
            }catch (Exception e){
                return "账期填写错误（只能填写数字）";
            }
        }
        if(otherServer != null && !"".equals(otherServer)){
            contract.setOtherServer(otherServer);
        }
        if(remind == null || "".equals(remind)){
            return "请填写合同续约提醒";
        }else{
            try{
                int reminds = Integer.parseInt(remind);
                contract.setRemind(reminds);
            }catch (Exception e){
                return "合同续约提醒时间填写错误（只能填写数字）";
            }
        }
        if(dispose == null || "".equals(dispose)){
            return "请填写呆滞品处理";
        }else{
            try{
                int disposes = Integer.parseInt(dispose);
                contract.setDispose(disposes);
            }catch (Exception e){
                return "呆滞品填写错误（只能填写数字）";
            }
        }
        Record record = new Record();
        record.setSalePlanID(salePlanID);
        record.setCustomerCode(customerCode);
        record.setCustomerName(customerName);
        record.setContractFileID(contractFileID);
        record.setContractFileName(contractFileName);
        record.setFileName(fileName);
        record.setAccount(user.getAccount());
        record.setName(user.getName());
        record.setCreateDate(LocalDate.now());
        contractFileService.saveContractRecord(contract , record , salePlanID);

        taskDAO.deleteBySalePlanID(salePlanID);
        System.out.println("FileRecordController");
        Task task2 = new Task();
        task2.setSalePlanID(salesPlan.getId());
        task2.setCustomerCode(salesPlan.getCustomerCode());
        task2.setCustomerName(salesPlan.getCustomerName());
        task2.setPrincipal(user.getAccount());
        task2.setJobName("计划排程");
        task2.setDescription("一级预警提醒");
        task2.setJobLevel(1);
        task2.setDeadline(LocalDate.now());
        task2.setRemainTime(0);
        task2.setReceiver(user.getAccount());
        task2.setExceedTime(0);
        task2.setCreater(user.getAccount());
        task2.setCreateDate(LocalDate.now());
        task2.setAuthority("sb");
        taskDAO.save(task2);
        taskSumDAO.deleteBySalePlanID(salePlanID);
        taskService.saveTaskForAss(salePlanID , "计划排程"  , "foreSchedule" , LocalDate.now() , 1);

        return "OK";
    }

    @RequestMapping("/showAllContractInfo")
    public ContractRecord findBySalePlanID(String salePlanID){
        ContractRecord contractRecord = contractFileService.findBySalePlan(salePlanID);
        if(contractRecord != null){
            String stewardAccount = contractRecord.getSteward();
            if(stewardAccount != null && !"".equals(stewardAccount)){
                IUser user = iUserDAO.getOne(stewardAccount);
                contractRecord.setSteward(user.getName());
            }else{
                contractRecord.setSteward("");
            }
            String technicistAccount = contractRecord.getTechnicist();
            if(technicistAccount != null && !"".equals(technicistAccount)){
                IUser user = iUserDAO.getOne(technicistAccount);
                contractRecord.setTechnicist(user.getName());
            }else{
                contractRecord.setTechnicist("");
            }
            String assistant = contractRecord.getServer();
            if(assistant != null && !"".equals(assistant)){
                IUser user = iUserDAO.getOne(assistant);
                contractRecord.setServer(user.getName());
            }else{
                contractRecord.setServer("");
            }
            String operatorAccount = contractRecord.getOperator();
            if(operatorAccount != null && !"".equals(operatorAccount)){
                IUser user = iUserDAO.getOne(operatorAccount);
                contractRecord.setOperator(user.getName());
            }else{
                contractRecord.setOperator("");
            }
        }
        return contractRecord;
    }
}
