package com.knowhy.crm.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.knowhy.crm.dao.CustomerDAO;
import com.knowhy.crm.dao.PrincipalListDAO;
import com.knowhy.crm.dao.SatisfactionDAO;
import com.knowhy.crm.entity.CrmCustomer;
import com.knowhy.crm.entity.CrmPrincipallist;
import com.knowhy.crm.mapper.CrmPrincipallistMapper;
import com.knowhy.crm.pojo.Customer;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.PrincipalList;
import com.knowhy.crm.pojo.Satisfaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class PrincipalListController {

    @Autowired
    PrincipalListDAO principalListDAO;
    @Resource
    CrmPrincipallistMapper crmPrincipallistMapper;
    @Autowired
    CustomerDAO customerDAO;
    @Autowired
    SatisfactionDAO satisfactionDAO;

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/showAllPrincipalByPage")
    public Map<String , Object> getPrincipalByPage(int start){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<CrmPrincipallist> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("createDate");
        Page<CrmPrincipallist> page = new Page<>(start,10);  // 查询第1页，每页返回5条
        IPage<CrmPrincipallist> iPage = crmPrincipallistMapper.selectPage(page,queryWrapper);
        int total = (int)iPage.getTotal();
        int size;
        if(total%10 == 0){
            size = total/10;
        }else{
            size = total/10 + 1;
        }

        List<CrmPrincipallist> principallists = iPage.getRecords();

        map.put("allPrincipal", principallists);
        map.put("pageSize" , size);
        return map;
    }

    @RequestMapping("/getCustomerPrincipal")
    public Map<String , Object> getPrincipalByCustomer(String customerCode){
        Map<String , Object> map = new HashMap<>();
        Customer customer = customerDAO.getOne(customerCode);
        map.put("customer" , customer);
        List<PrincipalList> principalLists = principalListDAO.findByCustomerCode(customerCode);
        for(PrincipalList principalList : principalLists){
            String mainPrincipal = principalList.getMainPrincipal();
            if(mainPrincipal.equals("Y")){
                principalList.setMainPrincipal("是");
            }else{
                principalList.setMainPrincipal("否");
            }
        }
        map.put("allPrincipal" , principalLists);
        return map;
    }

    @RequestMapping("/findPrincipalByName")
    public List<PrincipalList> getPrincipalByName(String name){
        String principalName = "%"+name+"%";
        String customerName = "%"+name+"%";
        List<PrincipalList> principalLists = principalListDAO.findByNameLike(principalName);
        List<PrincipalList> principalLists1 = principalListDAO.findByCustomerNameLike(customerName);
        for(PrincipalList principalList : principalLists1){
            if(!principalLists.contains(principalList)){
                principalLists.add(principalList);
            }
        }
        return principalLists;
    }

    /**
     * 对联系人进行的操作：新增、删除与更新
     *
     * 1、新建
     * 保存相关信息
     * 若不是主要联系人直接保存数据
     * 若是主要联系人，将其他联系人“是否是主要联系人”字段改为否，并保存相关数据
     *
     * 2、更新
     *（1）对除是否是主要负责人的其它字段进行更新，直接保存
     * (2)对是否是主要负责人进行更新，选择“是”，将其他人的是否是负责人改为“否”,该联系人改为“是”
     *
     * 3、删除
     * 删除该联系人
     * 若该联系人是主要联系人，删除之后，随机选择一个作为新的主要联系人
     */

    @RequestMapping("/getPrincipalDetail")
    public PrincipalList showPrincipalDetail(int pId){
        return principalListDAO.getOne(pId);
    }

    @RequestMapping("/deletePrincipal")
    @Transactional
    public String dropFromPrincipal(int pId){
        PrincipalList principalList = principalListDAO.getOne(pId);
        String customerCode = principalList.getCustomerCode();
        String mainPrincipal = principalList.getMainPrincipal();
        if(mainPrincipal.equals("Y")){
            principalListDAO.deleteById(pId);
            List<PrincipalList> principalLists = principalListDAO.findByCustomerCode(customerCode);
            for(PrincipalList principalList1 : principalLists){
                if(principalList1.getId() == pId){
                    principalLists.remove(principalList1);
                }
            }
            if(principalLists.size() > 0){
                PrincipalList principalList2 = principalLists.get(0);
                principalList2.setMainPrincipal("Y");
                principalListDAO.save(principalList2);

                /**
                 * 更新满意度调查表中客户的主要联系人
                 */
                List<Satisfaction> satisfactions = satisfactionDAO.findByCustomerCode(customerCode);
                if(satisfactions.size() > 0){
                    Satisfaction satisfaction = satisfactions.get(0);
                    satisfaction.setPrincipal(principalList2.getName());
                    satisfaction.setPhone(principalList2.getPhone());
                    satisfaction.setEmail(principalList2.getEmail());
                    satisfaction.setWechat(principalList2.getWechat());
                    satisfactionDAO.save(satisfaction);
                }
            }

        }else{
            principalListDAO.deleteById(pId);
        }
        return "OK";
    }

    @RequestMapping("/createNewPrincipal")
    @Transactional
    public String createPrincipal(String customerCode , String principalName , String phone , String email , String wechat , String sex , String position , String hobby , String relationship , String birthday , String mainPrincipal , HttpSession session){
        Customer customer = customerDAO.getOne(customerCode);
        IUser user = (IUser)session.getAttribute("user");
        PrincipalList principalList = new PrincipalList();
        principalList.setCreater(user.getAccount());
        principalList.setCreateDate(LocalDate.now());
        principalList.setCustomerCode(customerCode);
        principalList.setCustomerName(customer.getName());
        if(principalName == null || "".equals(principalName)){
            return "请输入联系人姓名";
        }else{
            principalList.setName(principalName);
        }
        if(phone == null || "".equals(phone)){
            return "请输入联系人电话";
        }else{
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(phone);
            if( !isNum.matches() || phone.length() > 11){
                return "联系人电话填写错误";
            }else{
                principalList.setPhone(phone);
            }

        }
        if(email == null || "".equals(email)){
            return "请输入邮箱";
        }else{
//            String regex = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
            String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})$";
            System.out.println(email.matches(regex));
            if(email.matches(regex)){
                principalList.setEmail(email);
            }else{
                return "邮箱填写错误";
            }
        }
        if(wechat == null || "".equals(wechat)){
            return "请填写微信号";
        }else{
            principalList.setWechat(wechat);
        }
        if(sex == null || "0".equals(sex)){
            return "请选择性别";
        }else{
            principalList.setSex(sex);
        }
        if(position == null || "".equals(position)){
            return "请填写职位";
        }else{
            principalList.setPosition(position);
        }
        if(hobby != null && !"".equals(hobby)){
            principalList.setHobby(hobby);
        }
        if(relationship != null && !"".equals(relationship)){
            principalList.setRelationship(relationship);
        }
        if(birthday == null || "".equals(birthday)){
            return "请选择生日";
        }else{
            try{
                LocalDate birth = LocalDate.parse(birthday , fmt);
                principalList.setBirthday(birth);
            }catch (Exception e){
                return "生日格式错误";
            }
        }
        if(mainPrincipal == null || "".equals(mainPrincipal)){
            return "请选择是否是主要联系人";
        }else{
            principalList.setMainPrincipal(mainPrincipal);
        }
        if(mainPrincipal.equals("Y")){
            List<PrincipalList> principalLists = principalListDAO.findByCustomerCode(customerCode);
            for(PrincipalList principalList1 : principalLists){
                principalList1.setMainPrincipal("N");
                principalListDAO.save(principalList1);
            }

            /**
             * 更新主要联系人的同时，一并将客户满意度调查表中的数据进行调整
             */
            List<Satisfaction> satisfactions = satisfactionDAO.findByCustomerCode(customerCode);
            if(satisfactions.size() > 0){
                Satisfaction satisfaction = satisfactions.get(0);
                satisfaction.setPrincipal(principalList.getName());
                satisfaction.setPhone(principalList.getPhone());
                satisfaction.setEmail(principalList.getEmail());
                satisfaction.setWechat(principalList.getWechat());
                satisfactionDAO.save(satisfaction);
            }
        }
        principalListDAO.save(principalList);

        return "OK";
    }

    @RequestMapping("/updatePrincipalInfo")
    public String updatePrincipal(int pId , String principalName , String phone , String email , String wechat , String sex , String position , String hobby , String relationship , String birthday , String mainPrincipal , HttpSession session) {
        PrincipalList principalList = principalListDAO.getOne(pId);
        String customerCode = principalList.getCustomerCode();
        System.out.println("客户编码"+customerCode);
        if (principalName == null || "".equals(principalName)) {
            return "请输入联系人姓名";
        } else {
            principalList.setName(principalName);
        }
        if (phone == null || "".equals(phone)) {
            return "请输入联系人电话";
        } else {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(phone);
            if (!isNum.matches() || phone.length() > 11) {
                return "联系人电话填写错误";
            } else {
                principalList.setPhone(phone);
            }

        }
        if (email == null || "".equals(email)) {
            return "请输入邮箱";
        } else {
            String regex = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
            if (email.matches(regex)) {
                principalList.setEmail(email);
            } else {
                return "邮箱填写错误";
            }
        }
        if (wechat == null || "".equals(wechat)) {
            return "请填写微信号";
        } else {
            principalList.setWechat(wechat);
        }
        if (sex == null || "0".equals(sex)) {
            return "请选择性别";
        } else {
            principalList.setSex(sex);
        }
        if (position == null || "".equals(position)) {
            return "请填写职位";
        } else {
            principalList.setPosition(position);
        }
        if (hobby != null && !"".equals(hobby)) {
            principalList.setHobby(hobby);
        }
        if (relationship != null && !"".equals(relationship)) {
            principalList.setRelationship(relationship);
        }
        if (birthday == null || "".equals(birthday)) {
            return "请选择生日";
        } else {
            try {
                LocalDate birth = LocalDate.parse(birthday, fmt);
                principalList.setBirthday(birth);
            } catch (Exception e) {
                return "生日格式错误";
            }
        }
        if (mainPrincipal == null || "".equals(mainPrincipal)) {
            return "请选择是否是主要联系人";
        } else {
            principalList.setMainPrincipal(mainPrincipal);
        }
        if (mainPrincipal.equals("Y")) {
            List<PrincipalList> principalLists = principalListDAO.findByCustomerCode(customerCode);
            System.out.println(principalLists.size());
            for(int i = 0 ; i < principalLists.size() ; i++){
                PrincipalList principal = principalLists.get(i);
                if(principal.getId() == pId){
                    principalLists.remove(principal);
                }
            }
            for (PrincipalList principalList1 : principalLists) {
                principalList1.setMainPrincipal("N");
                principalListDAO.save(principalList1);
            }

            /**
             * 更新主要联系人时 一并更新满意度调查表中的联系人数据
             */
            List<Satisfaction> satisfactions = satisfactionDAO.findByCustomerCode(customerCode);
            if(satisfactions.size() > 0){
                Satisfaction satisfaction = satisfactions.get(0);
                satisfaction.setPrincipal(principalList.getName());
                satisfaction.setPhone(principalList.getPhone());
                satisfaction.setEmail(principalList.getEmail());
                satisfaction.setWechat(principalList.getWechat());
                satisfactionDAO.save(satisfaction);
            }
        }
        principalListDAO.save(principalList);

        return "OK";
    }
}
