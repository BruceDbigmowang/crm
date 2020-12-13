package com.knowhy.crm.controller;

import com.knowhy.crm.dao.DeptDAO;
import com.knowhy.crm.dao.DeptGroupDAO;
import com.knowhy.crm.dao.GroupDAO;
import com.knowhy.crm.pojo.Dept;
import com.knowhy.crm.pojo.DeptGroup;
import com.knowhy.crm.pojo.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptGroupController {
    @Autowired
    DeptDAO deptDAO;
    @Autowired
    GroupDAO groupDAO;
    @Autowired
    DeptGroupDAO deptGroupDAO;

    @RequestMapping("/getAllDept")
    public List<Dept> findAllDept(){
        return deptDAO.findAll();
    }

    @RequestMapping("/getGroupByDept")
    public List<Group> findGroupByDept(String deptName){
        List<Dept> deptList = deptDAO.findByDeptNameLike(deptName);
        String deptCode = deptList.get(0).getDeptCode();
        return groupDAO.findByDeptCode(deptCode);
    }

    @RequestMapping("/getEmployeeByDeptGroup")
    public List<DeptGroup> findByDeptGroup(String deptName , String groupCode){
        List<Dept> deptList = deptDAO.findByDeptNameLike(deptName);
        String deptCode = deptList.get(0).getDeptCode();
        return deptGroupDAO.findByDeptCodeAndGroupCode(deptCode , groupCode);
    }

    @RequestMapping("/getEmployeeByDeptGroupRole")
    public List<DeptGroup> findByDeptGroupRole(String deptName , String groupCode , String role){
        List<Dept> deptList = deptDAO.findByDeptNameLike(deptName);
        String deptCode = deptList.get(0).getDeptCode();
        System.out.println(deptName+"-"+deptCode + "-" + groupCode + "-" + role);
        return deptGroupDAO.findByDeptCodeAndGroupCodeAndRole(deptCode , groupCode , role);
    }

}
