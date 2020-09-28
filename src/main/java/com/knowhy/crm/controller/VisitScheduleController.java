package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.entity.CrmSalesplan;
import com.knowhy.crm.entity.CrmVisitschedule;
import com.knowhy.crm.mapper.CrmVisitscheduleMapper;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.pojo.VisitSchedule;
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
import java.util.*;

@RestController
public class VisitScheduleController {

    @Autowired
    VisitScheduleService visitScheduleService;
    @Resource
    CrmVisitscheduleMapper crmVisitscheduleMapper;
    @Autowired
    SalesPlanDAO salesPlanDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/createVisitSchedule")
    public String saveVisit(@RequestParam("salePlanNo")String salePlanNo , @RequestParam("company")String company , @RequestParam("record")String record ,
                            @RequestParam("bdate")String bdate , @RequestParam("creater")String creater , HttpSession session) throws ParseException{
        VisitSchedule visitSchedule = new VisitSchedule();
        if(salePlanNo == null || "".equals(salePlanNo)){
            return "请填写销售计划编号";
        }else{
            String checkResult = visitScheduleService.checkSalePlanNo(salePlanNo);
            if(checkResult.equals("OK")){
                visitSchedule.setNumber(salePlanNo);
            }else{
                return checkResult;
            }
        }
        if (company == null || "".equals(company)){
            return "请填写客户名称";
        }else{
            visitSchedule.setVname(company);
        }
        if(record == null || "".equals(record)){
            return "请填写详细记录";
        }else{
            visitSchedule.setDestination(record);
        }
        if(bdate == null || "".equals(bdate)){
            return "请选择拜访时间";
        }else{
            visitSchedule.setBtime(bdate);
            LocalDate start = LocalDate.parse(bdate , fmt);
            visitSchedule.setBdate(start);
        }
        if(creater == null || "".equals(creater)){
            IUser user = (IUser)session.getAttribute("user");
            visitSchedule.setCreater(user.getAccount());
        }else{
            visitSchedule.setCreater(creater);
        }
        visitSchedule.setCreateDate(new Date());
        try{
            visitScheduleService.save(visitSchedule);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/findVisitScheduleByPage")
    public Map<String , Object> findVisitSchedule(int start){
        Map<String , Object> map = new HashMap<>();
        QueryWrapper<CrmVisitschedule> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<CrmVisitschedule> page = new Page<>(start,10);  // 查询第1页，每页返10条
        IPage<CrmVisitschedule> iPage = crmVisitscheduleMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }
        List<CrmVisitschedule> crmVisitscheduleList = iPage.getRecords();
        map.put("visitSchedules" , crmVisitscheduleList);
        map.put("pages" , size);

        return map;
    }

    @RequestMapping("/findVisitByParam")
    public Map<String , Object> getVisitByParam(String search){
        search = search.trim();
        Map<String , Object> map = new HashMap<>();
        if(visitScheduleService.isSalePlanNum(search)){
            List<VisitSchedule> visitScheduleList = visitScheduleService.findByNumber(search);
            if(visitScheduleList != null && visitScheduleList.size() != 0){
                map.put("result" , "OK");
                map.put("visitSchedules" , visitScheduleList);
            }else{
                map.put("result" , "NO");
            }
        }else{
            List<VisitSchedule> visitScheduleList = visitScheduleService.findByName(search);
            if(visitScheduleList != null && visitScheduleList.size() != 0){
                map.put("result" , "OK");
                map.put("visitSchedules" , visitScheduleList);
            }else{
                map.put("result" , "NO");
            }
        }
        return map;
    }

}
