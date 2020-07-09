package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.entity.CrmAccount;
import com.knowhy.crm.entity.CrmSalesplan;
import com.knowhy.crm.mapper.CrmSalesplanMapper;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.service.SalePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SalePlanController {

    @Autowired
    SalePlanService salePlanService;
    @Resource
    CrmSalesplanMapper crmSalesplanMapper;

    @RequestMapping("/createSalePlanFirst")
    public String createPlan(HttpSession session , @RequestParam("number")String number , @RequestParam("name")String name , @RequestParam("describe")String describe){
        SalesPlan salesPlan = new SalesPlan();
        if("".equals(number)){
            return "请填写销售计划编号";
        }else{
            salesPlan.setNo(number);
        }

        if("".equals(name)){
            return "请填写客户名称";
        }else{
            salesPlan.setCompany(name);
        }

        if("".equals(describe)){
            return "请填写销售描述";
        }else{
            salesPlan.setDescribe(describe);
        }

        salesPlan.setCreateTime(new Date());
        IUser user = (IUser)session.getAttribute("user");
        salesPlan.setCreater(user.getAccount());

        try{

            salePlanService.createSalePlan(salesPlan);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";

    }



    @RequestMapping("/createNewSalePlan")
    public String createNew(HttpSession session , @RequestParam("salePlanNumber")String salePlanNum , @RequestParam("costEstimate") String costEstimate , @RequestParam("company")String company , @RequestParam("costType")String costType , @RequestParam("costCenter")String costCenter , @RequestParam("amount")String amount , @RequestParam("applied")String applied , @RequestParam("used")String used){
        SalesPlan salesPlan = new SalesPlan();
        if("".equals(salePlanNum)){
            return "请输入销售计划编号";
        }
        salesPlan = salePlanService.findById(salePlanNum);
//        if(!"".equals(desc)){
//            salesPlan.setDescribe(desc);
//        }
        if("".equals(costEstimate)){
            return "请填写销售费用估算";
        }else{
            try{
                BigDecimal cost = new BigDecimal(costEstimate);
                salesPlan.setCostEstimate(cost);
            }catch (Exception e){
                return "销售费用估算填写有误,必须是数字";
            }
        }
       if("".equals(company)){
           return "请填写客户名称";
       } else{
           salesPlan.setCompany(company);
       }
       if("".equals(costType)){
           return "请填写费用类型";
       }else{
           salesPlan.setCostType(costType);
       }
       if("".equals(costCenter)){
           return "请输入成本中心";
       }else{
           salesPlan.setCostCenter(costCenter);
       }
       if("".equals(amount)){
           return "请输入金额";
       }else{
           try{
               BigDecimal total = new BigDecimal(amount);
               salesPlan.setAmount(total);
           }catch (Exception e){
               return "金额输入有误，必须是数字";
           }
       }
       if("".equals(applied)){
           return "请输入申请金额";
       }else {
           try {
               BigDecimal appliedAmount = new BigDecimal(applied);
               salesPlan.setAppliedAmount(appliedAmount);
           } catch (Exception e) {
               return "申请金额填写有误，必须是数字";
           }
       }
       if("".equals(used)){
           return "请填写使用金额";
       }else{
            try{
                BigDecimal usedAmount = new BigDecimal(used);
                salesPlan.setUsedAmount(usedAmount);
            }catch (Exception e){
                return "使用金额填写有误，必须是数字";
            }
       }
       try{

           salePlanService.createSalePlan(salesPlan);
       }catch (Exception e){
           return e.getMessage();
       }
       return "OK";
    }

    @RequestMapping("/getAllSalePlan")
    public Map<String,Object> getAll(@RequestParam("start")int start){
        List<SalesPlan> salesPlanList = salePlanService.findAllSalePlan();
        Map<String , Object> map = new HashMap<>();
        if(salesPlanList == null || salesPlanList.size() == 0){
            map.put("result" , "暂无数据");
        }else{
            QueryWrapper<CrmSalesplan> queryWrapper =  new QueryWrapper<>();
            queryWrapper.orderByDesc("salesPlanNumber");
            Page<CrmSalesplan> page = new Page<>(start,10);  // 查询第1页，每页返回5条
            IPage<CrmSalesplan> iPage = crmSalesplanMapper.selectPage(page,queryWrapper);
            int total = (int)iPage.getTotal();
            int size;
            if(total%10 == 0){
                size = total/10;
            }else{
                size = total/10 + 1;
            }
            List<CrmSalesplan> salesplanList = iPage.getRecords();
            for(int i = 0 ; i < salesplanList.size() ; i++){
                String totalStatus = salesplanList.get(i).getTotalStatus();
                if(totalStatus == null){
                    salesplanList.get(i).setTotalStatus("尚未进行");
                }else if(totalStatus == "END"){
                    salesplanList.get(i).setTotalStatus("已完成");
                }else{
                    salesplanList.get(i).setTotalStatus("进行中");
                }
            }
            map.put("result" , "成功获取数据");
            map.put("salePlans" , salesplanList);
            map.put("size" , size);
        }
        return map;
    }

    @RequestMapping("/findSalePlanByNumber")
    public Map<String , Object> findByNumber(@RequestParam("number")String number){
        Map<String , Object> map = new HashMap<>();
        SalesPlan salesPlan = salePlanService.findById(number);
        if(salesPlan == null){
            map.put("result" , "暂无数据");
            return map;
        }else{

            String totalStatus = salesPlan.getStatus();
            if(totalStatus == null){
                salesPlan.setStatus("尚未进行");
            }else if(totalStatus == "END"){
                salesPlan.setStatus("已完成");
            }else{
                salesPlan.setStatus("进行中");
            }

            map.put("result" , "成功获取数据");
            map.put("salesPlans" , salesPlan);
            return map;
        }
    }

    @RequestMapping("/findSalePlanByCompany")
    public Map<String , Object> findByCompany(@RequestParam("number")String company){
        Map<String , Object> map = new HashMap<>();
        List<SalesPlan> salesPlanList = salePlanService.findByCompany(company);
        if(salesPlanList == null || salesPlanList.size() == 0){
            map.put("result" , "暂无数据");
            return map;
        }else{
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String totalStatus = salesPlanList.get(i).getStatus();
                if(totalStatus == null){
                    salesPlanList.get(i).setStatus("尚未进行");
                }else if(totalStatus == "END"){
                    salesPlanList.get(i).setStatus("已完成");
                }else{
                    salesPlanList.get(i).setStatus("进行中");
                }
            }
            map.put("result" , "成功获取数据");
            map.put("salesPlans" , salesPlanList);
            return map;
        }
    }

    @RequestMapping("/findCompanyBySalePlan")
    public String findComapny(@RequestParam("salePlanNum")String salePlanNum){
        SalesPlan salesPlan = salePlanService.findById(salePlanNum);
        if(salesPlan != null){
            return salesPlan.getCompany();
        }else{
            return "F";
        }
    }

//    @RequestMapping("/findAllSalePlan")
//    public List<SalesPlan> findAll(){
//        return salePlanService.findAllSalePlan();
//    }
}
