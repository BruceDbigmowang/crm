package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.AddressListDAO;
import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.entity.CrmAddresslist;
import com.knowhy.crm.mapper.CrmAddresslistMapper;
import com.knowhy.crm.pojo.AddressList;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.IUser;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class AddressManageController {
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    AddressListDAO addressListDAO;
    @Resource
    CrmAddresslistMapper crmAddresslistMapper;

    @RequestMapping("/findAddressByPage")
    public Map<String , Object>getAddressByPage(){
        Map<String,Object> map = new HashMap<>();

        List<AddressList> addresslists = addressListDAO.findAll();

        map.put("allAddress", addresslists);
        return map;
    }

    @RequestMapping("/loadAddressByCustomer")
    public Map<String , Object> findAddressByPage(String customerCode){
        Customer customer = customerDAO.getOne(customerCode);
        Map<String , Object> map = new HashMap<>();
        map.put("customer" , customer);
        List<AddressList> addressLists = addressListDAO.findByCustomerCode(customerCode);
        for(AddressList addressList : addressLists){
            String mainAddress = addressList.getMainAddress();
            if(mainAddress.equals("Y")){
                addressList.setMainAddress("是");
            }else{
                addressList.setMainAddress("否");
            }
        }
        map.put("allAddress" , addressLists);
        return map;
    }

    @RequestMapping("/findByCustomerNameOr")
    public List<AddressList> findByAddressOr(String name){
        String customerName = "%"+name+"%";
        String address = "%"+name+"%";
        List<AddressList> addressLists = addressListDAO.findByCustomerNameLike(customerName);
        List<AddressList> addressLists1 = addressListDAO.findByAddressLike(address);
        for(AddressList addressList : addressLists1){
            if(!addressLists.contains(addressList)){
                addressLists.add(addressList);
            }
        }
        return addressLists;
    }

    @RequestMapping("/createNewAddress")
    @Transactional
    public String saveAddress(String customerCode , String describe , String address , String area , String postcode , String contract , String contractWay , String mainAddress , HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        Customer customer = customerDAO.getOne(customerCode);
        AddressList addressList = new AddressList();
        addressList.setCustomerCode(customerCode);
        addressList.setCustomerName(customer.getName());
        addressList.setCreater(user.getAccount());
        addressList.setCreateDate(LocalDate.now());
        if(describe == null || "".equals(describe)){
            return "请填写地址描述";
        }else{
            addressList.setDescribe(describe);
        }
        if(address == null || "".equals(address)){
            return "请填写详细地址";
        }else{
            addressList.setAddress(address);
        }
        if(area == null || "0".equals(area)){
            return "请选择所属地区";
        }else{
            addressList.setArea(area);
        }
        if(postcode != null && !"".equals(postcode)){
            addressList.setPostcode(postcode);
        }
        if(contract == null || "".equals(contract)){
            return "请填写联系人";
        }else{
            addressList.setContacts(contract);
        }
        if(contractWay == null || "".equals(contractWay)){
            return "请填写联系方式";
        }else{
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(contractWay);
            if( !isNum.matches() || contractWay.length() > 11){
                return "联系方式填写错误";
            }else{
                addressList.setPhone(contractWay);
            }
        }
        if(mainAddress == null){
            return "请选择是否是主要地址";
        }else{
            addressList.setMainAddress(mainAddress);
        }
        if("Y".equals(mainAddress)){
            List<AddressList> addressLists = addressListDAO.findByCustomerCode(customerCode);
            for(AddressList addressList1 : addressLists){
                addressList1.setMainAddress("N");
                addressListDAO.save(addressList1);
            }
        }
        addressListDAO.save(addressList);
        return "OK";
    }

    @RequestMapping("/loadAddressDetail")
    public AddressList findOneAddress(int aId){
        return addressListDAO.getOne(aId);
    }

    /**
     * 删除某个地址
     * 首先判断该地址是否是主要地址
     * 若是  随机取一个地址为主要地址，再删除目标地址
     * 若不是  直接删除目标地址
     * @param aId
     * @return
     */
    @RequestMapping("/deleteAddress")
    @Transactional
    public String deleteOneAddress(int aId){
        AddressList addressList = addressListDAO.getOne(aId);
        if(addressList.getMainAddress().equals("Y")){
            String customerCode = addressList.getCustomerCode();
            List<AddressList> addressLists = addressListDAO.findByCustomerCode(customerCode);
            for(int i = 0 ; i < addressLists.size() ; i++){
                AddressList addressList1 = addressLists.get(i);
                if(addressList1.getId() == aId){
                    addressLists.remove(addressList1);
                }
            }
            if(addressLists.size() > 0){
                AddressList target = addressLists.get(0);
                target.setMainAddress("Y");
                addressListDAO.save(target);
            }
            addressListDAO.deleteById(aId);
        }else{
            addressListDAO.deleteById(aId);
        }
        return "OK";
    }

    @RequestMapping("/updateOneAddress")
    public String changeAddressInfo(int aId , String address , String describe , String area , String postcode , String contract , String contractWay , String mainAddress ){
        AddressList addressList = addressListDAO.getOne(aId);
        String customerCode = addressList.getCustomerCode();
        if(address == null || "".equals(address)){
            return "请填写详细地址";
        }else{
            addressList.setAddress(address);
        }
        if(describe == null || "".equals(describe)){
            return "请填写地址描述";
        }else{
            addressList.setDescribe(describe);
        }
        if(area == null || "0".equals(area)){
            return "请选择所在地区";
        }else{
            addressList.setArea(area);
        }
        if(postcode != null && !"".equals(postcode)){
            addressList.setPostcode(postcode);
        }
        if(contract == null || "".equals(contract)){
            return "请填写联系人";
        }else{
            addressList.setContacts(contract);
        }
        if(contractWay == null || "".equals(contractWay)){
            return "请填写联系方式";
        }else{
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(contractWay);
            if( !isNum.matches() || contractWay.length() > 11){
                return "联系方式填写错误";
            }else{
                addressList.setPhone(contractWay);
            }
        }
        if(mainAddress == null){
            return "请选择是否是主要地址";
        }else{
            addressList.setMainAddress(mainAddress);
        }
        if("Y".equals(mainAddress)){
            List<AddressList> addressLists = addressListDAO.findByCustomerCode(customerCode);
            for(int i = 0 ; i < addressLists.size(); i++){
                AddressList addressList1 = addressLists.get(i);
               int id = addressList1.getId();
               if(id == aId){
                   addressLists.remove(addressList1);
               }
            }
            for(int i = 0 ; i < addressLists.size(); i++){
                AddressList addressList1 = addressLists.get(i);
                addressList1.setMainAddress("N");
                addressListDAO.save(addressList1);
            }
        }
        addressListDAO.save(addressList);
        return "OK";
    }
}
