package com.knowhy.crm.controller;

import com.knowhy.crm.dao.*;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class IntroduceController {

    @Autowired
    CustomerDetailDAO customerDetailDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    CompanyInfoDAO companyInfoDAO;
    @Autowired
    TaskDAO taskDAO;
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    TaskService taskService;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    PrincipalListDAO principalListDAO;
    @Autowired
    AddressListDAO addressListDAO;
    @Autowired
    ArrangeRecordDAO arrangeRecordDAO;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 该阶段 以进一步收集填写客户信息为主
     * 若数据填写并保存成功 则进入下一阶段
     * 主要任务：
     * 1、填写并保存客户信息
     * 2、修改销售订单的状态，进入下一阶段
     * @return
     */
    @RequestMapping("/saveIntroduceTask")
    @Transactional
    public String saveIntroduce(String salePlanID  , String condition , String bussinessNature , String product , String competitor , String useAmount , String[] names , String[] phones , String[] emails , String[] wechats , String[] sexs , String[] positions , String[] hobbies , String[] relations , String[] birthdays , String[] mainPrincipal , String[] address , String[] describes , String[] areas , String[] postcodes , String[] principals , String[] principalTels , String[] mainAddress , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String maker = user.getAccount();
        String makerName = user.getName();
        CustomerDetail customer = new CustomerDetail();
        if(salePlanID == null || "".equals(salePlanID)){
            return "程序出错";
        }else{
            customer.setSalePlanID(salePlanID);
        }
        SalesPlan plan = salesPlanDAO.getOne(salePlanID);
        String customerCode = plan.getCustomerCode();
        String customerName = plan.getCustomerName();
        customer.setCustomerID(customerCode);
        customer.setCustomerName(customerName);
        Customer customer1 = customerDAO.getOne(customerCode);
        customer.setAddress(customer1.getCompanyAddress());
        if(condition == null || "".equals(condition)){
            return "请输入基本情况";
        }else{
            customer.setCondition(condition);
        }
        if(bussinessNature != null && !"".equals(bussinessNature)){
            customer.setNature(bussinessNature);
        }
        if(product != null && !"".equals(product)){
            customer.setProduct(product);
        }
        if(competitor != null && !"".equals(competitor)){
            customer.setCompetitor(competitor);
        }
        if(useAmount != null && !"".equals(useAmount)){
            try{
                BigDecimal used = new BigDecimal(useAmount).setScale(4,BigDecimal.ROUND_HALF_UP);
                customer.setUseAmount(used);
            }catch (Exception e){
                return "年刀具用量输入错误（只能填写数字）";
            }
        }
        if(names == null || names.length == 0){
            return "请填写联系人姓名";
        }

        List<PrincipalList> principalLists = new ArrayList<>();
        for(int i = 0 ; i < names.length ; i++){
            PrincipalList principalList = new PrincipalList();
            principalList.setCustomerCode(customerCode);
            principalList.setCustomerName(customerName);
            int target = i + 1;
            String name = names[i];
            if("".equals(name)){
                return "第"+target+"行联系人姓名未填写";
            }else{
                principalList.setName(name);
            }
            if(phones != null && phones.length == names.length){
                String phone = phones[i].trim();
                if("".equals(phone)){
                    return "第"+target+"行联系人电话未填写";
                }else{
                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher isNum = pattern.matcher(phone);
                    if( !isNum.matches() ){
                        return "第"+target+"行联系人电话填写错误（只能填写数字）";
                    }else{
                        principalList.setPhone(phone);
                    }
                }
            }else{
                return "联系人电话是必填项";
            }
            if(emails != null && emails.length == names.length){
                String email = emails[i];
                if("".equals(email)){
                    return "第"+target+"行邮箱未填写";
                }else{
                    String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
                    Pattern p = Pattern.compile(str);
                    Matcher m = p.matcher(email);
                    if(!m.matches()){
                        return "第"+target+"行邮箱格式填写错误";
                    }else{
                        principalList.setEmail(email);
                    }
                }
            }else{
                return "联系人邮箱是必填项";
            }
            if(wechats != null && wechats.length == names.length){
                String wechat = wechats[i];
                if("".equals(wechat)){
                    return "第"+target+"行微信号未填写";
                }else{
                    principalList.setWechat(wechat);
                }
            }else{
                return "联系人微信是必填项";
            }
            if(sexs != null && sexs.length == names.length){
                String sex = sexs[i];
                if("0".equals(sex)){
                    return "请选择第"+target+"行联系人性别";
                }else{
                    principalList.setSex(sex);
                }
            }else{
                return "联系人性别是必填项";
            }
            if(positions != null && positions.length > i){
                String position = positions[i].trim();
                if("".equals(position)){
                    return "第"+target+"行职位未填写";
                }else{
                    principalList.setPosition(position);
                }
            }
            if(hobbies != null && hobbies.length > i){
                String hobby = hobbies[i];
                if(!"".equals(hobby)){
                    principalList.setHobby(hobby);
                }
            }
            if(relations != null && relations.length > i){
                String relationship = relations[i];
                if(!"".equals(relationship)){
                    principalList.setRelationship(relationship);
                }
            }
            if(birthdays != null && birthdays.length > i){
                String birth = birthdays[i];
                if("".equals(birth)){
                    return "第"+target+"行联系人生日未填写";
                }else{
                    try{
                        LocalDate birthday = LocalDate.parse(birth , fmt);
                        principalList.setBirthday(birthday);
                    }catch (Exception e){
                        return "第"+target+"行联系人生日格式错误（yyyy-MM-dd）";
                    }
                }
            }
            List<String> mainPr = Arrays.asList(mainPrincipal);
            if(!mainPr.contains("Y")){
                return "请选择主要联系人";
            }
            principalList.setMainPrincipal(mainPrincipal[i]);
            principalLists.add(principalList);
            principalList.setCreateDate(LocalDate.now());
            principalList.setCreater(user.getAccount());
        }

        if(address == null || address.length == 0){
            return "地址是必填项";
        }
        List<AddressList> addressLists = new ArrayList<>();
        for(int i = 0 ; i < address.length ; i++){
            int target = i + 1;
            String addr = address[i];

            AddressList addressList = new AddressList();

            addressList.setCustomerCode(customerCode);
            addressList.setCustomerName(customerName);

            if("".equals(addr)){
                return "第"+target+"行地址未填写";
            }else{
                addressList.setAddress(addr);
            }
            if(describes != null && describes.length == address.length){
                String describe = describes[i];
                if("".equals(describe)){
                    return "第"+target+"行地址说明未填写";
                }else{
                    addressList.setDescribe(describe);
                }
            }else{
                return "地址说明是必填项";
            }
            String area = areas[i];
            if("0".equals(area)){
                return "请选择第"+target+"行所在地区";
            }else{
                addressList.setArea(area);
            }
            if(postcodes != null && postcodes.length > i){
                String postcode = postcodes[i];
                if(!"".equals(postcode)){
                    if(postcode.length() != 6){
                        return "第"+target+"行邮编填写不符规则";
                    }else{
                        addressList.setPostcode(postcode);
                    }

                }
            }
            if(principals != null && principals.length == address.length){
                String contact = principals[i];
                if("".equals(contact)){
                    return "第"+target+"行联系人未填写";
                }else{
                    addressList.setContacts(contact);
                }
            }else{
                return "地址中联系人是必填项";
            }
            if(principalTels != null && principalTels.length == address.length){
                String contactWay = principalTels[i];
                if("".equals(contactWay)){
                    return "第"+target+"行联系方式未填写";
                }else{
                    addressList.setPhone(contactWay);
                }
            }else{
                return "地址中联系方式是必填项";
            }
            List<String> mainAdd = Arrays.asList(mainAddress);
            if(!mainAdd.contains("Y")){
                return "请选择主要地址";
            }
            addressList.setMainAddress(mainAddress[i]);
            addressList.setCreateDate(LocalDate.now());
            addressList.setCreater(user.getAccount());
            addressLists.add(addressList);
        }


        customer.setMakeDate(LocalDate.now());
        customer.setMaker(maker);
        customer.setMakerName(makerName);

        customerDetailDAO.save(customer);
//            介绍交流阶段包含在线尽调第一部分数据
//            所以 保存介绍交流阶段的数据时，也保存在线交流阶段数据
//            当进入在线尽调阶段时，直接从第二页开始进行
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setSalePlanID(salePlanID);
        companyInfo.setCompanyName(plan.getCustomerName());
        companyInfo.setContact(customer1.getPrincipal());
        companyInfo.setContactWay(customer1.getPhone());
        companyInfo.setWechatNum(customer1.getPhone());
        Date now = new Date();
        companyInfo.setCreateTime(now);
        companyInfo.setCreateDate(String.valueOf(now));
        companyInfoDAO.save(companyInfo);

        List<TaskSum> sumList = taskSumDAO.findBySalePlanIDAndTask(salePlanID , "保密协议签订");

        List<ArrangeRecord> arrangeRecordList = arrangeRecordDAO.findBySalePlanIDAndStepAndType(salePlanID , "介绍交流" , "sale");
        if(!arrangeRecordList.isEmpty()){
            ArrangeRecord arrangeRecord = arrangeRecordList.get(0);
            arrangeRecord.setCompleteStatus("C");
            arrangeRecordDAO.save(arrangeRecord);
        }

        plan.setPlanStatus("second");
        plan.setUpdateDate(LocalDate.now());
        plan.setSpendTime(7);
        plan.setDeadline(sumList.get(0).getDeadline());
        plan.setStep(2);
        salesPlanDAO.save(plan);

        for(PrincipalList principalList : principalLists){
            principalListDAO.save(principalList);
        }
        for(AddressList addressList : addressLists){
            addressListDAO.save(addressList);
        }

        taskDAO.deleteBySalePlanIDAndJobName(salePlanID , "介绍交流");
        taskSumDAO.deleteBySalePlanIDAndTask(salePlanID , "介绍交流");


        return "OK";
    }

    @RequestMapping("/getIntroduction")
    public Map<String , Object> findIntroduction(String salePlanID){
        Map<String  , Object> map = new HashMap<>();
        List<CustomerDetail> list1 = customerDetailDAO.findBySalePlanID(salePlanID);
        map.put("customer" , list1.get(0));
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        List<PrincipalList> principalLists = principalListDAO.findByCustomerCode(salesPlan.getCustomerCode());
        for(int i = 0 ; i < principalLists.size() ; i++){
            PrincipalList principalList = principalLists.get(i);
            String mainPrincipal = principalList.getMainPrincipal();
            String wechat = principalList.getWechat();
            if(wechat == null){
                principalLists.get(i).setWechat("");
            }
            if(mainPrincipal.equals("Y")){
                principalLists.get(i).setMainPrincipal("是");
            }else{
                principalLists.get(i).setMainPrincipal("否");
            }
        }
        map.put("list1" , principalLists);
        List<AddressList> addressLists = addressListDAO.findByCustomerCode(salesPlan.getCustomerCode());
        for(int i = 0 ; i < addressLists.size() ; i++){
            AddressList addressList = addressLists.get(i);
            String mainAddress = addressList.getMainAddress();
            if(mainAddress.equals("Y")){
                addressLists.get(i).setMainAddress("是");
            }else{
                addressLists.get(i).setMainAddress("否");
            }
        }
        map.put("list2" , addressLists);
        return map;
    }
}
