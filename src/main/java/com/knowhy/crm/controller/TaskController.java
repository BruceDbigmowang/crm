package com.knowhy.crm.controller;

import com.knowhy.crm.dao.TaskSumDAO;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.TaskSum;
import com.knowhy.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    @Autowired
    TaskSumDAO taskSumDAO;
    @Autowired
    UserService userService;

    @RequestMapping("/loadAllTask")
    public Map<String , Object> getAllTask(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
        List<String> funcList = userService.findAllFunc(account);
        if(funcList.contains("fileConclude")){
            Map<String , Object> map = new HashMap<>();
            List<TaskSum> list1 = taskSumDAO.findByTaskLevelAndAuthorityOrderByDeadlineAsc(3 , "sb");
            map.put("listOne" , list1);
            List<TaskSum> list2 = taskSumDAO.findByTaskLevelAndAuthorityOrderByDeadlineAsc(2 , "sb");
            map.put("listTwo" , list2);
            List<TaskSum> list3 = taskSumDAO.findByTaskLevelAndAuthorityOrderByDeadlineAsc(1 , "sb");
            map.put("listThree" , list3);
            return map;
        }else{
            Map<String , Object> map = new HashMap<>();
            List<TaskSum> list1 = taskSumDAO.findByPrincipalAndTaskLevelAndAuthorityOrderByDeadlineAsc(account , 3 , "sb");
            map.put("listOne" , list1);
            List<TaskSum> list2 = taskSumDAO.findByPrincipalAndTaskLevelAndAuthorityOrderByDeadlineAsc(account , 2 , "sb");
            map.put("listTwo" , list2);
            List<TaskSum> list3 = taskSumDAO.findByPrincipalAndTaskLevelAndAuthorityOrderByDeadlineAsc(account , 1 , "sb");
            map.put("listThree" , list3);
            return map;
        }

    }
}
