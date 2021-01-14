package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.*;
import com.knowhy.crm.entity.CrmSalesplan;
import com.knowhy.crm.mapper.CrmSalesplanMapper;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.SalePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SalePlanController {

    @Autowired
    SalePlanService salePlanService;
    @Resource
    CrmSalesplanMapper crmSalesplanMapper;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RolesDAO rolesDAO;
    @Autowired
    ContractRecordDAO contractRecordDAO;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping("/getAllSalePlan")
    public Map<String,Object> getAll(@RequestParam("start")int start){
        List<SalesPlan> salesPlanList = salePlanService.findAllSalePlan();
        Map<String , Object> map = new HashMap<>();
        if(salesPlanList.isEmpty()){
            map.put("result" , "暂无数据");
        }else{
            QueryWrapper<CrmSalesplan> queryWrapper =  new QueryWrapper<>();
            queryWrapper.orderByDesc("salesPlanNumber");
            Page<CrmSalesplan> page = new Page<>(start,10);  // 查询第1页，每页返回5条
            IPage<CrmSalesplan> iPage = crmSalesplanMapper.selectPage(page,queryWrapper);
            int total = (int)iPage.getTotal();
            int size;
            if(total%10 == 0){
                size = total/10;
            }else{
                size = total/10 + 1;
            }
            List<CrmSalesplan> salesplanList = iPage.getRecords();
//            for(int i = 0 ; i < salesplanList.size() ; i++){
//                String totalStatus = salesplanList.get(i).getTotalStatus();
//                if(totalStatus == null){
//                    salesplanList.get(i).setTotalStatus("尚未进行");
//                }else if(totalStatus == "END"){
//                    salesplanList.get(i).setTotalStatus("已完成");
//                }else{
//                    salesplanList.get(i).setTotalStatus("进行中");
//                }
//            }
            map.put("result" , "成功获取数据");
            map.put("salePlans" , salesplanList);
            map.put("size" , size);
        }
        return map;
    }

    @RequestMapping("/findSalePlanByNumber")
    public Map<String , Object> findByNumber(@RequestParam("number")String number){
        Map<String , Object> map = new HashMap<>();
        SalesPlan salesPlan = salePlanService.findById(number);
        if(salesPlan == null){
            map.put("result" , "暂无数据");
            return map;
        }else{

            String totalStatus = salesPlan.getPlanStatus();
            if(totalStatus == null){
                salesPlan.setPlanStatus("尚未进行");
            }else if(totalStatus == "END"){
                salesPlan.setPlanStatus("已完成");
            }else{
                salesPlan.setPlanStatus("进行中");
            }

            map.put("result" , "成功获取数据");
            map.put("salesPlans" , salesPlan);
            return map;
        }
    }

    @RequestMapping("/findSalePlanByCompany")
    public Map<String , Object> findByCompany(@RequestParam("number")String company){
        Map<String , Object> map = new HashMap<>();
        List<SalesPlan> salesPlanList = salePlanService.findByCompany(company);
        if(salesPlanList == null || salesPlanList.size() == 0){
            map.put("result" , "暂无数据");
            return map;
        }else{
            for(int i = 0 ; i < salesPlanList.size() ; i++){
                String totalStatus = salesPlanList.get(i).getPlanStatus();
                if(totalStatus == null){
                    salesPlanList.get(i).setPlanStatus("尚未进行");
                }else if(totalStatus == "END"){
                    salesPlanList.get(i).setPlanStatus("已完成");
                }else{
                    salesPlanList.get(i).setPlanStatus("进行中");
                }
            }
            map.put("result" , "成功获取数据");
            map.put("salesPlans" , salesPlanList);
            return map;
        }
    }

    @RequestMapping("/findCompanyBySalePlan")
    public String findComapny(@RequestParam("salePlanNum")String salePlanNum){
        if(salePlanNum == null || "".equals(salePlanNum)){
            return "F";
        }else{
            SalesPlan salesPlan = salePlanService.findById(salePlanNum);
            try{
                if(salesPlan != null){
                    return salesPlan.getCustomerName();
                }else{
                    return "F";
                }
            }catch (Exception e){
                return "F";
            }
        }
    }

    @RequestMapping("/generateSalePlanNum")
    public String generateNum(){
        return salePlanService.generateNumber();
    }

    @RequestMapping("/findCanOperate")
    public List<SalesPlan> getCanOperate(HttpSession session , String status){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        return salePlanService.findAllCanOperate(account , status);
    }

    @RequestMapping("/findCanOperateByName")
    public Map<String, Object> getCanOperateByName(HttpSession session , String status , String customerName){
        customerName = "%"+customerName+"%";
        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }
        IUser user = (IUser)session.getAttribute("user");
        Map<String , Object> map = new HashMap<>();
        String saleArrange = "C";
        List<SalesPlan> planList1 = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndSaleArrange(customerName , user.getAccount() , status , saleArrange);
        map.put("list1" , planList1);
        PageRequest pr = PageRequest.of(0, 10);
        List<SalesPlan> planList2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusInAndPrincipalAndSaleArrangeOrderByUpdateDateDesc(pr  , customerName, statuses , user.getAccount() , saleArrange);
        map.put("list2" , planList2);
        return map;
    }

    @RequestMapping("/findCanOperateByNameAndAdmin")
    public Map<String, Object> getCanOperateByNameAndAdmin(HttpSession session , String status , String customerName){
        customerName = "%"+customerName+"%";
        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }
        IUser user = (IUser)session.getAttribute("user");
        Map<String , Object> map = new HashMap<>();
        String saleArrange = "C";
        List<SalesPlan> planList1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndSaleArrange(customerName , status , saleArrange);
        map.put("list1" , planList1);
        PageRequest pr = PageRequest.of(0, 10);
        List<SalesPlan> planList2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusInAndSaleArrangeOrderByUpdateDateDesc(pr  , customerName, statuses , saleArrange);
        map.put("list2" , planList2);
        return map;
    }

    @RequestMapping("/findCanOperateAndOperated")
    public Map<String , Object> findOperateAndOperated(String status , HttpSession session){
        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }
        IUser user = (IUser)session.getAttribute("user");
        Map<String , Object> map = new HashMap<>();
        String saleArrange = "C";
        List<SalesPlan> planList1 = salesPlanDAO.findByPrincipalAndPlanStatusAndSaleArrange(user.getAccount() , status , saleArrange);
        map.put("list1" , planList1);
        PageRequest pr = PageRequest.of(0, 10);
        List<SalesPlan> planList2 = salesPlanDAO.findByPlanStatusInAndPrincipalAndSaleArrangeOrderByUpdateDateDesc(pr , statuses , user.getAccount() , saleArrange);
        map.put("list2" , planList2);
        return map;
    }

    @RequestMapping("/findCanOperateAndOperatedByAdmin")
    public Map<String , Object> findOperateAndOperatedByAdmin(String status , HttpSession session){
        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }
        IUser user = (IUser)session.getAttribute("user");
        Map<String , Object> map = new HashMap<>();
        String saleArrange = "C";
        List<SalesPlan> planList1 = salesPlanDAO.findByPlanStatusAndSaleArrange( status , saleArrange);
        map.put("list1" , planList1);
        PageRequest pr = PageRequest.of(0, 10);
        List<SalesPlan> planList2 = salesPlanDAO.findByPlanStatusInAndSaleArrangeOrderByUpdateDateDesc(pr , statuses , saleArrange);
        map.put("list2" , planList2);
        return map;
    }

    @RequestMapping("/findCanConclude")
    public List<SalesPlan> getCanConclude(String status){
        return salesPlanDAO.findByPlanStatus(status);
    }

    @RequestMapping("/findCanArrange")
    public List<SalesPlan> getCanArrange(HttpSession session , String status){
        IUser user = (IUser)session.getAttribute("user");
        return salesPlanDAO.findByPlanStatusAndAssistant(status , user.getAccount());
    }

    @RequestMapping("/findCanArrangeByName")
    public List<SalesPlan> getCanArrangeByName(HttpSession session , String customerName , String status){
        customerName = "%"+customerName+"%";
        IUser user = (IUser)session.getAttribute("user");
        return salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistant(customerName , status , user.getAccount());
    }

    @RequestMapping("/findSalePlanByOperatorService")
    public Map<String , Object> getSalesByOperatorService(HttpSession session , String status){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByPlanStatusAndOperatorAndLastTimeAfterAndServiceWriteOrderByUpdateDate(status , user.getAccount() , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByPlanStatusAndOperatorAndLastTimeAfterAndServiceWriteOrderByUpdateDate(status , user.getAccount() , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/findSalePlanByOperatorServiceAndName")
    public Map<String , Object> getSalesByOperatorService(String customerName , HttpSession session , String status){
        customerName = "%" + customerName +"%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndOperatorAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , status , user.getAccount() , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndOperatorAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , status , user.getAccount() , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/findRelatedSalePlanByOperatorService")
    public Map<String , Object> getRelatedSalesByOperatorService(HttpSession session , String status){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        String roleName = roles.getRoleName();
        if(roleName.equals("销售")){
            List<SalesPlan> list1 = salesPlanDAO.findByPrincipalAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(user.getAccount() , status , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByPrincipalAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(user.getAccount() , status , LocalDate.now() , "C");
            map.put("list2" , list2);
        }else if(roleName.equals("技术人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByTechnicist(user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getServiceWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getServiceWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else if(roleName.equals("供应链人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByOperator(user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getServiceWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getServiceWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);
        }else{
            //销售助理
            List<SalesPlan> list1 = salesPlanDAO.findByPlanStatusAndAssistantAndLastTimeAfterAndServiceWriteOrderByUpdateDate( status , user.getAccount() , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByPlanStatusAndAssistantAndLastTimeAfterAndServiceWriteOrderByUpdateDate( status , user.getAccount(), LocalDate.now() , "C");
            map.put("list2" , list2);

        }

        return map;
    }

    @RequestMapping("/findRelatedSalePlanByOperatorServiceAndName")
    public Map<String , Object> getRelatedSalesByOperatorServiceAndName(String customerName , HttpSession session , String status){
        customerName = "%" + customerName + "%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        String roleName = roles.getRoleName();
        if(roleName.equals("销售")){
            List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , user.getAccount() , status , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , user.getAccount() , status , LocalDate.now() , "C");
            map.put("list2" , list2);
        }else if(roleName.equals("技术人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndOperator(customerName , user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getServiceWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getServiceWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else if(roleName.equals("供应链人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndOperator(customerName , user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getServiceWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getServiceWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);
        }else{
            //销售助理
            List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistantAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , status , user.getAccount() , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistantAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , status , user.getAccount(), LocalDate.now() , "C");
            map.put("list2" , list2);

        }

        return map;
    }

    @RequestMapping("/findSalePlanByOperatorServiceAndAdmin")
    public Map<String , Object> getSalesByOperatorServiceAndAdmin(HttpSession session , String status){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(status , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(status , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/findSalePlanByOperatorServiceAndAdminAndName")
    public Map<String , Object> getSalesByOperatorServiceAndAdminAndName(String customerName , HttpSession session , String status){
        customerName = "%" + customerName + "%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , status , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndLastTimeAfterAndServiceWriteOrderByUpdateDate(customerName , status , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/findSalePlanByOperatorReport")
    public Map<String , Object> getSalesByOperatorReport(HttpSession session , String status){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByPlanStatusAndOperatorAndLastTimeAfterAndReportWriteOrderByUpdateDate(status , user.getAccount() , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByPlanStatusAndOperatorAndLastTimeAfterAndReportWriteOrderByUpdateDate(status , user.getAccount() , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/findSalePlanByOperatorReportAndName")
    public Map<String , Object> getSalesByOperatorReport(HttpSession session , String status , String customerName){
        customerName = "%"+customerName+"%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndOperatorAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , status , user.getAccount() , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndOperatorAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , status , user.getAccount() , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/findRelatedSalePlanByOperatorReport")
    public Map<String , Object> getRelatedSalesByOperatorReport(HttpSession session , String status){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        if(roles.getRoleName().equals("销售")){
            List<SalesPlan> list1 = salesPlanDAO.findByPrincipalAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(user.getAccount() , status , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByPrincipalAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(user.getAccount() , status , LocalDate.now() , "C");
            map.put("list2" , list2);
        }else if(roles.getRoleName().equals("技术人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByTechnicist(user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getReportWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getReportWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else if(roles.getRoleName().equals("供应链人员")){

            List<ContractRecord> contractRecordList = contractRecordDAO.findByOperator(user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getReportWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getReportWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else{
            List<SalesPlan> list1 = salesPlanDAO.findByPlanStatusAndAssistantAndLastTimeAfterAndReportWriteOrderByUpdateDate(status , user.getAccount() , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByPlanStatusAndAssistantAndLastTimeAfterAndReportWriteOrderByUpdateDate(status , user.getAccount() , LocalDate.now() , "C");
            map.put("list2" , list2);
        }

        return map;
    }

    @RequestMapping("/findRelatedSalePlanByOperatorReportAndName")
    public Map<String , Object> getRelatedSalesByOperatorReport(String customerName , HttpSession session , String status){
        customerName = "%" + customerName + "%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<UserRole> userRoleList = userRoleDAO.findByAccount(user.getAccount());
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        if(roles.getRoleName().equals("销售")){
            List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , user.getAccount() , status , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , user.getAccount() , status , LocalDate.now() , "C");
            map.put("list2" , list2);
        }else if(roles.getRoleName().equals("技术人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndTechnicist(customerName , user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getReportWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getReportWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else if(roles.getRoleName().equals("供应链人员")){

            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndOperator(customerName , user.getAccount());
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                SalesPlan salesPlan = salesPlanDAO.getOne(contractRecordList.get(i).getSalePlanID());
                if(salesPlan.getPlanStatus().equals(status)){
                    if(salesPlan.getLastTime().isAfter(LocalDate.now())){
                        if(salesPlan.getReportWrite().equals("O")){
                            list1.add(salesPlan);
                        }else if(salesPlan.getReportWrite().equals("C")){
                            list2.add(salesPlan);
                        }
                    }
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else{
            List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistantAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , status , user.getAccount() , LocalDate.now() , "O");
            map.put("list1" , list1);
            List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndAssistantAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , status , user.getAccount() , LocalDate.now() , "C");
            map.put("list2" , list2);
        }

        return map;
    }

    @RequestMapping("/findOperateReportByAdmin")
    public Map<String , Object> getOperateReportByAdmin( HttpSession session , String status){
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(status , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(status , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/findOperateReportByAdminAndName")
    public Map<String , Object> getOperateReportByAdminAndName(String customerName , HttpSession session , String status){
        customerName = "%"+customerName+"%";
        Map<String , Object> map = new HashMap<>();
        IUser user = (IUser)session.getAttribute("user");
        List<SalesPlan> list1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , status , LocalDate.now() , "O");
        map.put("list1" , list1);
        List<SalesPlan> list2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndLastTimeAfterAndReportWriteOrderByUpdateDate(customerName , status , LocalDate.now() , "C");
        map.put("list2" , list2);
        return map;
    }

    @RequestMapping("/giveUpSalePlan")
    @Transactional
    public String toGiveUp( String salePlanID){
        SalesPlan salesPlan = salePlanService.findById(salePlanID);
        salesPlan.setPrincipal(null);
        salesPlan.setPrincipalName(null);

        String customerID = salesPlan.getCustomerCode();
        Customer customer = customerDAO.getOne(customerID);
        customer.setFollowPerson(null);
        customer.setFollowName(null);
        customer.setFollowStatus("O");

        try{
            salePlanService.createSalePlan(salesPlan);
            customerDAO.save(customer);

            System.out.println(salesPlan);
            taskDAO.deleteBySalePlanID(salePlanID);
            System.out.println("删除任务表1");
            taskSumDAO.deleteBySalePlanID(salePlanID);
            System.out.println("删除任务表2");
        }catch (Exception e){
            return e.getMessage();
        }
        return "OK";
    }

    /**
     * 在运营服务过程中，除具有操作权限的人员（销售助理、运营人员）
     * 其它相关人员，如：销售，技术，供应链等人员需要查看相关信息
     * -----------计划排程---------------
     */
    @RequestMapping("/getRelatedSalePlan")
    public Map<String , Object> findRelatedSalePlan(HttpSession session , String status){
        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }

        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<UserRole> userRoleList = userRoleDAO.findByAccount(account);
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        String roleName = roles.getRoleName();
        Map<String , Object> map = new HashMap<>();
        if(roleName.equals("销售")){
            List<SalesPlan> planList1 = salesPlanDAO.findByPrincipalAndPlanStatusAndSaleArrange(account , status , "C");
            map.put("list1" , planList1);
            PageRequest pr = PageRequest.of(0, 10);
            List<SalesPlan> planList2 = salesPlanDAO.findByPlanStatusInAndPrincipalAndSaleArrangeOrderByUpdateDateDesc(pr , statuses , user.getAccount() , "C");
            map.put("list2" , planList2);

        }else if(roleName.equals("技术人员")){
            /**
             * 查询合同归档的表，首先查询出相关联的客户，再筛选出符合要求的信息
             */
            List<ContractRecord> contractRecordList = contractRecordDAO.findByTechnicist(account);
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String salePlanID = contractRecordList.get(i).getSalePlanID();
                SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
                if(salesPlan.getPlanStatus().equals(status) && salesPlan.getOperateArrange().equals("O")){
                    list1.add(salesPlan);
                }
                if(statuses.contains(salesPlan.getPlanStatus()) && salesPlan.getOperateArrange().equals("C")){
                    list2.add(salesPlan);
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else if(roleName.equals("供应链人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByOperator(account);
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String salePlanID = contractRecordList.get(i).getSalePlanID();
                SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
                if(salesPlan.getPlanStatus().equals(status) && salesPlan.getOperateArrange().equals("O")){
                    list1.add(salesPlan);
                }
                if(statuses.contains(salesPlan.getPlanStatus()) && salesPlan.getOperateArrange().equals("C")){
                    list2.add(salesPlan);
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);
        }else{
            List<SalesPlan> salesPlanList1 = salesPlanDAO.findByPlanStatusAndOperatorAndOperateArrange(status , account , "C");
            map.put("list1" , salesPlanList1);
            List<SalesPlan> salesPlanList2 = salesPlanDAO.findByPlanStatusInAndOperatorAndOperateArrange(statuses , account , "C");
            map.put("list2" , salesPlanList2);
        }
        return map;
    }


    @RequestMapping("/getRelatedSalePlanByName")
    public Map<String , Object> findRelatedSalePlanByName(HttpSession session  , String customerName , String status){
        customerName = "%"+customerName+"%";
        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }

        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<UserRole> userRoleList = userRoleDAO.findByAccount(account);
        int roleId = userRoleList.get(0).getId();
        Roles roles = rolesDAO.getOne(roleId);
        String roleName = roles.getRoleName();
        Map<String , Object> map = new HashMap<>();
        if(roleName.equals("销售")){
            List<SalesPlan> planList1 = salesPlanDAO.findByCustomerNameLikeAndPrincipalAndPlanStatusAndSaleArrange(customerName , account , status , "C");
            map.put("list1" , planList1);
            PageRequest pr = PageRequest.of(0, 10);
            List<SalesPlan> planList2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusInAndPrincipalAndSaleArrangeOrderByUpdateDateDesc(pr , customerName , statuses , user.getAccount() , "C");
            map.put("list2" , planList2);

        }else if(roleName.equals("技术人员")){
            /**
             * 查询合同归档的表，首先查询出相关联的客户，再筛选出符合要求的信息
             */
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndTechnicist(customerName , account);
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String salePlanID = contractRecordList.get(i).getSalePlanID();
                SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
                if(salesPlan.getPlanStatus().equals(status) && salesPlan.getOperateArrange().equals("O")){
                    list1.add(salesPlan);
                }
                if(statuses.contains(salesPlan.getPlanStatus()) && salesPlan.getOperateArrange().equals("C")){
                    list2.add(salesPlan);
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);

        }else if(roleName.equals("供应链人员")){
            List<ContractRecord> contractRecordList = contractRecordDAO.findByCustomerNameLikeAndOperator(customerName , account);
            List<SalesPlan> list1 = new ArrayList<>();
            List<SalesPlan> list2 = new ArrayList<>();
            for(int i = 0 ; i < contractRecordList.size() ; i++){
                String salePlanID = contractRecordList.get(i).getSalePlanID();
                SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
                if(salesPlan.getPlanStatus().equals(status) && salesPlan.getOperateArrange().equals("O")){
                    list1.add(salesPlan);
                }
                if(statuses.contains(salesPlan.getPlanStatus()) && salesPlan.getOperateArrange().equals("C")){
                    list2.add(salesPlan);
                }
            }
            map.put("list1" , list1);
            map.put("list2" , list2);
        }else{
            List<SalesPlan> salesPlanList1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusAndOperatorAndOperateArrange(customerName , status , account , "C");
            map.put("list1" , salesPlanList1);
            List<SalesPlan> salesPlanList2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusInAndOperatorAndOperateArrange(customerName , statuses , account , "C");
            map.put("list2" , salesPlanList2);
        }
        return map;
    }


    @RequestMapping("/getAllScheduleCustomer")
    public Map<String , Object> findAllScheduleCustomer(String status){
        Map<String , Object> map = new HashMap<>();

        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }
         List<SalesPlan> salesPlanList1 = salesPlanDAO.findByPlanStatus(status);
        map.put("list1" , salesPlanList1);
        List<SalesPlan> salesPlanList2 = salesPlanDAO.findByPlanStatusIn(statuses);
        map.put("list2" , salesPlanList2);
        return map;
    }

    @RequestMapping("/getAllScheduleCustomerByName")
    public Map<String , Object> findAllScheduleCustomerByName(String customerName , String status){
        customerName = "%"+customerName + "%";
        Map<String , Object> map = new HashMap<>();

        List<String> statuses = new ArrayList<>();
        statuses.add("first");
        statuses.add("second");
        statuses.add("third");
        statuses.add("fourth");
        statuses.add("fifth");
        statuses.add("sixth");
        statuses.add("seventh");
        statuses.add("eighth");
        statuses.add("ninth");
        statuses.add("tenth");
        statuses.add("eleventh");
        statuses.add("over");
        String[] statusList = {"first" , "second" , "third" , "fourth" , "fifth"  , "sixth" , "seventh" , "eighth" , "ninth" , "tenth" , "eleventh" , "over"};
        List<String> ee = new ArrayList<>();
        for(int i = 0 ; i < statusList.length ; i++){
            ee.add(statusList[i]);
            if(statusList[i].equals(status)){
                break;
            }
        }
        for(int i = 0 ; i < ee.size() ; i++){
            statuses.remove(ee.get(i));
        }
        List<SalesPlan> salesPlanList1 = salesPlanDAO.findByCustomerNameLikeAndPlanStatus(customerName , status);
        map.put("list1" , salesPlanList1);
        List<SalesPlan> salesPlanList2 = salesPlanDAO.findByCustomerNameLikeAndPlanStatusIn(customerName , statuses);
        map.put("list2" , salesPlanList2);
        return map;
    }
}
