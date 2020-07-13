package com.knowhy.crm.controller;

import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.IncomeDetail;
import com.knowhy.crm.pojo.IncomeHead;
import com.knowhy.crm.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IncomeController {
    @Autowired
    IncomeService incomeService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @RequestMapping("/saveIncomeHead")
    public String saveIHead(HttpSession session , @RequestParam("incomeNum")String incomeNum , @RequestParam("principal")String principal , @RequestParam("dept")String dept , @RequestParam("customer")String customer , @RequestParam("serverId")String serverId , @RequestParam("incomeDate")String incomeDate , @RequestParam("leafType")String leafType , @RequestParam("leafNum")String leafNum){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Date now = new Date();
        IncomeHead incomeHead = new IncomeHead();
        incomeHead.setIncomeNum(incomeNum);
        incomeHead.setEmployee(principal);
        incomeHead.setDept(dept);
        incomeHead.setCreater(account);
        incomeHead.setCreateDate(now);
        incomeHead.setUpdatePerson(account);
        incomeHead.setUpdateDate(now);
        incomeHead.setCustomer(customer);
        incomeHead.setRequestNum(serverId);
        try{
            LocalDate incomeTime = LocalDate.parse(incomeDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            incomeHead.setIncomeDate(incomeTime);
        }catch (Exception e){
            return e.getMessage();
        }
        incomeHead.setLeafType(leafType);
        incomeHead.setLeafNum(leafNum);
        incomeHead.setCheckStatus("W");
        try{
            incomeService.wirteIncomeHead(incomeHead);
        }catch (Exception e){
            return e.getMessage();
        }
        return "数据保存成功";
    }

    @RequestMapping("/getAllCanWrite")
    public Object getCanWrite(HttpSession session , @RequestParam("status")String status){
        IUser user = (IUser) session.getAttribute("user");
        String account = user.getAccount();
        List<IncomeHead> incomeHeadList = incomeService.findByAccountAndStatus(account , status);
        Map<String , Object> map = new HashMap<>();
        map.put("incomes" , incomeHeadList);
        return map;
    }

    @RequestMapping("/saveIncomeDetail")
    public String saveDetail(HttpSession session , @RequestParam("iId")int iId , @RequestParam("incomeCode")String incomeCode , @RequestParam("incomeName")String incomeName , @RequestParam("employee")String employee , @RequestParam("dept")String dept , @RequestParam("happenDate")String happenDate , @RequestParam("amount")String amount , @RequestParam("associate")String associate , @RequestParam("note")String note){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Date now = new Date();
        IncomeDetail incomeDetail = new IncomeDetail();
        incomeDetail.setHid(iId);
        incomeDetail.setIncomeType(incomeCode);
        incomeDetail.setIncomeName(incomeName);
        incomeDetail.setEmployee(employee);
        incomeDetail.setDept(dept);
        try{
            LocalDate incomeTime = LocalDate.parse(happenDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            incomeDetail.setHappenDate(incomeTime);
        }catch (Exception e){
            return e.getMessage();
        }
        try{
            BigDecimal money = new BigDecimal(amount).setScale(4 , BigDecimal.ROUND_HALF_UP);
            incomeDetail.setAmount(money);
            BigDecimal associateAmount = new BigDecimal(associate).setScale(4,BigDecimal.ROUND_HALF_UP);
            incomeDetail.setAssociateAmount(associateAmount);
        }catch (Exception e){
            return e.getMessage();
        }
        if(!"".equals(note) && note != null){
            incomeDetail.setNote(note);
        }
        try{
            incomeService.writeIncomeDetail(incomeDetail);
            IncomeHead incomeHead = incomeService.findHeadById(iId);
            incomeHead.setUpdatePerson(account);
            incomeHead.setUpdateDate(now);
            incomeHead.setCheckStatus("O");
            incomeService.wirteIncomeHead(incomeHead);
        }catch (Exception e){
            return e.getMessage();
        }
        return "数据保存成功";
    }

    @RequestMapping("/findTotalIncome")
    public Object findByHID(int hId){
        IncomeHead incomeHead = incomeService.findHeadById(hId);
        IncomeDetail incomeDetail = incomeService.findDetailByHId(hId);
        Map<String , Object> map = new HashMap<>();
        map.put("head" , incomeHead);
        map.put("detail" , incomeDetail);
        return map;
    }

    @RequestMapping("/approveIncome")
    public String approve(String status , int hId , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Date now = new Date();
        IncomeHead incomeHead = incomeService.findHeadById(hId);
        incomeHead.setCheckStatus(status);
        incomeHead.setCheckPerson(account);
        incomeHead.setCheckDate(now);
        incomeHead.setUpdatePerson(account);
        incomeHead.setUpdateDate(now);
        try{
            incomeService.wirteIncomeHead(incomeHead);
        }catch (Exception e){
            return e.getMessage();
        }
        return "已审批";
    }
}
