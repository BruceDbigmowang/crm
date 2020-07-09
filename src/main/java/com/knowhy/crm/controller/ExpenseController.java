package com.knowhy.crm.controller;

import com.knowhy.crm.pojo.ExpenseDetail;
import com.knowhy.crm.pojo.ExpenseHead;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/createExpenseHead")
    public String newExpenseHead(HttpSession session , @RequestParam("costNum")String costNum , @RequestParam("costType")String costType , @RequestParam("employee")String employee , @RequestParam("dept")String dept , @RequestParam("costDate")String costDate){
        ExpenseHead expenseHead = new ExpenseHead();
        expenseHead.setExpenseNum(costNum);
        expenseHead.setLeafType(costType);
        expenseHead.setPrincipal(employee);
        expenseHead.setDept(dept);
        Date now = new Date();
        expenseHead.setCreateDate(now);
        IUser user = (IUser)session.getAttribute("user");
        String userAccount = user.getAccount();
        expenseHead.setCreater(userAccount);
        expenseHead.setUpdater(userAccount);
        expenseHead.setUpdateDate(now);
        try{
            Date expenseDate = sdf.parse(costDate);
            expenseHead.setExpenseDate(expenseDate);
        }catch (Exception e){
            return e.getMessage();
        }

        try{
            expenseService.saveExpanse(expenseHead);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/getAllExpenseHead")
    public List<ExpenseHead> findAll(){
        return expenseService.findAllHead();
    }

    @RequestMapping("/writeExpenseDetail")
    @Transactional
    public String writeMoreExpense(int hId , String spendType , String happenDate , String person , String sdept , String customer , String samount , String appliedAmount , String bamount , String budgetNum){
        ExpenseDetail expenseDetail = new ExpenseDetail();
        expenseDetail.setHeadId(hId);
        try{
            LocalDate happen = LocalDate.parse(happenDate , fmt);
            expenseDetail.setHappenDate(happen);
        }catch (Exception e){
            return e.getMessage();
        }
        expenseDetail.setEmployee(person);
        expenseDetail.setDept(sdept);
        expenseDetail.setCustomer(customer);
        try{
            BigDecimal sAmount = new BigDecimal(samount);
            expenseDetail.setAmount(sAmount);
            BigDecimal applied = new BigDecimal(appliedAmount);
            expenseDetail.setApplyAmount(applied);
            BigDecimal reimburse = new BigDecimal(bamount);
            expenseDetail.setReimburseAmount(reimburse);
        }catch (Exception e){
            return e.getMessage();
        }
        expenseDetail.setBudgetNum(budgetNum);

        try{
            expenseService.saveExpenseMore(expenseDetail);
            ExpenseHead expenseHead = expenseService.getOne(hId);
            expenseHead.setCheckStatus("O");
            expenseService.saveExpanse(expenseHead);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";

    }
}
