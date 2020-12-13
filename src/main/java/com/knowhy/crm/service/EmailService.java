package com.knowhy.crm.service;

import com.knowhy.crm.dao.ContractRecordDAO;
import com.knowhy.crm.dao.DeptGroupDAO;
import com.knowhy.crm.dao.IUserDAO;
import com.knowhy.crm.dao.SalesPlanDAO;
import com.knowhy.crm.pojo.ContractRecord;
import com.knowhy.crm.pojo.DeptGroup;
import com.knowhy.crm.pojo.IUser;
import com.knowhy.crm.pojo.SalesPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    IUserDAO iUserDAO;
    @Autowired
    SalesPlanDAO salesPlanDAO;
    @Autowired
    DeptGroupDAO deptGroupDAO;
    @Autowired
    ContractRecordDAO contractRecordDAO;

    public List<String> getReceiveFirst(String salePlanID){
        List<String> allReceive = new ArrayList<>();
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        String principalAccount = salesPlan.getPrincipal();
        IUser user = iUserDAO.getOne(principalAccount);
        String principalEmail = user.getEmail();
        if(principalEmail != null && !"".equals(principalEmail)){
            allReceive.add(principalEmail);
        }
        List<ContractRecord> contractRecordList = contractRecordDAO.findBySalePlanID(salePlanID);
        if(contractRecordList != null && contractRecordList.size() != 0){
            ContractRecord contractRecord = contractRecordList.get(0);
            String steward = contractRecord.getOperator();
            IUser user1 = iUserDAO.getOne(steward);
            String stewardEmail = user1.getEmail();
            if(stewardEmail != null && !"".equals(stewardEmail)){
                allReceive.add(stewardEmail);
            }

            String technicist = contractRecord.getTechnicist();
            IUser user2 = iUserDAO.getOne(technicist);
            String technicistEmail = user2.getEmail();
            if(technicistEmail != null && !"".equals(technicistEmail)){
                allReceive.add(technicistEmail);
            }

            String operator = contractRecord.getSteward();
            IUser user3 = iUserDAO.getOne(operator);
            String operatorEmail = user3.getEmail();
            if(operatorEmail != null && !"".equals(operatorEmail)){
                allReceive.add(operatorEmail);
            }

            String assistant = contractRecord.getServer();
            IUser user4 = iUserDAO.getOne(assistant);
            String assistantEmail = user4.getEmail();
            if(assistantEmail != null && !"".equals(assistantEmail)){
                allReceive.add(user4.getEmail());
            }
        }
        return allReceive;
    }

    public List<String> getReceiveSecond(String salePlanID){
        List<String> allReceive = new ArrayList<>();
        SalesPlan salesPlan = salesPlanDAO.getOne(salePlanID);
        String principalAccount = salesPlan.getPrincipal();
        IUser user = iUserDAO.getOne(principalAccount);
        String principalEmail = user.getEmail();
        if(principalEmail != null && !"".equals(principalEmail)){
            allReceive.add(principalEmail);
        }
        /**
         * 查找销售组总监
         */
        DeptGroup deptGroup = deptGroupDAO.getOne(principalAccount);
        String dept = deptGroup.getDeptCode();
        String group = deptGroup.getGroupCode();
        List<DeptGroup> deptGroupList = deptGroupDAO.findByDeptCodeAndGroupCodeAndRole(dept , group , "manager");
        for(int i = 0 ; i < deptGroupList.size() ; i++){
            String managerAccount = deptGroupList.get(i).getAccount();
            IUser user5 = iUserDAO.getOne(managerAccount);
            String managerEmail = user5.getEmail();
            if(managerEmail != null && !"".equals(managerEmail)){
                allReceive.add(managerEmail);
            }
        }

        List<ContractRecord> contractRecordList = contractRecordDAO.findBySalePlanID(salePlanID);
        if(contractRecordList != null && contractRecordList.size() != 0){
            ContractRecord contractRecord = contractRecordList.get(0);
            String steward = contractRecord.getOperator();
            IUser user1 = iUserDAO.getOne(steward);
            String stewardEmail = user1.getEmail();
            if(stewardEmail != null && !"".equals(stewardEmail)){
                allReceive.add(stewardEmail);
            }
            DeptGroup deptGroup1 = deptGroupDAO.getOne(steward);
            String dept1 = deptGroup1.getDeptCode();
            String group1 = deptGroup1.getGroupCode();
            List<DeptGroup> deptGroupList1 = deptGroupDAO.findByDeptCodeAndGroupCodeAndRole(dept1 , group1 , "manager");
            for(int i = 0 ; i < deptGroupList1.size() ; i++){
                String managerAccount = deptGroupList1.get(i).getAccount();
                IUser user5 = iUserDAO.getOne(managerAccount);
                String managerEmail = user5.getEmail();
                if(managerEmail != null && !"".equals(managerEmail)){
                    allReceive.add(managerEmail);
                }
            }

            String technicist = contractRecord.getTechnicist();
            IUser user2 = iUserDAO.getOne(technicist);
            String technicistEmail = user2.getEmail();
            if(technicistEmail != null && !"".equals(technicistEmail)){
                allReceive.add(technicistEmail);
            }
            DeptGroup deptGroup2 = deptGroupDAO.getOne(technicist);
            String dept2 = deptGroup2.getDeptCode();
            String group2 = deptGroup2.getGroupCode();
            List<DeptGroup> deptGroupList2 = deptGroupDAO.findByDeptCodeAndGroupCodeAndRole(dept2 , group2 , "manager");
            for(int i = 0 ; i < deptGroupList2.size() ; i++){
                String managerAccount = deptGroupList2.get(i).getAccount();
                IUser user5 = iUserDAO.getOne(managerAccount);
                String managerEmail = user5.getEmail();
                if(managerEmail != null && !"".equals(managerEmail)){
                    allReceive.add(managerEmail);
                }
            }

            String operator = contractRecord.getSteward();
            IUser user3 = iUserDAO.getOne(operator);
            String operatorEmail = user3.getEmail();
            if(operatorEmail != null && !"".equals(operatorEmail)){
                allReceive.add(operatorEmail);
            }
            DeptGroup deptGroup3 = deptGroupDAO.getOne(operator);
            String dept3 = deptGroup3.getDeptCode();
            String group3 = deptGroup3.getGroupCode();
            List<DeptGroup> deptGroupList3 = deptGroupDAO.findByDeptCodeAndGroupCodeAndRole(dept3 , group3 , "manager");
            for(int i = 0 ; i < deptGroupList3.size() ; i++){
                String managerAccount = deptGroupList3.get(i).getAccount();
                IUser user5 = iUserDAO.getOne(managerAccount);
                String managerEmail = user5.getEmail();
                if(managerEmail != null && !"".equals(managerEmail)){
                    allReceive.add(managerEmail);
                }
            }

            String assistant = contractRecord.getServer();
            IUser user4 = iUserDAO.getOne(assistant);
            String assistantEmail = user4.getEmail();
            if(assistantEmail != null && !"".equals(assistantEmail)){
                allReceive.add(user4.getEmail());
            }
            DeptGroup deptGroup4 = deptGroupDAO.getOne(assistant);
            String dept4 = deptGroup4.getDeptCode();
            String group4 = deptGroup4.getGroupCode();
            List<DeptGroup> deptGroupList4 = deptGroupDAO.findByDeptCodeAndGroupCodeAndRole(dept4 , group4 , "manager");
            for(int i = 0 ; i < deptGroupList4.size() ; i++){
                String managerAccount = deptGroupList4.get(i).getAccount();
                IUser user5 = iUserDAO.getOne(managerAccount);
                String managerEmail = user5.getEmail();
                if(managerEmail != null && !"".equals(managerEmail)){
                    allReceive.add(managerEmail);
                }
            }
        }
        List<String> receiveList = new ArrayList<>();
        for(int i = 0 ; i < allReceive.size() ; i++){
            String receiver = allReceive.get(i);
            if(receiveList.contains(receiver)){
                continue;
            }else{
                receiveList.add(receiver);
            }
        }
        return receiveList;
    }
}
