package com.knowhy.crm.controller;

import com.knowhy.crm.dao.BussinessTravelDAO;
import com.knowhy.crm.dao.ReimburseDAO;
import com.knowhy.crm.dao.TravelReqDAO;
import com.knowhy.crm.dao.TravelSumDAO;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.BussinessTravelService;
import com.knowhy.crm.service.CodeService;
import com.knowhy.crm.service.TravelReqService;
import com.knowhy.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BussinessTravelController {

    @Autowired
    BussinessTravelService bussinessTravelService;
    @Autowired
    UserService userService;
    @Autowired
    CodeService codeService;
    @Autowired
    TravelReqService reqService;
    @Autowired
    TravelReqDAO reqDAO;
    @Autowired
    BussinessTravelDAO travelDAO;
    @Autowired
    TravelSumDAO sumDAO;
    @Autowired
    ReimburseDAO reimburseDAO;
    @Autowired
    TravelSumDAO travelSumDAO;



    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     *1、填写出差申请单，并生成出差流程
     */

    @RequestMapping("/createTravel")
    public String saveTravel(HttpSession session , String customerCode , String customerName, String applyAccount , String applyName , String travelDate , String travelTarget , String supportPerson , String spend , String costType ,
                             String costCenter , String other , String processCode , String processName ){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        String makerName = userService.getUserName(account);

        boolean canWrite = bussinessTravelService.canWriteTravel(customerCode , processCode , account);
        if(!canWrite){
            return "请先填写上一次出差的工作总结";
        }

        BussinessTravel travel = new BussinessTravel();
        String travelId = codeService.generateTravelId();
        travel.setId(travelId);
        if(customerCode == null || "0".equals(customerCode)){
            return "请选择客户";
        }else{
            travel.setCustomerCode(customerCode);
            travel.setCustomerName(customerName);
        }
        if(applyAccount == null || "0".equals(applyAccount)){
            return "请选择申请人账号";
        }else{
            travel.setApplyAccount(applyAccount);
            travel.setApplyName(applyName);
        }
        if(travelDate == null || "".equals(travelDate)){
            return "请选择出差日期";
        }else{
            try{
                LocalDate tDate = LocalDate.parse(travelDate , fmt);
                travel.setTravelDate(tDate);
            }catch (Exception e){
                return e.getMessage();
            }
        }
        if(travelTarget == null || "".equals(travelTarget)){
            return "请填写出差目的";
        }else {
            travel.setTravelTarget(travelTarget);
        }
        if(supportPerson != null && !"".equals(supportPerson)){
            travel.setSupportPerson(supportPerson);
        }
        if(spend == null || "".equals(spend)){
            return "请填写费用预估";
        }else{
            try{
                BigDecimal amount = new BigDecimal(spend).setScale(4 , BigDecimal.ROUND_HALF_UP);
                travel.setCostAmount(amount);
            }catch (Exception e){
                return e.getMessage();
            }
        }
        if(costType == null || "0".equals(costType)){
            return "请选择费用类型";
        }else{
            travel.setCostType(costType);
        }
        if(costCenter == null || "".equals(costCenter)){
            return "请填写成本中心";
        }else{
            travel.setCostCenter(costCenter);
        }
        if(other != null && "".equals(other)){
            travel.setOther(other);
        }
        travel.setMakeDate(LocalDate.now());
        travel.setMakerAccount(account);
        travel.setMakeName(makerName);
        travel.setProcessCode(processCode);
        travel.setProcessName(processName);

        TravelReq req = new TravelReq();
        String rId = codeService.generateReqId("T");
        req.setReqNum(rId);
        req.setLeafId(travelId);
        req.setAccount(account);
        req.setMaker(account);
        req.setMakerName(makerName);
        req.setMakeDate(LocalDate.now());
        req.setType("travel");
        req.setTypeName("出差申请");
        req.setReason(applyName+"创建出差申请流程——"+LocalDate.now());
        req.setAccount(applyAccount);
        req.setName(applyName);
        req.setStatus("first");
        req.setStatusName("审批中");
        try {
            bussinessTravelService.saveTravel(travel , req);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 2、审批时
     * 审批过程中，修改流程的状态
     * 若审批通过，修改流程的状态为pass 出差申请的状态为open
     * 若审批不通过，修改流程的状态为refuse
     */
    @RequestMapping("/setTravelPass")
    public String passTravel(HttpSession session , String reqNum){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int next = reqService.getNextNode(account);
        TravelReq req = reqDAO.getOne(reqNum);
        String travelID = req.getLeafId();
        BussinessTravel travel = travelDAO.getOne(travelID);
        try{
            switch (next){
                case -1:
                    req.setStatus("pass");
                    req.setStatusName("已审批");
                    reqDAO.save(req);
                    travel.setTravelStatus("O");
                    travelDAO.save(travel);
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

    @RequestMapping("/setTravelRefuse")
    public String refuseTravel(HttpSession session , String reqNum , String note){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int next = reqService.getNextNode(account);
        TravelReq req = reqDAO.getOne(reqNum);
        String travelID = req.getLeafId();
        BussinessTravel travel = travelDAO.getOne(travelID);
        if(note == null || "".equals(note)){
            return "请填写退回原因";
        }else{
            req.setNote(note);
            travel.setNote(note);
        }
        try{
            req.setStatus("refuse");
            travel.setTravelStatus("refuse");
            req.setStatusName("已退回");
            reqDAO.save(req);
            travelDAO.save(travel);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 3、填写出差总结
     * 根据 阶段 ， 客户 ， 创建人 ， 出差申请的状态查询出出差单（限定在某一阶段，同一个客户提了出差流程之后，必须填写出差总结之后才能提交下一次出差申请）
     * 填写完出差总结（根据出差申请编号关联），并修改出差申请的状态为close
     * 填写报销申请，生成报销流程
     */
    @RequestMapping("/saveTravelSum")
    @Transactional
    public String createSum(HttpSession session , String customer, String travelSum , String needSupport , String trouble , String oppose , String nextPlan , String processCode ,
                            String travelSpend , String eatting , String server , String other , String fileName){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        System.out.println("<><><>"+customer);
        boolean canWrite = bussinessTravelService.canWriteTravel(customer , processCode , account);
        if(canWrite){
            return "请先填写出差申请";
        }
        List<BussinessTravel> travelList = travelDAO.findByCustomerCodeAndProcessCodeAndMakerAccountAndTravelStatus(customer , processCode , account , "O");
        if(travelList == null || travelList.size() == 0){
            return "请先填写出差申请或等出差申请审批结束再填写出差总结";
        }
        String travelID = travelList.get(0).getId();
        TravelSum sum = new TravelSum();
        sum.setTravelID(travelID);
        if(travelSum == null || "".equals(travelSum)){
            return "请填写出差总结";
        }else{
            sum.setTravelSum(travelSum);
        }
        if(needSupport != null && !"".equals(needSupport)){
            sum.setNeedSupport(needSupport);
        }
        if(trouble != null && !"".equals(trouble)){
            sum.setTrouble(trouble);
        }
        if(oppose != null && !"".equals(oppose)){
            sum.setOppose(oppose);
        }
        if(nextPlan != null && !"".equals(nextPlan)){
            sum.setNextPlan(nextPlan);
        }else{
            return "请填写下次计划";
        }

        BussinessTravel travel = travelDAO.getOne(travelID);
        travel.setTravelStatus("C");

        Reimburse reimburse = new Reimburse();
        String rId = codeService.generateReimburseID();
        reimburse.setId(rId);
        reimburse.setTravelID(travelID);
        if (travelSpend != null && !"".equals(travelSpend)){
            try{
                BigDecimal travelCost = new BigDecimal(travelSpend).setScale(4 , BigDecimal.ROUND_HALF_UP);
                reimburse.setTravelCost(travelCost);
            }catch (Exception e){
                return "差旅费填写错误(只能填写数字)";
            }
        }
        if(eatting != null && !"".equals(eatting)){
            try{
                BigDecimal eattingCost = new BigDecimal(eatting).setScale(4 , BigDecimal.ROUND_HALF_UP);
                reimburse.setEattingCost(eattingCost);
            }catch (Exception e){
                return "餐补填写错误(只能填写数字)";
            }
        }
        if(server != null && !"".equals(server)){
            try{
                BigDecimal serverCost = new BigDecimal(server).setScale(4 , BigDecimal.ROUND_HALF_UP);
                reimburse.setServerCost(serverCost);
            }catch (Exception e){
                return "招待费填写错误(只能填写错误)";
            }
        }
        if(other!= null && !"".equals(other)){
            try{
                BigDecimal otherCost = new BigDecimal(other).setScale(4 , BigDecimal.ROUND_HALF_UP);
                reimburse.setOtherCost(otherCost);
            }catch (Exception e){
                return "其他费用填写错误(只能填写数字)";
            }
        }
        if(fileName != null && !"".equals(fileName)){
            reimburse.setFileName(fileName);
        }
        reimburse.setMaker(account);
        reimburse.setMakerName(user.getName());
        reimburse.setMakeDate(LocalDate.now());


        TravelReq req = new TravelReq();
        String reqID = codeService.generateReqId("R");
        req.setReqNum(reqID);
        req.setLeafId(rId);
        req.setAccount(account);
        req.setMaker(account);
        req.setMakerName(user.getName());
        req.setMakeDate(LocalDate.now());
        req.setType("reimburse");
        req.setTypeName("报销申请");
        req.setReason(user.getName()+"创建出差申请流程——"+LocalDate.now());
        req.setAccount(account);
        req.setName(user.getName());
        req.setStatus("first");
        req.setStatusName("审批中");
        try{
            sumDAO.save(sum); //填写出差总结
            travelDAO.save(travel); //修改出差申请中的状态
            reimburseDAO.save(reimburse);
            reqDAO.save(req);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 4、报销流程审批
     * 若审批通过，修改流程状态为pass
     * 若审批不通过，修改流程状态为refuse,并修改出差申请的状态为open，删除出差总结
     * 销售人员重新填写出差总结及报销
     */

    @RequestMapping("/setReimbursePass")
    public String passReimburse(String reqNum , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        TravelReq req = reqDAO.getOne(reqNum);
        int next = reqService.getNextNode(account);
        try{
            switch (next){
                case -1:
                    req.setStatus("pass");
                    req.setStatusName("已审批");
                    reqDAO.save(req);
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

    @RequestMapping("/setReimburseRefuse")
    @Transactional
    public String refuseReimburse(String reqNum , String note , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
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

            String rId = req.getLeafId();
            Reimburse reimburse = reimburseDAO.getOne(rId);
            reimburse.setReimburseStatus("refuse");
            reimburse.setNote(note);
            reimburseDAO.save(reimburse);
            BussinessTravel travel = travelDAO.getOne(reimburse.getTravelID());
            travel.setTravelStatus("O");
            travelDAO.save(travel);
            sumDAO.deleteByTravelID(travel.getId());
        }catch (Exception e){
            return e.getMessage();
        }

        return "OK";
    }

    @RequestMapping("/getTotalTravel")
    public Map<String , Object> getTotalInfo(String travelID , String orderID){
        BussinessTravel travel = travelDAO.getOne(travelID);
        List<TravelSum> travelSumList = travelSumDAO.findByTravelID(travelID);
        TravelReq req = reqDAO.getOne(orderID);
        Map<String , Object> map = new HashMap<>();
        map.put("travel",travel);
        if(travelSumList != null && travelSumList.size() != 0){
            map.put("sum" , travelSumList.get(0));
        }
        return map;
    }
}
