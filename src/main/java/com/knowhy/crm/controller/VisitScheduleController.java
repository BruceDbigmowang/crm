package com.knowhy.crm.controller;

import com.knowhy.crm.pojo.VisitSchedule;
import com.knowhy.crm.service.VisitScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class VisitScheduleController {

    @Autowired
    VisitScheduleService visitScheduleService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");

    @RequestMapping("/createVisitSchedule")
    public String saveVisit(@RequestParam("salePlanNum")String salePlanNum , @RequestParam("company")String company , @RequestParam("target")String target ,
    @RequestParam("bdate")String bdate , @RequestParam("edate")String edate , @RequestParam("visitor[]")String[] traveller) throws ParseException{
        VisitSchedule visitSchedule = new VisitSchedule();
        if(salePlanNum == null || "".equals(salePlanNum)){
            return "请填写销售计划编号";
        }else{
            visitSchedule.setNumber(salePlanNum);
        }
        if (company == null || "".equals(company)){
            return "销售计划编号填写有误";
        }else{
            visitSchedule.setVname(company);
        }
        if(target == null || "".equals(target)){
            return "请填写拜访目的";
        }else{
            visitSchedule.setDestination(target);
        }
        if(bdate == null || "".equals(bdate)){
            return "请选择拜访开始时间";
        }else{
            visitSchedule.setBtime(bdate);
            Date start = sdf.parse(bdate);
            visitSchedule.setBdate(start);
        }
        if(edate == null || "".equals(edate)){
            return "请选择拜访结束时间";
        }else{
            visitSchedule.setEtime(edate);
            Date end = sdf.parse(edate);
            visitSchedule.setEdate(end);
        }
        try{
            visitScheduleService.save(visitSchedule);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }



}
