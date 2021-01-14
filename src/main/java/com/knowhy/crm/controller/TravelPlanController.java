package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.entity.CrmTravelplan;
import com.knowhy.crm.mapper.CrmTravelplanMapper;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.TravelPlan;
import com.knowhy.crm.service.TravelPlanService;
import com.knowhy.crm.service.VisitScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TravelPlanController {
    @Autowired
    TravelPlanService travelPlanService;
    @Autowired
    VisitScheduleService visitScheduleService;
    @Resource
    CrmTravelplanMapper crmTravelplanMapper;
    @Autowired
    SalesPlanDAO salesPlanDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/saveTravelPlan")
    public String createTravelPlan(@RequestParam("salePlanNum")String salePlanNum , @RequestParam("company")String company , @RequestParam("target")String target , @RequestParam("province")String province , @RequestParam("city")String city , @RequestParam("address")String address ,
                                   @RequestParam("bdate")String bdate , @RequestParam("creater")String creater , HttpSession session) throws ParseException {
        TravelPlan travelPlan = new TravelPlan();

        if(salePlanNum == null || "".equals(salePlanNum)){
            return "请输入销售计划编号";
        }else{
            travelPlan.setSalePlanNumber(salePlanNum);
        }
        if(company == null || "".equals(company)){
            return "销售计划填写错误";
        }else{
            travelPlan.setCustomer(company);
        }
        if(target != null && !"".equals(target)){
            travelPlan.setTarget(target);
        }
        if(province == null || "".equals(province)){
            return "请选择省";
        }else{
            travelPlan.setProvince(province);
        }
        if(city == null || "".equals(city)){
            return "请选择市";
        }else{
            travelPlan.setCity(city);
        }
        if(address.trim() != null && "".equals(address.trim())){
            travelPlan.setAddress(address);
        }
        if(bdate == null || "".equals(bdate)){
            return "请选择出差日期";
        }else{
            LocalDate start = LocalDate.parse(bdate , fmt);
            travelPlan.setBdate(start);
        }
        if(creater == null || "".equals(creater)){
            IUser user = (IUser)session.getAttribute("user");
            creater = user.getAccount();
        }
        travelPlan.setCreater(creater);
        travelPlan.setCreateDate(new Date());
        try{
            travelPlanService.save(travelPlan);
        }catch (Exception e){
            return e.getMessage();
        }

        return "OK";
    }

    @RequestMapping("/findTravelByPage")
    public Map<String , Object> getTravelByPage(int start){
        Map<String , Object> map = new HashMap<>();
        QueryWrapper<CrmTravelplan> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<CrmTravelplan> page = new Page<>(start,10);  // 查询第1页，每页返10条
        IPage<CrmTravelplan> iPage = crmTravelplanMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }
        List<CrmTravelplan> crmTravelplanList = iPage.getRecords();
        map.put("pages" , size);
        map.put("travels" , crmTravelplanList);
        return map;
    }

    @RequestMapping("/searchTravelPlan")
    public Map<String , Object> searchTravel(String param){
        Map<String , Object> map = new HashMap<>();
        boolean checkResult = salesPlanDAO.existsById(param);
        if(checkResult){
            List<TravelPlan> travelPlanList = travelPlanService.findBySalePlanNum(param);
            if(!travelPlanList.isEmpty()){
                map.put("travels" , travelPlanList);
                map.put("result" , "OK");
            }else{
                map.put("result" , "no");
            }
        }else{
            List<TravelPlan> travelPlanList = travelPlanService.findByCustomerName(param);
            if(!travelPlanList.isEmpty()){
                map.put("travels" , travelPlanList);
                map.put("result" , "OK");
            }else{
                map.put("result" , "no");
            }
        }

        return map;
    }

}
