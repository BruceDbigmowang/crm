package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.OpportunityDAO;
import com.knowhy.crm.entity.CrmOpportunity;
import com.knowhy.crm.entity.CrmSalecost;
import com.knowhy.crm.mapper.CrmOpportunityMapper;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.Opportunity;
import com.knowhy.crm.service.OpportunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OpportunityController {
    @Autowired
    OpportunityService opportunityService;
    @Resource
    CrmOpportunityMapper crmOpportunityMapper;
    @Autowired
    OpportunityDAO opportunityDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/createOpportunity")
    public String saveOppo(HttpSession session , String fax , String companyName , String email , String source , String principal , String body , String dept , String contract ,
                           String phone , String mobile , String province , String city , String area , String address){

        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Date now = new Date();

        Opportunity opportunity = new Opportunity();
        opportunity.setCreater(account);
        opportunity.setCreateDate(now);
        if(body == null || "".equals(body)){
            return "请输入线索主题";
        }else{
            opportunity.setBody(body);
        }
        if(fax == null || "".equals(fax)){
            return "请输入传真";
        }else{
            opportunity.setFax(fax);
        }
        if(source == null || "0".equals(source)){
            return "请输入线索来源";
        }else{
            opportunity.setResource(source);
        }
        if(email == null || "".equals(email)){
            return "请输入邮箱";
        }else{
            opportunity.setEmail(email);
        }
        if(companyName == null || "".equals(companyName)){
            return "请输入客户名称";
        }else{
            opportunity.setCompanyName(companyName);
        }
        if(principal == null || "".equals(principal)){
            return "请输入负责人";
        }else{
            opportunity.setPrincipal(principal);
        }
        if(contract == null || "".equals(contract)){
            return "请输入联系人";
        }else{
            opportunity.setContract(contract);
        }
        if(dept == null || "".equals(dept)){
            return "请输入负责部门";
        }else{
            opportunity.setDept(dept);
        }
        opportunity.setMaker(user.getAccount());
        if(mobile == null || "".equals(mobile)){
            return "请填写移动电话";
        }else{
            opportunity.setMobile(mobile);
        }
        if(phone == null || "".equals(phone)){
            return "请填写办公电话";
        }else{
            opportunity.setPhone(phone);
        }
        opportunity.setMakeDate(LocalDate.now());
//        if(leafType == null || "".equals(leafType)){
//            return "请填写源单类型";
//        }else{
//            opportunity.setLeafType(leafType);
//        }
//
//        if(leafNum == null || "".equals(leafNum)){
//            return "请输入源单编号";
//        }else{
//            opportunity.setLeafNum(leafNum);
//        }
        if(province != null){
            opportunity.setProvince(province);
        }else{
            return "程序出错";
        }
        if(city == null){
            return "程序出错";
        }else{
            opportunity.setCity(city);
        }
        if(area == null){
            return "程序出错";
        }else{
            opportunity.setArea(area);
        }
        if(address == null || "".equals(address)){
            return "请输入地址";
        }else{
            opportunity.setAddress(address);
        }
        try{
            opportunityService.saveOpportunity(opportunity);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";

    }

    @RequestMapping("/getOpportunityByPage")
    public Map<String, Object> findOpportunity(){
        Map<String , Object> map = new HashMap<>();

        List<Opportunity> opportunityList = opportunityDAO.findAll();

        map.put("oppos" , opportunityList);

        return map;
    }

    @RequestMapping("/findOppoByAddress")
    public Map<String , Object> getByAddress(String province , String city , String area){
        Map<String , Object>map = new HashMap<>();
        List<Opportunity> opportunityList = opportunityService.getByAddress(province , city , area);
        map.put("oppos" , opportunityList);
        return map;
    }

    @RequestMapping("/findOppoByResource")
    public Map<String , Object> getByResource(String resource){
        Map<String , Object>map = new HashMap<>();
        List<Opportunity> opportunityList = opportunityService.getByResource(resource);
        map.put("oppos" , opportunityList);
        return map;
    }

    @RequestMapping("/getOpportunityDetail")
    public Opportunity findOppDetail(int opId){
        return opportunityDAO.getOne(opId);
    }

    @RequestMapping("/updateOpportunity")
    public String changeOppo(Integer opId , String contract , String mobile , String phone , String email , String fax , String principal , String dept , String leafType , String leafNum){
        Opportunity opportunity = opportunityDAO.getOne(opId);
        if(contract != null && !"".equals(contract)){
            opportunity.setContract(contract);
        }
        if(mobile != null && !"".equals(mobile)){
            opportunity.setMobile(mobile);
        }
        if(phone != null && !"".equals(phone)){
            opportunity.setPhone(phone);
        }
        if(email != null && !"".equals(email)){
            opportunity.setEmail(email);
        }
        if(fax != null && !"".equals(fax)){
            opportunity.setFax(fax);
        }
        if(principal != null && !"".equals(principal)){
            opportunity.setPrincipal(principal);
        }
        if(dept != null && !"".equals(dept)){
            opportunity.setDept(dept);
        }
        if(leafType != null && !"".equals(leafType)){
            opportunity.setLeafType(leafType);
        }
        if(leafNum != null && !"".equals(leafNum)){
            opportunity.setLeafNum(leafNum);
        }
        opportunityDAO.save(opportunity);
        return "OK";
    }

    @RequestMapping("/getAllOpportunity")
    public List<Opportunity> findAll(){
        return opportunityDAO.findAll();
    }
}
