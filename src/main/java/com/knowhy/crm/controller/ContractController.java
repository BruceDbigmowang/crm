package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.CodeService;
import com.knowhy.crm.service.TaskService;
import com.knowhy.crm.service.TravelReqService;
import com.knowhy.crm.util.SendRemind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ContractController {
    @Autowired
    SalesPlanDAO planDAO;
    @Autowired
    TravelReqDAO reqDAO;
    @Autowired
    TravelReqService reqService;
    @Autowired
    ContractFlowDAO flowDAO;
    @Autowired
    ContractFileDAO fileDAO;
    @Autowired
    CodeService codeService;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    RecordDAO recordDAO;
    @Autowired
    ExtraContractDAO extraContractDAO;
    @Autowired
    ArrangeRecordDAO arrangeRecordDAO;
    /**
     * 1、拟定合同后，上传合同附件
     * 自动生成审批流程
     */
    @RequestMapping("/saveContractFirst")
    public String createFirstContract(String salePlanID , String fileName , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String maker = user.getAccount();
        String makerName = user.getName();
        String contractFlowID = codeService.generateContractFlowID();
        SalesPlan plan = planDAO.getOne(salePlanID);
        String customerCode = plan.getCustomerCode();
        String customerName = plan.getCustomerName();
        ContractFlow flow = new ContractFlow();
        flow.setContractId(contractFlowID);
        flow.setReqNum(salePlanID);
        flow.setCustomerCode(customerCode);
        flow.setCustomerName(customerName);
        flow.setFileName(fileName);
        flow.setMaker(maker);
        flow.setMakerName(makerName);
        flow.setMakeDate(LocalDate.now());
        flow.setStep("first");
        flow.setStepName("初步合同");

        TravelReq req = new TravelReq();
        String reqID = codeService.generateReqId("C");
        req.setReqNum(reqID);
        req.setLeafId(contractFlowID);
        req.setAccount(maker);
        req.setName(makerName);
        req.setType("contractF");
        req.setTypeName("初步合同");
        req.setReason(makerName+"上传初步合同");
        req.setStatus("first");
        req.setStatusName("审批中");
        req.setMaker(maker);
        req.setMakerName(makerName);
        req.setMakeDate(LocalDate.now());

        try{
            flowDAO.save(flow);
            reqDAO.save(req);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }


    /**
     * 2、审批初步合同
     * 通过，保存合同附件并进入下一阶段
     * 不通过，退回重新上传
     */
    @RequestMapping("/setContractFirstPass")
    @Transactional
    public String passContractFirst(String reqNum , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int next = reqService.getNextNode(account);
        TravelReq req = reqDAO.getOne(reqNum);
        String flowID = req.getLeafId();
        ContractFlow flow = flowDAO.getOne(flowID);
        String salePlanID = flow.getReqNum();
        String customerCode = flow.getCustomerCode();
        String customerName = flow.getCustomerName();
        String fileName = flow.getFileName();
        String maker = flow.getMaker();
        String makerName = flow.getMakerName();

        try{
            switch (next){
                case -1:
                    req.setStatus("pass");
                    req.setStatusName("已审批");
                    reqDAO.save(req);
                    ContractFile file = new ContractFile();
                    file.setReqNum(salePlanID);
                    file.setCustomerCode(customerCode);
                    file.setCustomerName(customerName);
                    file.setFirstFile(fileName);
                    file.setFirstMaker(maker);
                    file.setFirstMakerName(makerName);
                    file.setFirstMakeDate(LocalDate.now());
                    fileDAO.save(file);
                    List<TaskSum> sumList = taskSumDAO.findBySalePlanIDAndTask(salePlanID , "合同签订");
                    SalesPlan plan = planDAO.getOne(salePlanID);
                    plan.setPlanStatus("seventh");
                    plan.setUpdateDate(LocalDate.now());
                    plan.setSpendTime(21);
                    plan.setDeadline(sumList.get(0).getDeadline());
                    planDAO.save(plan);

                    List<ArrangeRecord> arrangeRecordList = arrangeRecordDAO.findBySalePlanIDAndStepAndType(salePlanID , "合同交流" , "sale");
                    if(!arrangeRecordList.isEmpty()){
                        ArrangeRecord arrangeRecord = arrangeRecordList.get(0);
                        arrangeRecord.setCompleteStatus("C");
                        arrangeRecordDAO.save(arrangeRecord);
                    }

                    taskDAO.deleteBySalePlanIDAndJobName(salePlanID , "合同交流");
                    taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , "合同交流");
//                    taskService.saveTask(salePlanID , "合同签订" , "foreContractLater", LocalDate.now().plusDays(21) , 3);

                    break;
                case 0:
                    break;
                case 2:
                    req.setStatus("second");
                    reqDAO.save(req);
                    break;
                case 3:
                    req.setStatus("third");
                    reqDAO.save(req);
                    break;
                case 4:
                    req.setStatus("fourth");
                    reqDAO.save(req);
                    break;
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/setContractFirstRefuse")
    public String refuseContractFirst(String reqNum , String note){
        try{
            TravelReq req = reqDAO.getOne(reqNum);
            req.setStatus("refuse");
            req.setStatusName("已退回");
            if(note == null || "".equals(note)){
                return "请填写退回原因";
            }else{
                req.setNote(note);
            }
            reqDAO.save(req);
            String contractId = req.getLeafId();
            ContractFlow contractFlow = flowDAO.getOne(contractId);
            contractFlow.setContractStatus("refuse");
            contractFlow.setReason(note);
            flowDAO.save(contractFlow);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 3、与客户商议后 达成最终合同
     * 上传最终合同，自动生成合同审批流程
     */
    @RequestMapping("/saveContractSecond")
    @Transactional
    public String createContractSecond(String salePlanID , String fileName , String note , HttpSession session){
        List<ContractFile> fileList = fileDAO.findByReqNum(salePlanID);
        if(fileList == null || fileList.size() == 0){
            return "请先上传初步合同";
        }
        IUser user = (IUser)session.getAttribute("user");
        String maker = user.getAccount();
        String makerName = user.getName();
        SalesPlan plan = planDAO.getOne(salePlanID);
        String customerCode = plan.getCustomerCode();
        String customerName = plan.getCustomerName();
        ContractFlow flow = new ContractFlow();
        String flowID = codeService.generateContractFlowID();
        flow.setContractId(flowID);
        flow.setReqNum(salePlanID);
        flow.setCustomerCode(customerCode);
        flow.setCustomerName(customerName);
        flow.setStep("second");
        flow.setStepName("最终合同");
        flow.setFileName(fileName);
        flow.setNote(note);
        flow.setMaker(maker);
        flow.setMakerName(makerName);
        flow.setMakeDate(LocalDate.now());

        TravelReq req = new TravelReq();
        String reqNum = codeService.generateReqId("C");
        req.setReqNum(reqNum);
        req.setLeafId(flowID);
        req.setAccount(maker);
        req.setName(makerName);
        req.setType("contractS");
        req.setTypeName("最终合同");
        req.setReason(makerName+"上传最终合同");
        req.setStatus("first");
        req.setStatusName("审批中");
        req.setMaker(maker);
        req.setMakerName((makerName));
        req.setMakeDate(LocalDate.now());

        try{
            flowDAO.save(flow);
            reqDAO.save(req);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 4、最终合同的审批
     * 通过，保存附件，进入运营导入
     * 不通过，退回重新申请
     *
     */

    @RequestMapping("/setContractSecondPass")
    @Transactional
    public String passContract(String reqNum , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int next = reqService.getNextNode(account);
        TravelReq req = reqDAO.getOne(reqNum);
        try{
            switch (next){
                case -1:
                    req.setStatus("pass");
                    req.setStatusName("已审批");
                    reqDAO.save(req);
                    String flowID = req.getLeafId();
                    ContractFlow flow = flowDAO.getOne(flowID);
                    String fileName = flow.getFileName();
                    String maker = flow.getMaker();
                    String makerName = flow.getMakerName();
                    String note = flow.getNote();
                    String salePlanID = flow.getReqNum();
                    ContractFile file = fileDAO.findByReqNum(salePlanID).get(0);
                    file.setSecondFile(fileName);
                    file.setSecondMaker(maker);
                    file.setSecondMakerName(makerName);
                    file.setSecondMakeDate(LocalDate.now());
                    file.setNote(note);
                    fileDAO.save(file);
                    SalesPlan plan = planDAO.getOne(salePlanID);
                    plan.setPlanStatus("eighth");
                    plan.setUpdateDate(LocalDate.now());
                    plan.setSpendTime(1);
                    plan.setDeadline(LocalDate.now().plusDays(1));
                    planDAO.save(plan);

                    List<ArrangeRecord> arrangeRecordList = arrangeRecordDAO.findBySalePlanIDAndStepAndType(salePlanID , "合同签订" , "sale");
                    if(!arrangeRecordList.isEmpty()){
                        ArrangeRecord arrangeRecord = arrangeRecordList.get(0);
                        arrangeRecord.setCompleteStatus("C");
                        arrangeRecordDAO.save(arrangeRecord);
                    }

                    taskDAO.deleteBySalePlanIDAndJobName(salePlanID , "合同签订");
                    taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , "合同签订");
                    taskService.saveTaskForAll(salePlanID , "合同归档"  , "foreAddFile" , LocalDate.now() , 1);
//                    SendRemind.getPhonemsg(); //发送短信提醒
                    break;
                case 0:
                    break;
                case 2:
                    req.setStatus("second");
                    reqDAO.save(req);
                    break;
                case 3:
                    req.setStatus("third");
                    reqDAO.save(req);
                    break;
                case 4:
                    req.setStatus("fourth");
                    reqDAO.save(req);
                    break;
            }
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/setContractSecondRefuse")
    public String refuseContractSecond(String reqNum , String note){
        try{
            TravelReq req = reqDAO.getOne(reqNum);
            req.setStatus("refuse");
            req.setStatusName("已退回");
            if(note == null || "".equals(note)){
                return "请填写退回原因";
            }else{
                req.setNote(note);
            }
            reqDAO.save(req);
            String contractId = req.getLeafId();
            ContractFlow contractFlow = flowDAO.getOne(contractId);
            contractFlow.setContractStatus("refuse");
            contractFlow.setReason(note);
            flowDAO.save(contractFlow);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 根据销售订单编号 查询出最终合同
     * 从系统中取出最终合同，用于合同归档
     */

    @RequestMapping("/getContract")
    public String selectCustomerContract(String salePlanID){
        List<ContractFile> contractList = fileDAO.findByReqNum(salePlanID);
        if(contractList != null && !"".equals(contractList)){
            return contractList.get(0).getSecondFile();
        }else{
            return null;
        }
    }

    @RequestMapping("/showContractList")
    public Map<String, Object> getContractByCustomer(String search){
        String customerName = "%"+search+"%";
        Map<String , Object> map = new HashMap<>();
        List<Record> recordList = recordDAO.findByCustomerNameLike(customerName);
        map.put("records", recordList);
        List<String> contractNameList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        for(int i = 0 ; i < recordList.size() ; i++){
            String salePlanID = recordList.get(i).getSalePlanID();
            List<ExtraContract> extraContractList =extraContractDAO.findBySalePlanID(salePlanID);
            String contractName = "";
            String fileName = "";
            for(int j = 0 ; j < extraContractList.size() ; j++){
                if(j == extraContractList.size() - 1){
                    contractName = contractName + extraContractList.get(j).getContractName();
                    fileName = fileName + extraContractList.get(j).getFileName();
                }else{
                    contractName = contractName + extraContractList.get(j).getContractName()+",";
                    fileName = fileName + extraContractList.get(j).getFileName()+",";
                }
            }
            contractNameList.add(contractName);
            fileNameList.add(fileName);
        }
        map.put("contractNames" , contractNameList);
        map.put("fileNames" , fileNameList);
        return map;
    }

    @RequestMapping("/getContractHistory")
    public List<ContractFile> selectCustomerContractHistory(String salePlanID){
        List<ContractFile> contractList = fileDAO.findByReqNum(salePlanID);
        return contractList;
    }
}
