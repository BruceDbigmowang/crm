package com.knowhy.crm.service;

import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.dao.MarketingDAO;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.Marketing;
import com.knowhy.crm.pojo.SalesPlan;
import com.knowhy.crm.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AddService {

    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SalePlanService salePlanService;
    @Autowired
    MarketingDAO marketingDAO;

    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

    public String checkCustomerEnter(HttpServletRequest request, HttpServletResponse response) {
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
            listob = new ExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "请选择需要导入的文件";
        }
        List<String> customerList = new ArrayList<>();
        for (int i = 0; i < listob.size(); i++) {
            int target = i + 1;
            List<Object> lo = listob.get(i);
            String customerName = String.valueOf(lo.get(0)).trim();
            if("".equals(customerName)){
                return "第"+target+"行客户名称未填写";
            }else if(customerName.matches("[0-9]{1,}")){
                return "第"+target+"行客户名称填写错误（不能包含数字）";
            }else{

                if(customerList.contains(customerName)){
                    return "第"+target+"行客户信息重复";
                }else{
                    customerList.add(customerName);
                }
            }
            String customerType = String.valueOf(lo.get(1)).trim();
            if("".equals(customerType)){
                return "第"+target+"行客户类型未填写";
            }
            String companyAddress = String.valueOf(lo.get(2)).trim();
            if("".equals(companyAddress)){
                return "第"+target+"行公司地址未填写";
            }else{
                if(!companyAddress.contains("市")){
                    return "第"+target+"行公司地址填写错误，格式为“xx市xxxx”";
                }
            }

            String area = String.valueOf(lo.get(3)).trim();
            if("".equals(area)){
                return "第"+target+"行客户所在地区未填写";
            }
            String decision = String.valueOf(lo.get(4)).trim();
            if("".equals(decision)){
                return "第"+target+"行决策者未填写";
            }
            String decisionPhone = String.valueOf(lo.get(5)).trim();
            if("".equals(decisionPhone)){
                return "第"+target+"行决策者联系方式未填写";
            }else if(!decisionPhone.matches("[0-9]{1,}")){
                return "第"+target+"行决策者联系方式填写错误（只能包含数字）";
            }
            String principal = String.valueOf(lo.get(6)).trim();
            if("".equals(principal)){
                return "第"+target+"行项目负责人未填写";
            }
            String phone = String.valueOf(lo.get(7)).trim();
            if("".equals(phone)){
                return "第"+target+"行项目负责人联系方式未填写";
            }else if(!phone.matches("[0-9]{1,}")){
                return "第"+target+"行项目负责人联系方式填写错误（只能包含数字）";
            }
        }
        return "OK";
    }

    public String uploadCustomer(HttpServletRequest request, HttpServletResponse response , HttpSession session) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("customerFile");
        int mId = Integer.parseInt(request.getParameter("mId"));
        if (file.isEmpty()) {
            try {
                throw new Exception("文件不存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        InputStream in = null;
        try {
            in = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtils().getBankListByExcel(in, file.getOriginalFilename());
        } catch (Exception e) {
            return "请选择需要导入的文件";
        }
        String path = request.getParameter("path");
        IUser user = (IUser)session.getAttribute("user");
        int targetNum = 0;
        for (int i = 0; i < listob.size(); i++) {
            List<Object> lo = listob.get(i);
            String customerName = String.valueOf(lo.get(0)).trim();
            String customerType = String.valueOf(lo.get(1)).trim();
            String companyAddress = String.valueOf(lo.get(2)).trim();
            String area = String.valueOf(lo.get(3)).trim();
            String decision = String.valueOf(lo.get(4)).trim();
            String decisionPhone = String.valueOf(lo.get(5)).trim();
            String principal = String.valueOf(lo.get(6)).trim();
            String phone = String.valueOf(lo.get(7)).trim();

            Customer customer = new Customer();
            customer.setCreater(user.getAccount());
            customer.setCreaterName(user.getName());
            customer.setCreateDate(LocalDate.now());
            customer.setCompanyAddress(companyAddress);
            String city = companyAddress.split("市")[0];
            customer.setCity(city);
            customer.setFollowStatus("O");

            //系统自动生成客户编号
            String customerID = "knowhy"+sdf2.format(new Date());
            List<Customer> customerList = customerDAO.findByCreateDate(LocalDate.now());
            int customerLength = customerList.size() + 1;
            String customers = String.valueOf(customerLength);
            for(int j = customers.length() ; j < 4 ; j++){
                customers = "0"+customers;
            }
            customerID = customerID + customers;

            customer.setId(customerID);
            customer.setName(customerName);
            customer.setCustomerType(customerType);
            customer.setArea(area);
            customer.setDecisionMaker(decision);
            customer.setDecisionPhone(decisionPhone);
            customer.setPrincipal(principal);
            customer.setPhone(phone);
            customer.setSource(user.getName()+"通过"+path+"活动批量导入");
            customerDAO.save(customer);

            //创建销售计划

            SalesPlan salesPlan= new SalesPlan();
            String salePlanId = salePlanService.generateNumber();
            salesPlan.setId(salePlanId);
            salesPlan.setCustomerCode(customerID);
            salesPlan.setCustomerName(customerName);
            salesPlan.setCreater(user.getAccount());
            salesPlan.setCreaterName(user.getName());
            salesPlan.setDescribe(user.getName()+"通过"+path+"活动批量导入");
            salesPlan.setPlanStatus("first");
            salesPlan.setMakeDate(LocalDate.now());
            salesPlan.setSpendTime(7);
            salesPlan.setStep(1);
            salePlanService.createSalePlan(salesPlan);

            targetNum = targetNum + 1;
        }
        String filename = file.getOriginalFilename();
        Marketing marketing = marketingDAO.getOne(mId);
        marketing.setTotalCustomer(targetNum);
        marketing.setCustomerFile(filename);
        marketingDAO.save(marketing);
        return "成功导入"+targetNum+"条数据";
    }


}
