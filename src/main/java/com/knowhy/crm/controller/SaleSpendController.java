package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.entity.CrmSalespend;
import com.knowhy.crm.entity.CrmTravelplan;
import com.knowhy.crm.mapper.CrmSalespendMapper;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SaleSpend;
import com.knowhy.crm.service.SaleSpendService;
import com.knowhy.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SaleSpendController {

    @Autowired
    UserService userService;
    @Autowired
    SaleSpendService saleSpendService;
    @Resource
    CrmSalespendMapper crmSalespendMapper;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/saveSaleSpend")
    public String createSpend(String costType , String describe , String money , String happenDate , String contract , String principal , HttpSession session){
        SaleSpend saleSpend = new SaleSpend();
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        saleSpend.setCreater(account);
        saleSpend.setCreateDate(new Date());
        if(costType == null || "".equals(costType)){
            return "请输入费用类型";
        }else{
            saleSpend.setCostType(costType);
        }
        if(describe == null || "".equals(describe)){
            return "请填写费用描述";
        }else{
            saleSpend.setDescribe(describe);
        }
        if(money == null || "".equals(money)){
            return "请输入金额";
        }else{
            try{
                BigDecimal amount = new BigDecimal(money).setScale(4 , BigDecimal.ROUND_HALF_UP);
                saleSpend.setAmount(amount);
            }catch (Exception e){
                return "金额输入有误，只允许数字";
            }
        }
        if(happenDate == null || "".equals(happenDate)){
            return "请选择发生日期";
        }else{
            try{
                LocalDate happenTime = LocalDate.parse(happenDate , fmt);
                saleSpend.setHappenDate(happenDate);
                saleSpend.setHappenTime(happenTime);
            }catch (Exception e){
                return "发生日期填写格式错误";
            }
        }
        if(contract == null || "".equals(contract)){
            return "请输入联系人";
        }else{
            saleSpend.setContract(contract);
        }
        if(principal == null || "".equals(principal)){
            return "请输入负责人";
        }else{
            saleSpend.setPrincipal(principal);
        }
        try{
            saleSpendService.saveSaleSpend(saleSpend);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/findSaleSpendByPage")
    public Map<String , Object> findByPage(int start){
        Map<String , Object> map = new HashMap<>();
        QueryWrapper<CrmSalespend> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<CrmSalespend> page = new Page<>(start,10);  // 查询第1页，每页返10条
        IPage<CrmSalespend> iPage = crmSalespendMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }
        List<CrmSalespend> crmSalespendList = iPage.getRecords();
        map.put("pages" , size);
        map.put("spends" , crmSalespendList);
        return map;
    }
    @RequestMapping("/findSaleSpendByParams")
    public Map<String , Object> findByParam(String type , String name){
        Map<String , Object> map = new HashMap<>();

        if(type.equals("0")){
            type = "";
        }
        System.out.println("--->"+type);
        System.out.println("--->"+name);
        List<SaleSpend> saleSpendList = saleSpendService.findSpendByParams(type , name);
        if(saleSpendList == null || saleSpendList.size() == 0){
            map.put("result" , "NO");
        }else{
            map.put("result" , "OK");
            map.put("spends" , saleSpendList);
        }

        return map;
    }
}
