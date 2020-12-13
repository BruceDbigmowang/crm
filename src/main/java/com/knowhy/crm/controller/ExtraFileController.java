package com.knowhy.crm.controller;

import com.knowhy.crm.dao.ExtraContractDAO;
import com.knowhy.crm.dao.RecordDAO;
import com.knowhy.crm.pojo.ExtraContract;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExtraFileController {

    @Autowired
    RecordDAO recordDAO;
    @Autowired
    ExtraContractDAO extraContractDAO;

    @RequestMapping("/getHadFile")
    public Map<String , Object> selectHadFile(HttpSession session){
        IUser user = (IUser)session.getAttribute("user");
        String account = user.getAccount();
//        List<Record> recordList = recordDAO.findByAccount(account);
        Map<String , Object> map = new HashMap<>();
        List<Record> recordList = recordDAO.findAll();
        map.put("records", recordList);
        List<String> contractNameList = new ArrayList<>();
        List<String> fileNameList = new ArrayList<>();
        for(int i = 0 ; i < recordList.size() ; i++){
            String salePlanID = recordList.get(i).getSalePlanID();
            List<ExtraContract> extraContractList =extraContractDAO.findBySalePlanID(salePlanID);
            String contractName = "";
            String fileName = "";
            for(int j = 0 ; j < extraContractList.size() ; j++){
                if(j == extraContractList.size() - 1){
                    contractName = contractName + extraContractList.get(j).getContractName();
                    fileName = fileName + extraContractList.get(j).getFileName();
                }else{
                    contractName = contractName + extraContractList.get(j).getContractName()+",";
                    fileName = fileName + extraContractList.get(j).getFileName()+",";
                }
            }
            contractNameList.add(contractName);
            fileNameList.add(fileName);
        }
        map.put("contractNames" , contractNameList);
        map.put("fileNames" , fileNameList);
        return map;
    }
}
