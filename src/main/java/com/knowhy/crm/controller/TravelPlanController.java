package com.knowhy.crm.controller;

import com.knowhy.crm.pojo.TravelPlan;
import com.knowhy.crm.service.TravelPlanService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class TravelPlanController {
    @Autowired
    TravelPlanService travelPlanService;

    @RequestMapping("/saveTravelPlan")
    public String createTravelPlan(@RequestParam("salePlanNum")String salePlanNum , @RequestParam("company")String company , @RequestParam("target")String target , @RequestParam("province")String province , @RequestParam("city")String city , @RequestParam("address")String address ,
                                   @RequestParam("bdate")String bdate , @RequestParam("edate")String edate , @RequestParam("traveller[]")String[] traveller) throws ParseException {
        TravelPlan travelPlan = new TravelPlan();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
        if(address != null && "".equals(address)){
            travelPlan.setAddress(address);
        }
        if(bdate == null || "".equals(bdate)){
            return "请选择出差开始时间";
        }else{
            Date start = sdf.parse(bdate);
            travelPlan.setBdate(start);
        }
        if(edate == null){
            return "请选择出差结束时间";
        }else{
            Date end = sdf.parse(edate);
            travelPlan.setEdate(end);
        }
        if(traveller.length == 0){
            return "请填写出差人员工号";
        }else{
            String person = "";
            for(int i = 0 ; i < traveller.length ; i++){
                if(i == traveller.length-1){
                    person = person+traveller[i];
                }else{
                    person = person+traveller[i]+",";
                }
            }
//            System.out.println(person);
            travelPlan.setTraveller(person);
        }

        try{
            travelPlanService.save(travelPlan);
        }catch (Exception e){
            return e.getMessage();
        }

        return "OK";
    }

    @RequestMapping("/findAllTravelPlan")
    public List<TravelPlan> findAll(){
        return travelPlanService.findAll();
    }
}
