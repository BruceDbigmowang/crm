package com.knowhy.crm.service;

import com.knowhy.crm.dao.AddressListDAO;
import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.dao.PrincipalListDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.*;
import com.knowhy.crm.util.CustomerDataUtil;
import com.knowhy.crm.util.ExcelUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class loadCustomerService {

    @Autowired
    SalePlanService salePlanService;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    PrincipalListDAO principalListDAO;
    @Autowired
    AddressListDAO addressListDAO;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    public String checkCustomerEnter(HttpServletRequest request , HttpServletResponse response, HttpSession session) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("customerFile");
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<Object>> listob = null;
        try {
            listob = new CustomerDataUtil().getCustomerListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "请选择需要导入的文件";
        }
        for(int i = 0 ; i < listob.size() ; i++){
            int no = i + 1;
            Customer customer = new Customer();
            SalesPlan salesPlan = new SalesPlan();
            List<Object> lo = listob.get(i);
            String customerCode = String.valueOf(lo.get(0)).trim();
            if("".equals(customerCode)){
                return "客户信息——第"+no+"行客户编号未填写";
            }else{
                customer.setId(customerCode);
            }
            String customerName = String.valueOf(lo.get(1)).trim();
            if("".equals(customerName)){
                return "客户信息——第"+no+"行客户名称未填写";
            }else{
                customer.setName(customerName);
            }
            String customerType = String.valueOf(lo.get(2)).trim();
            if("".equals(customerType)){
                return "客户信息——第"+no+"行客户类型未填写";
            }else{
                customer.setCustomerType(customerType);
            }
            String province = String.valueOf(lo.get(3)).trim();
            if("".equals(province)){
                return "客户信息——第"+no+"行客户地址省未填写";
            }
            String city = String.valueOf(lo.get(4)).trim();
            if("".equals(city)){
                return "客户信息——第"+no+"行客户地址市未填写";
            }
            String district = String.valueOf(lo.get(5)).trim();
            if("".equals(district)){
                return "客户信息——第"+no+"行客户地址区未填写";
            }
            String addre = String.valueOf(lo.get(6)).trim();
            if("".equals(addre)){
                return "客户信息——第"+no+"行客户地址详细地址未填写";
            }
            String address = province+city+district+addre;
            customer.setCompanyAddress(address);
            customer.setCity(city);
            String area = String.valueOf(lo.get(7)).trim();
            if("".equals(area)){
                return "客户信息——第"+no+"行客户所属区域未填写";
            }else{
                customer.setArea(area);
            }
            String decisionMaker = String.valueOf(lo.get(8)).trim();
            if(!"".equals(decisionMaker)){
                customer.setDecisionMaker(decisionMaker);
            }
            String decisionPhone = String.valueOf(lo.get(9)).trim();
            if(!"".equals(decisionPhone)){
                customer.setDecisionPhone(decisionPhone);
            }
            String principal = String.valueOf(lo.get(10)).trim();
            if(!"".equals(principal)){
                customer.setPrincipal(principal);
            }
            String phone = String.valueOf(lo.get(11)).trim();
            if(!"".equals(phone)){
                customer.setPhone(phone);
            }
            IUser user = (IUser)session.getAttribute("user");
            customer.setCreater(user.getAccount());
            customer.setCreaterName(user.getName());
            customer.setCreateDate(LocalDate.now());
            customer.setFollowStatus("C");
            customer.setSource("由旧系统导入");

            String salePlanID = salePlanService.generateNumber();
            salesPlan.setId(salePlanID);
            salesPlan.setCustomerCode(customerCode);
            salesPlan.setCustomerName(customerName);
            salesPlan.setDescribe("由旧系统导入");
            salesPlan.setMakeDate(LocalDate.now());
            salesPlan.setCreater(user.getAccount());
            salesPlan.setCreaterName(user.getName());
            salesPlan.setPlanStatus("eleventh");
            customerDAO.save(customer);
            salePlanService.createSalePlan(salesPlan);
        }

        List<List<Object>> contactList = null;
        try{
            contactList = new CustomerDataUtil().getContactListByExcel(in, file.getOriginalFilename());
        }catch (Exception e){
            return "未查询到联系人数据信息";
        }
        for(int i = 0 ; i < contactList.size() ; i++){
            List<Object> lo = contactList.get(i);
            int no = i + 1;
            PrincipalList principalList = new PrincipalList();
            String customerCode = String.valueOf(lo.get(0)).trim();
            if("".equals(customerCode)){
                return "联系人信息——第"+no+"行客户编号未填写";
            }else{
                SalesPlan salesPlan = salePlanService.findByCustomerCode(customerCode);
                if(salesPlan == null){
                    return "联系人信息——第"+no+"行未查询到客户信息";
                }else{
                    principalList.setCustomerCode(salesPlan.getCustomerCode());
                    principalList.setCustomerName(salesPlan.getCustomerName());
                }
            }
            String phone = String.valueOf(lo.get(2)).trim();
            if("".equals(phone)){
                return "联系人信息——第"+no+"行联系人电话未填写";
            }else{
                principalList.setPhone(phone);
            }
            String email = String.valueOf(lo.get(3)).trim();
            if("".equals(email)){
                return "联系人信息——第"+no+"行邮箱未填写";
            }else{
                principalList.setEmail(email);
            }
            String wechat = String.valueOf(lo.get(4)).trim();
            if(!"".equals(wechat)){
                principalList.setWechat(wechat);
            }
            String sex = String.valueOf(lo.get(5)).trim();
            if(!"".equals(sex)){
                principalList.setSex(sex);
            }
            String position = String.valueOf(lo.get(6)).trim();
            if(!"".equals(position)){
                principalList.setPosition(position);
            }
            String hobby = String.valueOf(lo.get(7)).trim();
            if(!"".equals(hobby)){
                principalList.setHobby(hobby);
            }
            String relationship = String.valueOf(lo.get(8)).trim();
            if(!"".equals(relationship)){
                principalList.setRelationship(relationship);
            }
            String birthDay = String.valueOf(lo.get(9)).trim();
            if(!"".equals(birthDay)){
                try{
                    LocalDate birth = LocalDate.parse(birthDay , fmt);
                    principalList.setBirthday(birth);
                }catch (Exception e){
                    return "联系人信息——第"+no+"行生日填写错误";
                }
            }
            String mainPrincipal = String.valueOf(lo.get(10)).trim();
            if(mainPrincipal.equals("是") || "Y".equals(mainPrincipal)){
                principalList.setMainPrincipal("Y");
            }else{
                principalList.setMainPrincipal("N");
            }
            principalListDAO.save(principalList);
        }

        List<List<Object>> addresses = null;
        try{
            addresses = new CustomerDataUtil().getAddressListByExcel(in , file.getOriginalFilename());
        }catch (Exception e){
            return "未找到地址信息";
        }
        for(int i = 0 ; i < addresses.size() ; i++){
            List<Object> lo = addresses.get(i);
            int no = i + 1;
            AddressList addressList = new AddressList();
            String customerCode = String.valueOf(lo.get(0));
            if("".equals(customerCode)){
                return "地址信息——第"+no+"行客户编号未填写";
            }else{
                SalesPlan salesPlan = salePlanService.findByCustomerCode(customerCode);
                if(salesPlan == null){
                    return "地址信息——第"+no+"行未查询到客户信息";
                }else{
                    addressList.setCustomerCode(salesPlan.getCustomerCode());
                    addressList.setCustomerName(salesPlan.getCustomerName());
                }
            }
        }
        return "OK";
    }
}
