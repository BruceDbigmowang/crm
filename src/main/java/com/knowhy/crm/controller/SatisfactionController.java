package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class SatisfactionController {

    @Autowired
    SatisfactionDAO satisfactionDAO;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    PrincipalListDAO principalListDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RolesDAO rolesDAO;
    @Autowired
    ContractRecordDAO contractRecordDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

    @RequestMapping("/createSatisfactionInfo")
    public String saveSatisfacInfo(String customerCode){
        Customer customer = customerDAO.getOne(customerCode);
        PrincipalList principalList = principalListDAO.findByCustomerCodeAndMainPrincipal(customerCode , "Y").get(0);
        Satisfaction satisfaction = new Satisfaction();
        satisfaction.setCustomerCode(customer.getId());
        satisfaction.setCustomerName(customer.getName());
        satisfaction.setPrincipal(principalList.getName());
        satisfaction.setPhone(principalList.getPhone());
        satisfaction.setEmail(principalList.getEmail());
        satisfaction.setWechat(principalList.getWechat());
        Date now = new Date();
        satisfaction.setYearMonth(sdf.format(now));
        satisfaction.setComplete("N");
        satisfaction.setStatus("N");
        satisfaction.setUsing("Y");
        satisfactionDAO.save(satisfaction);
        return "OK";
    }



    @RequestMapping("/getSatisfactionByAssistant")
    public Map<String , Object> findSatisfactionByAssistant(HttpSession session){
        Map<String , Object>map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String status = "eleventh";
        List<SalesPlan> salesPlanList = salesPlanDAO.findByPlanStatusAndAssistant(status , user.getAccount());
        List<Satisfaction> noSatisfaction = new ArrayList<>();
        List<Satisfaction> satisfactedList = new ArrayList<>();
        for(int i = 0 ; i < salesPlanList.size() ; i++){
            String customerCode = salesPlanList.get(i).getCustomerCode();
            List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
            if(satisfactionList != null && satisfactionList.size() != 0){
                Satisfaction satisfaction = satisfactionList.get(0);
                if(satisfaction.getStatus().equals("N")){
                    noSatisfaction.add(satisfaction);
                }else if(satisfaction.getStatus().equals("Y")){
                    satisfactedList.add(satisfaction);
                }
            }
        }
        for(int i = 0 ; i < noSatisfaction.size() ; i++){
            Satisfaction satisfaction = noSatisfaction.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        for(int i = 0 ; i < satisfactedList.size() ; i++){
            Satisfaction satisfaction = satisfactedList.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        map.put("notSend" , noSatisfaction);
        map.put("send" , satisfactedList);
        return map;
    }

    @RequestMapping("/getSatisfactionByAssistantAndName")
    public Map<String , Object> findSatisfactionByAssistantAndName(HttpSession session , String customerName){
        customerName = "%"+customerName+"%";
        Map<String , Object>map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        String status = "eleventh";
        List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistant(customerName , status , user.getAccount());
        List<Satisfaction> noSatisfaction = new ArrayList<>();
        List<Satisfaction> satisfactedList = new ArrayList<>();
        for(int i = 0 ; i < salesPlanList.size() ; i++){
            String customerCode = salesPlanList.get(i).getCustomerCode();
            List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
            if(satisfactionList != null && satisfactionList.size() != 0){
                Satisfaction satisfaction = satisfactionList.get(0);
                if(satisfaction.getStatus().equals("N")){
                    noSatisfaction.add(satisfaction);
                }else if(satisfaction.getStatus().equals("Y")){
                    satisfactedList.add(satisfaction);
                }
            }
        }
        for(int i = 0 ; i < noSatisfaction.size() ; i++){
            Satisfaction satisfaction = noSatisfaction.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        for(int i = 0 ; i < satisfactedList.size() ; i++){
            Satisfaction satisfaction = satisfactedList.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        map.put("notSend" , noSatisfaction);
        map.put("send" , satisfactedList);
        return map;
    }

    @RequestMapping("/getRelatedSatisfaction")
    public Map<String , Object> findRelatedSatisfaction(HttpSession session){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        if(roles.getRoleName().equals("销售")){
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<SalesPlan> salesPlanList = salesPlanDAO.findByPrincipalAndPlanStatusAndSaleArrange(user.getAccount() , "eleventh" , "C");
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String customerCode = salesPlanList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }else if(roles.getRoleName().equals("技术人员")){
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<ContractRecord> contractRecordList = contractRecordDAO.findByTechnicist(user.getAccount());
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String customerCode = contractRecordList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }else if(roles.getRoleName().equals("供应链人员")){
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<ContractRecord> contractRecordList = contractRecordDAO.findByOperator(user.getAccount());
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String customerCode = contractRecordList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }else{
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<SalesPlan> salesPlanList = salesPlanDAO.findByPlanStatusAndOperator("eleventh" , user.getAccount());
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String customerCode = salesPlanList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }

        return map;
    }

    @RequestMapping("/getRelatedSatisfactionByName")
    public Map<String , Object> findRelatedSatisfactionByName(HttpSession session , String customerName){
        customerName = "%" + customerName + "%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        if(roles.getRoleName().equals("销售")){
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndSaleArrange(customerName , user.getAccount() , "eleventh" , "C");
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String customerCode = salesPlanList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }else if(roles.getRoleName().equals("技术人员")){
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndTechnicist(customerName , user.getAccount());
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String customerCode = contractRecordList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }else if(roles.getRoleName().equals("供应链人员")){
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndOperator(customerName , user.getAccount());
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String customerCode = contractRecordList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }else{
            List<Satisfaction> noSatisfaction = new ArrayList<>();
            List<Satisfaction> satisfactedList = new ArrayList<>();
            List<SalesPlan> salesPlanList = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndOperator(customerName ,"eleventh" , user.getAccount());
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String customerCode = salesPlanList.get(i).getCustomerCode();
                List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactionList != null && satisfactionList.size() != 0){
                    Satisfaction satisfaction = satisfactionList.get(0);
                    if(satisfaction.getStatus().equals("N")){
                        noSatisfaction.add(satisfaction);
                    }else if(satisfaction.getStatus().equals("Y")){
                        satisfactedList.add(satisfaction);
                    }
                }
            }
            for(int i = 0 ; i < noSatisfaction.size() ; i++){
                Satisfaction satisfaction = noSatisfaction.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            for(int i = 0 ; i < satisfactedList.size() ; i++){
                Satisfaction satisfaction = satisfactedList.get(i);
                if(satisfaction.getComplete().equals("Y")){
                    satisfaction.setComplete("是");
                }else{
                    satisfaction.setComplete("否");
                }
            }
            map.put("notSend" , noSatisfaction);
            map.put("send" , satisfactedList);
        }

        return map;
    }

    @RequestMapping("/getSatisfactionList")
    public Map<String , Object> findAllSatisfaction(){
        Map<String , Object> map = new HashMap<>();
        List<Satisfaction> noSatisfaction = satisfactionDAO.findByStatusAndUsing("N" , "Y");
        for(int i = 0 ; i < noSatisfaction.size() ; i++){
            Satisfaction satisfaction = noSatisfaction.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        map.put("notSend" , noSatisfaction);
        List<Satisfaction> satisfactionList = satisfactionDAO.findByStatusAndUsing("Y" , "Y");
        for(int i = 0 ; i < satisfactionList.size() ; i++){
            Satisfaction satisfaction = satisfactionList.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        map.put("send" , satisfactionList);
        return map;
    }
    @RequestMapping("/getSatisfactionListByName")
    public Map<String , Object> findAllSatisfactionByName(String customerName){
        Map<String , Object> map = new HashMap<>();
        List<Satisfaction> noSatisfaction = satisfactionDAO.findByCustomerNameLikeAndStatusAndUsing(customerName , "N" , "Y");
        for(int i = 0 ; i < noSatisfaction.size() ; i++){
            Satisfaction satisfaction = noSatisfaction.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        map.put("notSend" , noSatisfaction);
        List<Satisfaction> satisfactionList = satisfactionDAO.findByCustomerNameLikeAndStatusAndUsing(customerName , "Y" , "Y");
        for(int i = 0 ; i < satisfactionList.size() ; i++){
            Satisfaction satisfaction = satisfactionList.get(i);
            if(satisfaction.getComplete().equals("Y")){
                satisfaction.setComplete("是");
            }else{
                satisfaction.setComplete("否");
            }
        }
        map.put("send" , satisfactionList);
        return map;
    }

    @RequestMapping("/sendLink")
    public String sendSatisfactionLink(int sId){
        Satisfaction satisfaction = satisfactionDAO.getOne(sId);
        String principal = satisfaction.getPrincipal();
        String phone = satisfaction.getPhone();
        String email = satisfaction.getEmail();
        String wechat = satisfaction.getWechat();
        /**
         * 此处省略发送消息的具体步骤
         * 发送消息的方式还未确定
         */
        satisfaction.setStatus("Y");
        satisfactionDAO.save(satisfaction);
        return "OK";
    }

    @RequestMapping("/sendLinkAll")
    public String sendLinkToAll(){
        List<Satisfaction> satisfactionList = satisfactionDAO.findAll();
        for(int i = 0 ; i < satisfactionList.size() ; i++){
            Satisfaction satisfaction = satisfactionList.get(i);

            String principal = satisfaction.getPrincipal();
            String phone = satisfaction.getPhone();
            String email = satisfaction.getEmail();
            String wechat = satisfaction.getWechat();
            /**
             * 此处省略发送消息的具体步骤
             * 发送消息的方式还未确定
             */
            satisfaction.setStatus("Y");
            satisfactionDAO.save(satisfaction);
        }
        return "OK";
    }

    @RequestMapping("/sendLinkByBatch")
    public String sendLinkToSome(int[] sIds){
        for(int i = 0 ; i < sIds.length ; i++){
            int targetId = sIds[i];
            Satisfaction satisfaction = satisfactionDAO.getOne(targetId);
            String principal = satisfaction.getPrincipal();
            String phone = satisfaction.getPhone();
            String email = satisfaction.getEmail();
            String wechat = satisfaction.getWechat();
            /**
             * 此处省略发送消息的具体步骤
             * 发送消息的方式还未确定
             */
            satisfaction.setStatus("Y");
            satisfactionDAO.save(satisfaction);
        }
        return "OK";
    }

}
