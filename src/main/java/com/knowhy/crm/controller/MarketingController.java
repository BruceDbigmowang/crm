package com.knowhy.crm.controller;

import com.knowhy.crm.dao.ActivityDataDAO;
import com.knowhy.crm.dao.MarketSpendDAO;
import com.knowhy.crm.dao.MarketingDAO;
import com.knowhy.crm.pojo.ActivityData;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.MarketSpend;
import com.knowhy.crm.pojo.Marketing;
import com.knowhy.crm.service.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class MarketingController {

    @Autowired
    MarketingDAO marketingDAO;
    @Autowired
    MarketSpendDAO marketSpendDAO;
    @Autowired
    ActivityDataDAO activityDataDAO;
    @Autowired
    AddService addService;

    @RequestMapping("/saveMarketingOnline")
    public String saveOnlineMarket(int opId , String theme , String activity , String describe , String spend , String isPlan , String contractAmount , String contractFile , String note , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        Marketing marketing = new Marketing();
        if(theme == null || "".equals(theme)){
            return "程序出错，未获得商机/线索信息";
        }else{
            marketing.setOpId(opId);
            marketing.setTheme(theme);
        }
        if(activity == null){
            return "程序出错，未获得活动分类信息";
        }else{
            if(activity.equals("其它")){
                marketing.setActivity(note);
            }else{
                marketing.setActivity(activity);
            }
        }
        if(describe == null || "".equals(describe)){
            return "请填写活动说明";
        }else{
            marketing.setDescribe(describe);
        }
        if(spend == null || "".equals(spend)){
            return "请填写费用预算";
        }else{
            try{
                BigDecimal cost = new BigDecimal(spend);
                marketing.setSpend(cost);
            }catch (Exception e){
                return "费用预算填写错误（只能填写数字）";
            }
        }
        if(isPlan == null){
            return "程序出错，未获取到是否在计划内信息";
        }else{
            marketing.setIsPlan(isPlan);
        }
        if(contractAmount == null || "".equals(contractAmount)){
            return "请填写合同金额";
        }else{
            try{
                BigDecimal amount = new BigDecimal(contractAmount);
                marketing.setContractAmount(amount);
            }catch (Exception e){
                return "合同金额填写错误（只能填写数字）";
            }
        }
        if(contractFile == null || "".equals(contractFile)){
            return "请选择合同附件";
        }else{
            marketing.setContractFile(contractFile);
        }
        marketing.setType("线上推广");
        marketing.setCreaterAccount(user.getAccount());
        marketing.setCreaterName(user.getName());
        marketing.setCreatDate(LocalDate.now());
        marketing.setStatus("O");
        marketing.setUseStatus("O");
        marketing.setUploadStatus("O");
        try{
            marketingDAO.save(marketing);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/saveMarketingOffline")
    public String saveOfflineMarket(int opId , String theme , String activity , String describe , String spend , String isPlan , String contractAmount , String contractFile , String note , String channel, HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        Marketing marketing = new Marketing();
        if(theme == null || "".equals(theme)){
            return "程序出错，未获得商机/线索信息";
        }else{
            marketing.setOpId(opId);
            marketing.setTheme(theme);
        }
        if(activity == null){
            return "程序出错，未获得活动分类信息";
        }else{
            if(activity.equals("其它")){
                marketing.setActivity(note);
            }else{
                marketing.setActivity(activity);
            }
        }
        if(describe == null || "".equals(describe)){
            return "请填写活动说明";
        }else{
            marketing.setDescribe(describe);
        }
        if(spend == null || "".equals(spend)){
            return "请填写费用预算";
        }else{
            try{
                BigDecimal cost = new BigDecimal(spend);
                marketing.setSpend(cost);
            }catch (Exception e){
                return "费用预算填写错误（只能填写数字）";
            }
        }
        if(isPlan == null){
            return "程序出错，未获取到是否在计划内信息";
        }else{
            marketing.setIsPlan(isPlan);
        }
        if(contractAmount == null || "".equals(contractAmount)){
            return "请填写合同金额";
        }else{
            try{
                BigDecimal amount = new BigDecimal(contractAmount);
                marketing.setContractAmount(amount);
            }catch (Exception e){
                return "合同金额填写错误（只能填写数字）";
            }
        }
        if(contractFile == null || "".equals(contractFile)){
            return "请选择合同附件";
        }else{
            marketing.setContractFile(contractFile);
        }
        if(channel != null && !"".equals(channel)){
            marketing.setChannel(channel);
        }
        marketing.setType("线下推广");
        marketing.setCreaterAccount(user.getAccount());
        marketing.setCreaterName(user.getName());
        marketing.setCreatDate(LocalDate.now());
        marketing.setStatus("O");
        marketing.setUseStatus("O");
        marketing.setUploadStatus("O");
        try{
            marketingDAO.save(marketing);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/saveActivityData")
    @Transactional
    public String toSaveActivity(HttpSession session , int mId , String activityFile , String customerFile , String clueNum , String customerOne , String customerTwo , String customerMoney){
        IUser user = (IUser)session.getAttribute("user");
        Marketing marketing = marketingDAO.getOne(mId);
        ActivityData activity = new ActivityData();
        activity.setmId(mId);
        if(activityFile == null || "".equals(activityFile)){
            return "请选择活动资料附件";
        }else{
            activity.setActivityFile(activityFile);
        }
        if(customerFile == null || "".equals(customerFile)){
            activity.setCustomerFile(marketing.getCustomerFile());
        }else{
            activity.setCustomerFile(customerFile);
        }
        int one = 0;
        if(customerOne == null || "".equals(customerOne)){
            return "请填写意向客户数";
        }else{
            try{
                one = Integer.parseInt(customerOne);
                activity.setCustomerOne(one);
            }catch (Exception e){
                return "意向客户数填写错误（只能填写整数）";
            }
        }
        int two = 0;
        if(customerTwo == null || "".equals(customerTwo)){
            return "请填写成交合同数";
        }else{
            try{
                two = Integer.parseInt(customerTwo);
                activity.setCustomerTwo(two);
            }catch (Exception e){
                return "成交合同数填写错误（只能填写整数）";
            }
        }
        if(customerMoney == null || "".equals(customerMoney)){
            return "请填写客户成交金额";
        }else{
            try{
                BigDecimal money = new BigDecimal(customerMoney);
                activity.setCustomerMoney(money);
            }catch (Exception e){
                return "客户成交金额填写错误（只能填写数字）";
            }
        }
        if(one + two != marketing.getTotalCustomer()){
            return "意向客户数和成交客户数与批量导入的不一致";
        }
        activity.setCreaterAccount(user.getAccount());
        activity.setCreaterName(user.getName());
        activity.setCreateDate(LocalDate.now());
        activityDataDAO.save(activity);

        marketing.setStatus("C");
        marketingDAO.save(marketing);
        return "OK";
    }



    @RequestMapping("/selectAllMarketingOnOrOff")
    public List<Marketing> findAllMarket(){
        String status = "O";
        List<Marketing> marketingList = marketingDAO.findByStatus(status);
        for(int i = 0 ; i < marketingList.size() ; i++){
            String isPlan = marketingList.get(i).getIsPlan();
            if(isPlan.equals("Y")){
                marketingList.get(i).setIsPlan("是");
            }else{
                marketingList.get(i).setIsPlan("否");
            }
        }
        return marketingList;
    }

    @RequestMapping("/selectAllMarketing")
    public List<Marketing> findAll(){
        String status = "O";
        List<Marketing> marketingList = marketingDAO.findByUseStatus(status);
        for(int i = 0 ; i < marketingList.size() ; i++){
            String isPlan = marketingList.get(i).getIsPlan();
            if(isPlan.equals("Y")){
                marketingList.get(i).setIsPlan("是");
            }else{
                marketingList.get(i).setIsPlan("否");
            }
        }
        return marketingList;
    }

    @RequestMapping("/saveMarketSpend")
    public String saveSpends(String mId , String budget , String category , String project , String amount , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        MarketSpend spend = new MarketSpend();
        int marketId;
        if(mId == null || "".equals(mId)){
            return "程序出错，未获取到活动信息";
        }else{
            marketId = Integer.parseInt(mId);
            spend.setmId(marketId);
        }
        if(budget == null || "".equals(budget)){
            return "请填写预算科目";
        }else{
            spend.setBudget(budget);
        }
        if(category == null || "".equals(category)){
            return "请填写分类";
        }else{
            spend.setCategory(category);
        }
        if(project == null || "".equals(project)){
            return "请填写项目";
        }else{
            spend.setProject(project);
        }
        if(amount == null || "".equals(amount)){
            return "请填写金额";
        }else{
            try{
                BigDecimal money = new BigDecimal(amount);
                spend.setAmount(money);
            }catch (Exception e){
                return "金额填写出错（只能填写数字）";
            }
        }
        spend.setCreaterAccount(user.getAccount());
        spend.setCreaterName(user.getName());
        spend.setCreateDate(LocalDate.now());
        marketSpendDAO.save(spend);
        Marketing marketing = marketingDAO.getOne(marketId);
        marketing.setUseStatus("C");
        marketingDAO.save(marketing);
        return "OK";
    }

    @RequestMapping("/uploadMarktingOnline")
    public String  UploadExcel(HttpServletRequest request, HttpServletResponse response , HttpSession session) throws Exception {
        String check = addService.checkCustomerEnter(request, response);
        if(check.equals("OK")) {
            String s = addService.uploadCustomer(request, response , session);
            return s;
        }else {
            return check;
        }
    }

    @RequestMapping("/saveMarketingActivity")
    public String saveMarketData(int oppId , String theme , String type , String activity , String detail , String describe , String spend , String isPlan , String contractAmount , String contractFile , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        Marketing marketing = new Marketing();
        if(theme == null || "".equals(theme)){
            return "程序出错，未获得商机/线索信息";
        }else{
            marketing.setOpId(oppId);
            marketing.setTheme(theme);
        }
        if(type == null || "0".equals(type)){
            return "请选择活动推广类型";
        }else{
            marketing.setType(type);
        }
        if(activity == null){
            return "程序出错，未获得活动分类信息";
        }else{
            if(activity.equals("其它")){
                marketing.setActivity(detail);
            }else{
                marketing.setActivity(activity);
            }
        }
        if(describe == null || "".equals(describe)){
            return "请填写活动说明";
        }else{
            marketing.setDescribe(describe);
        }
        if(spend == null || "".equals(spend)){
            return "请填写费用预算";
        }else{
            try{
                BigDecimal cost = new BigDecimal(spend);
                marketing.setSpend(cost);
            }catch (Exception e){
                return "费用预算填写错误（只能填写数字）";
            }
        }
        if(isPlan == null){
            return "程序出错，未获取到是否在计划内信息";
        }else{
            marketing.setIsPlan(isPlan);
        }
        if(contractAmount == null || "".equals(contractAmount)){
            return "请填写合同金额";
        }else{
            try{
                BigDecimal amount = new BigDecimal(contractAmount);
                marketing.setContractAmount(amount);
            }catch (Exception e){
                return "合同金额填写错误（只能填写数字）";
            }
        }
        if(contractFile == null || "".equals(contractFile)){
            return "请选择合同附件";
        }else{
            marketing.setContractFile(contractFile);
        }
        marketing.setCreaterAccount(user.getAccount());
        marketing.setCreaterName(user.getName());
        marketing.setCreatDate(LocalDate.now());
        marketing.setStatus("O");
        marketing.setUseStatus("O");
        marketing.setUploadStatus("O");
        try{
            marketingDAO.save(marketing);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

}
