package com.knowhy.crm.service;

import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SalePlanService {

    @Autowired
    SalesPlanDAO salesPlanDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getNumeric(String str) {
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public void createSalePlan(SalesPlan salesPlan){
        salesPlanDAO.save(salesPlan);
    }

    public SalesPlan findById(String salePlanNumber){
        return salesPlanDAO.getOne(salePlanNumber);
    }

    public List<SalesPlan> findAllSalePlan(){
        return salesPlanDAO.findAll();
    }

    public List<SalesPlan> findByCompany(String company){
        return salesPlanDAO.findByCustomerName(company);
    }


    public boolean salePlanExist(String salePlanNo){
        return salesPlanDAO.existsById(salePlanNo);
    }

    //自动生成销售计划编号:固定字符串"knowhy"+日期""
    public String generateNumber(){
        Date now = new Date();
        String createDate = sdf.format(now);
        String create = getNumeric(createDate);
        List<SalesPlan> salesPlanList = salesPlanDAO.findByMakeDate(LocalDate.now());
        int size = 0;
        if(salesPlanList == null || salesPlanList.size() == 0){
            size = 1;
        }else{
            size = salesPlanList.size()+1;
        }
        String no = String.valueOf(size);
        int stringSize = no.length();
        String  target = "";
        if(stringSize < 4){
            int num = 4 - stringSize;

            for(int i = 0 ; i < num ; i++){
                target = target + "0";
            }
            target = "knowhy" + create + target + no;
        }
        return target;
    }

    public List<SalesPlan> findAllCanOperate(String account , String status){
        return salesPlanDAO.findByPlanStatus(status);
    }

    public int getStep(String salePlanID){
        SalesPlan plan = salesPlanDAO.getOne(salePlanID);
        Integer step = plan.getStep();
        if(step == null){
            return 1;
        }else{
            return step;
        }
    }
}
