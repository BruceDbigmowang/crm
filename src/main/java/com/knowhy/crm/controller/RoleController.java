package com.knowhy.crm.controller;

import com.knowhy.crm.dao.FuncDAO;
import com.knowhy.crm.dao.RoleFuncDAO;
import com.knowhy.crm.dao.RolesDAO;
import com.knowhy.crm.pojo.Func;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.RoleFunc;
import com.knowhy.crm.pojo.Roles;
import com.knowhy.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RoleController {
    @Autowired
    RolesDAO rolesDAO;
    @Autowired
    RoleFuncDAO roleFuncDAO;
    @Autowired
    FuncDAO funcDAO;
    @Autowired
    UserService userService;

    @RequestMapping("/selectAllRoles")
    public List<Roles> getAllRoles(){
        return rolesDAO.findAll();
    }

    @RequestMapping("/deleteRole")
    @Transactional
    public String deleteRole(int roleId){
        roleFuncDAO.deleteByRid(roleId);
        rolesDAO.deleteById(roleId);
        return "OK";
    }

    /**
     * 查询该角色所拥有的的功能
     * 根据角色查询出所拥有的功能
     * 再查询出所有，从所拥有的功能排除，剩下的就是未曾拥有的功能
     */
    @RequestMapping("/getFunc")
    public Map<String , Object> showFunc(int roleId){

        List<Func> hasFuns = new ArrayList<>();
        Map<String , Object> map = new HashMap<>();
        //获取所有已拥有的权限
        List<RoleFunc> roleFuncList = roleFuncDAO.findByRid(roleId);
        for(RoleFunc roleFunc : roleFuncList){
            int fId = roleFunc.getFid();
            Func func = funcDAO.getOne(fId);
            hasFuns.add(func);
        }
        map.put("has" , hasFuns);
        List<Func> funcList = funcDAO.findAll();
        for(Func func : hasFuns){
            funcList.remove(func);
        }
        map.put("hasnot" , funcList);
        return map;
    }

    @RequestMapping("/deleteFuncs")
    @Transactional
    public String dropFunc(int roleId , int[] funcs){
        for(int funcId : funcs){
            roleFuncDAO.deleteByRidAndFid(roleId , funcId);
        }
        return "OK";
    }

    @RequestMapping("/addFuncs")
    @Transactional
    public String insertFunc(int roleId , int[] funcs){
        for(int funcId : funcs){
            RoleFunc roleFunc = new RoleFunc();
            roleFunc.setRid(roleId);
            roleFunc.setFid(funcId);
            roleFuncDAO.save(roleFunc);
        }
        return "OK";
    }

    /**
     * 销售过程
     * 根据账号来判断权限
     * 判断是否具有查询所有的权限
     * 有：返回1
     * 无：返回0
     * @param session
     * @return
     */
    @RequestMapping("/getSaleRole")
    public int showSaleLook(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<Func> funcList = userService.findPermsByAccount(account);
        int result = 0;
        for(Func func : funcList){
            String funcCode = func.getCode();
            if(funcCode.equals("saleLookAll")){
                result = 1;
                break;
            }
        }
        return result;
    }

    /**
     * 判断该账号是否具有查看合同归档的权限
     */
    @RequestMapping("/getFileConcludeRole")
    public int fileConcludeRole(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<Func> funcList = userService.findPermsByAccount(account);
        int result = 0;
        for(Func func : funcList){
            String funcCode = func.getCode();
            if(funcCode.equals("fileLookAll")){
                result = 1;
                break;
            }
        }
        return result;
    }

    /**
     * 查询计划排成的权限
     * 共三种情况：
     * 1、对计划排成进行操作（销售助理） result == 0
     * 2、查询相关的（销售/技术/供应链） result == 1
     * 3、查询所有（领导）  result == 2
     */
    @RequestMapping("/getScheduleRole")
    public int scheduleRole(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int result = 0;
        List<Func> funcList = userService.findPermsByAccount(account);
        for(Func func : funcList){
            String funcCode = func.getCode();
            if(funcCode.equals("scheduleLookSome")){
                result = 1;
                break;
            }
            if(funcCode.equals("scheduleLookAll") ){
                result = 2 ;
                break;
            }
        }
        return result;
    }

    @RequestMapping("/getOperateRole")
    public int operateRole(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int result = 0;
        List<Func> funcList = userService.findPermsByAccount(account);
        for(Func func : funcList){
            String funcCode = func.getCode();
            if(funcCode.equals("operateLookSome")){
                result = 1;
                break;
            }
            if(funcCode.equals("operateLookAll") ){
                result = 2 ;
                break;
            }
        }
        return result;
    }

    @RequestMapping("/getTechnicalServiceRole")
    public int technicalServiceRole(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int result = 0;
        List<Func> funcList = userService.findPermsByAccount(account);
        for(Func func : funcList){
            String funcCode = func.getCode();
            if(funcCode.equals("technicalServiceLookSome")){
                result = 1;
                break;
            }
            if(funcCode.equals("technicalServiceLookAll") ){
                result = 2 ;
                break;
            }
        }
        return result;
    }

    @RequestMapping("/getReportRole")
    public int reportRole(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int result = 0;
        List<Func> funcList = userService.findPermsByAccount(account);
        for(Func func : funcList){
            String funcCode = func.getCode();
            if(funcCode.equals("reportLookSome")){
                result = 1;
                break;
            }
            if(funcCode.equals("reportLookAll") ){
                result = 2 ;
                break;
            }
        }
        return result;
    }

    @RequestMapping("/getSatisfationRole")
    public int satisfationRole(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        int result = 0;
        List<Func> funcList = userService.findPermsByAccount(account);
        for(Func func : funcList){
            String funcCode = func.getCode();
            if(funcCode.equals("satisfactionLookSome")){
                result = 1;
                break;
            }
            if(funcCode.equals("satisfactionLookAll") ){
                result = 2 ;
                break;
            }
        }
        return result;
    }

    @RequestMapping("/createNewRole")
    public String saveNewRole(String roleName){
        if(roleName == null || "".equals(roleName)){
            return "请填写角色名称";
        }else{
            Roles roles = new Roles();
            roles.setRoleName(roleName);
            try{
                rolesDAO.save(roles);
            }catch (Exception e){
                return e.getMessage();
            }
            return "OK";
        }
    }
}
