package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.knowhy.crm.entity.CrmCompetitor;
import com.knowhy.crm.mapper.CrmCompetitorMapper;
import com.knowhy.crm.pojo.Competitor;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.service.CompetitorService;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.function.Function;

@RestController
public class CompetitorController {

    @Autowired
    CompetitorService competitorService;
    @Resource
    CrmCompetitorMapper crmCompetitorMapper;

    @RequestMapping("/saveCompetitor")
    public String createNewCompetitor(HttpSession session , String competitorName , String owner , String province , String city ,String address , String advantage , String website , String product , String customer , String manace ){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        Date now = new Date();
        Competitor competitor = new Competitor();
        competitor.setCreater(account);
        competitor.setCreateDate(now);
        competitor.setUpdatePerson(account);
        competitor.setUpdateDate(now);
        if(competitorName == null || "".equals(competitorName)){
            return "请输入竞争对手名称";
        }else{
            competitor.setCompetitorName(competitorName);
        }
        if(owner == null || "".equals(owner)){
            return "请输入竞争对手所有人";
        }else{
            competitor.setOwner(owner);
        }
        if(province == null || "".equals(province)){
            return "请选择省";
        }else{
            competitor.setProvince(province);
        }
        if(city == null || "".equals(city)){
            return "请选择市";
        }else{
            competitor.setCity(city);
        }
        if(address == null || "".equals(address)){
            return "请输入地址";
        }else{
            competitor.setAddress(address);
        }
        if(website == null || "".equals(website)){
            return "请输入公司网址";
        }else{
            competitor.setWebsite(website);
        }
        if(manace == null || "".equals(manace)){
            return "请选择是否存在威胁";
        }else{
            competitor.setManace(manace);
        }
        if(product != null && !"".equals(product)){
            competitor.setProduct(product);
        }
        if(customer != null && !"".equals(customer)){
            competitor.setCustomer(customer);
        }
        if(advantage != null && !"".equals(advantage)){
            competitor.setAdvantage(advantage);
        }

        try{
            competitorService.saveCompetitor(competitor);
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    @RequestMapping("/getCompetitorByName")
    public List<Competitor> getByName(String name){
        return competitorService.findByName(name);
    }

    @RequestMapping("/findAllCompetitor")
    public Map<String , Object> getAllCompetitor(int start){
        QueryWrapper<CrmCompetitor> competitorQueryWrapper = new QueryWrapper<>();
        competitorQueryWrapper.orderByDesc("updateDate");
        Page<CrmCompetitor> page = new Page<>(start , 10);
        IPage<CrmCompetitor> iPage = crmCompetitorMapper.selectPage(page , competitorQueryWrapper);

        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }

        Map<String , Object> map = new HashMap<>();
        map.put("pages" , size);
        map.put("competitors" , iPage.getRecords());
        return map;
    }

}
