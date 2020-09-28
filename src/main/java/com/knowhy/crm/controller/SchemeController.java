package com.knowhy.crm.controller;

import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.dao.SchemeDAO;
import com.knowhy.crm.dao.SchemeFlowDAO;
import com.knowhy.crm.dao.TravelReqDAO;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.CodeService;
import com.knowhy.crm.service.TravelReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@RestController
public class SchemeController {
    @Autowired
    SalesPlanDAO planDAO;
    @Autowired
    TravelReqDAO reqDAO;
    @Autowired
    SchemeDAO schemeDAO;
    @Autowired
    SchemeFlowDAO flowDAO;
    @Autowired
    CodeService codeService;
    @Autowired
    TravelReqService reqService;

    /**
     * 制定初步方案之后，上传初步方案
     * 并生成审批流程
     */
    @RequestMapping("/saveFirstScheme")
    @Transactional
    public String saveSchemeFirst(String reqNum , String fileName , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String maker = user.getAccount();
        String makerName = user.getName();

        SchemeFlow flow = new SchemeFlow();
        String schemenID = codeService.generateSchemeFlowID();
        flow.setSchemeId(schemenID);
        flow.setReqNum(reqNum);
        SalesPlan plan = planDAO.getOne(reqNum);
        String customerCode = plan.getCustomerCode();
        String customerName = plan.getCustomerName();
        flow.setCustomerCode(customerCode);
        flow.setCustomerName(customerName);
        flow.setFileName(fileName);
        flow.setStep("first");
        flow.setStepName("初步方案");
        flow.setMaker(maker);
        flow.setMakerName(makerName);
        flow.setMakeDate(LocalDate.now());

        TravelReq req = new TravelReq();
        String reqID = codeService.generateReqId("S");
        req.setReqNum(reqID);
        req.setLeafId(schemenID);
        req.setType("schemeF");
        req.setTypeName("初步方案");
        req.setReason(makerName+"上传初步方案");
        req.setAccount(maker);
        req.setName(makerName);
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
     * 初步方案审批
     * 通过 保存附件
     *若多次审批通过，以最后一次上传为准
     * 不通过 流程退回，销售重新上传
     */

    @RequestMapping("/setSchemeFirstPass")
    @Transactional
    public String passScheme(String reqNum , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        TravelReq req = reqDAO.getOne(reqNum);
        String schemeID = req.getLeafId();
        SchemeFlow flow = flowDAO.getOne(schemeID);
        String salePlanNum = flow.getReqNum();
        String customerCode = flow.getCustomerCode();
        String customerName = flow.getCustomerName();
        String fileName = flow.getFileName();
        String maker = flow.getMaker();
        String makerName = flow.getMakerName();

        int next = reqService.getNextNode(account);
        try{
            switch (next){
                case -1:
                    req.setStatus("pass");
                    req.setStatusName("已审批");
                    reqDAO.save(req);

                    List<Scheme> schemeList = schemeDAO.findByReqNum(salePlanNum);
                    if(schemeList == null || schemeList.size() == 0){
                        Scheme scheme = new Scheme();
                        scheme.setReqNum(salePlanNum);
                        scheme.setCustomerCode(customerCode);
                        scheme.setCustomerName(customerName);
                        scheme.setFirstFile(fileName);
                        scheme.setFirstMaker(maker);
                        scheme.setFirstMakerName(makerName);
                        scheme.setFirstMakeDate(LocalDate.now());
                        schemeDAO.save(scheme);
                    }else{
                        Scheme scheme = schemeList.get(0);
                        scheme.setFirstFile(fileName);
                        scheme.setFirstMaker(maker);
                        scheme.setFirstMakerName(makerName);
                        scheme.setFirstMakeDate(LocalDate.now());
                        schemeDAO.save(scheme);
                    }

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

    @RequestMapping("/setSchemeFirstRefuse")
    public String setFirstSchemeRefuse(String reqNum ){
        TravelReq req = reqDAO.getOne(reqNum);
        req.setStatus("refuse");
        req.setStatusName("已退回");
        reqDAO.save(req);
        return "OK";
    }

    /**
     * 最终方案上传，并根据人员、设备、降本标注出修改的地方
     * 生成方案流程审批
     */
    @RequestMapping("/saveSchemeSecond")
    @Transactional
    public String saveLastScheme(HttpSession session , String reqNum , String fileName , String personChange , String assetChange , String reduceChange){
        IUser user = (IUser)session.getAttribute("user");
        String maker = user.getAccount();
        String makerName = user.getName();

        List<Scheme> schemeList = schemeDAO.findByReqNum(reqNum);
        if(schemeList == null || schemeList.size() == 0){
            return "请先上传初步方案";
        }

        SchemeFlow flow = new SchemeFlow();
        String schemenID = codeService.generateSchemeFlowID();
        flow.setSchemeId(schemenID);
        flow.setReqNum(reqNum);
        SalesPlan plan = planDAO.getOne(reqNum);
        String customerCode = plan.getCustomerCode();
        String customerName = plan.getCustomerName();
        flow.setCustomerCode(customerCode);
        flow.setCustomerName(customerName);
        flow.setFileName(fileName);
        flow.setStep("second");
        flow.setStepName("最终方案");
        flow.setMaker(maker);
        flow.setMakerName(makerName);
        flow.setMakeDate(LocalDate.now());
        flow.setNote(personChange);
        flow.setNote2(assetChange);
        flow.setNote3(reduceChange);
        TravelReq req = new TravelReq();
        String reqID = codeService.generateReqId("S");
        req.setReqNum(reqID);
        req.setLeafId(schemenID);
        req.setAccount(maker);
        req.setName(makerName);
        req.setStatus("first");
        req.setStatusName("审批中");
        req.setType("schemeS");
        req.setTypeName("最终方案");
        req.setReason(makerName+"上传最终方案");
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
     * 最终方案审批
     * 通过 保存附件 并修改销售单的状态至下一阶段
     * 不通过 流程退回重新上传
     */

    @RequestMapping("/setSchemeSecondPass")
    @Transactional
    public String passSecondScheme(String reqNum , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        TravelReq req = reqDAO.getOne(reqNum);
        String schemeID = req.getLeafId();
        SchemeFlow flow = flowDAO.getOne(schemeID);
        String salePlanID = flow.getReqNum();
        Scheme scheme = schemeDAO.findByReqNum(salePlanID).get(0);
        String note = flow.getNote();
        String note2 = flow.getNote2();
        String note3 = flow.getNote3();
        String fileName = flow.getFileName();
        String maker = flow.getMaker();
        String makerName = flow.getMakerName();

        int next = reqService.getNextNode(account);
        try{
            switch (next){
                case -1:
                    req.setStatus("pass");
                    req.setStatusName("已审批");
                    reqDAO.save(req);
                    scheme.setSecondFile(fileName);
                    scheme.setSecondMaker(maker);
                    scheme.setSecondMakerName(makerName);
                    scheme.setSecondMakeDate(LocalDate.now());
                    if(!"".equals(note)){
                        scheme.setPersonChange(note);
                    }
                    if(!"".equals(note2)){
                        scheme.setAssetChange(note2);
                    }
                    if (!"".equals(note3)){
                        scheme.setReduceChange(note3);
                    }
                    schemeDAO.save(scheme);
                    SalesPlan plan = planDAO.getOne(salePlanID);
                    plan.setPlanStatus("sixth");
                    planDAO.save(plan);
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

    @RequestMapping("/setSchemeSecondRefuse")
    public String refuseSecondScheme(String reqNum){
        TravelReq req = reqDAO.getOne(reqNum);
        req.setStatus("refuse");
        req.setStatusName("已退回");
        reqDAO.save(req);
        return "OK";
    }

}
