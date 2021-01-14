package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.*;
import com.knowhy.crm.entity.CrmCustomer;
import com.knowhy.crm.entity.CrmTravelreq;
import com.knowhy.crm.mapper.CrmTravelreqMapper;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.TravelReqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RequestController {
    @Autowired
    TravelReqService reqService;
    @Autowired
    TravelReqDAO travelReqDAO;
    @Autowired
    BussinessTravelDAO travelDAO;
    @Autowired
    ReimburseDAO reimburseDAO;
    @Autowired
    SchemeFlowDAO flowDAO;
    @Autowired
    ContractFlowDAO contractFlowDAO;
    @Resource
    CrmTravelreqMapper crmTravelreqMapper;

    @RequestMapping("/getMyAll")
    public Map<String , Object> getAllFlow(HttpSession session , int start){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Map map = new HashMap();
//        List<TravelReq> reqList = reqService.findMyAll(account);
        QueryWrapper<CrmTravelreq> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("makeDate");
        queryWrapper.eq("maker" , account);
        Page<CrmTravelreq> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmTravelreq> iPage = crmTravelreqMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }

        List<CrmTravelreq> crmTravelreqList = iPage.getRecords();
        if(crmTravelreqList.isEmpty()){
            map.put("info" , "no");
        }else{
            map.put("info" , "ok");
            map.put("reqs" , crmTravelreqList);
            map.put("pages" , size);
        }
        return map;
    }

    @RequestMapping("/getMyAllApprove")
    public Map<String , Object> getAllApprove(HttpSession session){
        Map map = new HashMap();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<TravelReq> reqList = reqService.findAllApprove(account);
        if(reqList.isEmpty()){
            map.put("info" , "no");
        }else{
            map.put("info" , "ok");
            map.put("reqs" , reqList);
        }
        return map;
    }

    @RequestMapping("/approveTravel")
    public String approveReq(HttpSession session , String reqNum , String remark , String status){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        if(status.equals("Y")){
            reqService.approveReqPass(account , reqNum);
            return "已审批通过";
        }else{
            reqService.approveReqRefuse(account , reqNum , remark);
            return "流程已退回";
        }
    }

    @RequestMapping("/getReqDetail")
    public Object findDetail(String reqNum){
        TravelReq req = travelReqDAO.getOne(reqNum);
        String type = req.getType();
        String leaf = req.getLeafId();
        if(type.equals("travel")){
            BussinessTravel travel = travelDAO.getOne(leaf);
            return travel;
        }else if(type.equals("reimburse")){
            Reimburse reimburse = reimburseDAO.getOne(leaf);
            return reimburse;
        }else if(type.equals("schemeF")){
            SchemeFlow flow = flowDAO.getOne(leaf);
            return flow;
        }else if(type.equals("schemeS")){
            SchemeFlow flow = flowDAO.getOne(leaf);
            return flow;
        }else if(type.equals("contractF")){
            ContractFlow contractFlow = contractFlowDAO.getOne(leaf);
            return contractFlow;
        }else{
            ContractFlow contractFlow = contractFlowDAO.getOne(leaf);
            return contractFlow;
        }
    }

}
