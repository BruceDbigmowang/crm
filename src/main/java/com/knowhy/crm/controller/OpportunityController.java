package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/createOpportunity")
    public String saveOppo(HttpSession session , String name , String fax , String companyName , String email , String source , String principal , String body , String dept , String contract , String maker ,
                           String phone , String makeDate , String mobile , String leafType , String address , String leafNum){

        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Date now = new Date();

        Opportunity opportunity = new Opportunity();
        opportunity.setCreater(account);
        opportunity.setCreateDate(now);
        if(name == null || "".equals(name)){
            return "请输入名称";
        }else{
            opportunity.setName(name);
        }
        if(fax == null || "".equals(fax)){
            return "请输入传真";
        }else{
            opportunity.setFax(fax);
        }
        if(companyName == null || "".equals(companyName)){
            return "请输入公司名称";
        }else{
            opportunity.setCompanyName(companyName);
        }
        if(email == null || "".equals(email)){
            return "请输入邮箱";
        }else{
            opportunity.setEmail(email);
        }
        if(source == null || "".equals(source)){
            return "请输入线索来源";
        }else{
            opportunity.setResource(source);
        }
        if(principal == null || "".equals(principal)){
            return "请输入负责人";
        }else{
            opportunity.setPrincipal(principal);
        }
        if(body == null || "".equals(body)){
            return "请输入线索主体";
        }else{
            opportunity.setBody(body);
        }
        if(dept == null || "".equals(dept)){
            return "请输入负责部门";
        }else{
            opportunity.setDept(dept);
        }
        if(contract == null || "".equals(contract)){
            return "请输入联系人";
        }else{
            opportunity.setContract(contract);
        }
        if(maker == null || "".equals(maker)){
            return "请输入制单人";
        }else{
            opportunity.setMaker(maker);
        }
        if(phone == null || "".equals(phone)){
            return "请填写电话";
        }else{
            opportunity.setPhone(phone);
        }
        if(makeDate == null || "".equals(makeDate)){
            return "请选择制单日期";
        }else{
            try{
                LocalDate md = LocalDate.parse(makeDate , fmt);
                opportunity.setMakeDate(md);
            }catch (Exception e){
                return e.getMessage();
            }
        }
        if(mobile == null || "".equals(mobile)){
            return "请填写手机号";
        }else{
            opportunity.setMobile(mobile);
        }
        if(leafType == null || "".equals(leafType)){
            return "请填写源单类型";
        }else{
            opportunity.setLeafType(leafType);
        }
        if(address == null || "".equals(address)){
            return "请输入地址";
        }else{
            opportunity.setAddress(address);
        }
        if(leafNum == null || "".equals(leafNum)){
            return "请输入源单编号";
        }else{
            opportunity.setLeafNum(leafNum);
        }
        try{
            opportunityService.saveOpportunity(opportunity);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";

    }

    @RequestMapping("/getOpportunityByPage")
    public Map<String, Object> findOpportunity(int start){
        Map<String , Object> map = new HashMap<>();

        QueryWrapper<CrmOpportunity> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("makeDate");
        Page<CrmOpportunity> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmOpportunity> iPage = crmOpportunityMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }
        List<CrmOpportunity> crmOpportunityList = iPage.getRecords();
        map.put("oppos" , crmOpportunityList);
        map.put("pages" , size);

        return map;
    }

    @RequestMapping("/findOppoByAddress")
    public Map<String , Object> getByAddress(String address){
        Map<String , Object>map = new HashMap<>();
        List<Opportunity> opportunityList = opportunityService.getByAddress(address);
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
}
