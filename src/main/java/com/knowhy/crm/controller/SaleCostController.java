package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.entity.CrmSalecost;
import com.knowhy.crm.mapper.CrmSalecostMapper;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SaleCost;
import com.knowhy.crm.service.SaleCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
public class SaleCostController {
    @Autowired
    SaleCostService saleCostService;
    @Resource
    CrmSalecostMapper crmSalecostMapper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/saveSaleCost")
    public String saveOneCost(HttpSession session,String costType , String happenDate , String describe , String contract , String costAmount , String principal){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Date now = new Date();
        SaleCost saleCost = new SaleCost();
        saleCost.setCreater(account);
        saleCost.setCreateDate(now);
        if(costType == null || "".equals(costType)){
            return "请输入费用类型";
        }else{
            saleCost.setCostType(costType);
        }
        if(happenDate == null || "".equals(happenDate)){
            return "请输入发生日期";
        }else{
            try{
                LocalDate happen = LocalDate.parse(happenDate , fmt);
                saleCost.setHappenDate(happen);
            }catch (Exception e){
                return e.getMessage();
            }
        }
        if(describe == null || "".equals(describe)){
            return "请输入费用描述";
        }else{
            saleCost.setCostDescribe(describe);
        }
        if(contract == null || "".equals(contract)){
            return "请输入联系人";
        }else{
            saleCost.setContract(contract);
        }
        if(costAmount == null || "".equals(costAmount)){
            return "请输入费用金额";
        }else{
            try{
                BigDecimal amount = new BigDecimal(costAmount);
                saleCost.setCostAmount(amount);
            }catch (Exception e){
                return e.getMessage();
            }
        }
        if(principal == null || "".equals(principal)){
            return "请输入负责人";
        }else{
            saleCost.setPrincipal(principal);
        }
        try{
            saleCostService.saveCost(saleCost);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/findAllByPage")
    public Map<String , Object> findByPage(int start){
        Map<String , Object> map = new HashMap<>();

        QueryWrapper<CrmSalecost> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("createDate");
        Page<CrmSalecost> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmSalecost> iPage = crmSalecostMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }
        List<CrmSalecost> crmSalecosts = iPage.getRecords();
        map.put("costs" , crmSalecosts);
        map.put("pages" , size);

        return map;
    }

    @RequestMapping("/findByTypeAndPrincipal")
    public Map<String , Object> findByParam(String type , String principal){
        List<SaleCost> saleCostList = saleCostService.findByTypeAndPrincipal(type , principal);
        Map<String , Object>map = new HashMap<>();
        map.put("costs" , saleCostList);
        return map;
    }


}
