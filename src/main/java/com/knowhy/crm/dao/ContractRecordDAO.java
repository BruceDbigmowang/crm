package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ContractRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRecordDAO extends JpaRepository<ContractRecord , Integer> {

    List<ContractRecord> findBySalePlanID(String salePlanID);

    List<ContractRecord> findByTechnicist(String technicist);

    List<ContractRecord> findByCustomerNameLikeAndTechnicist(String customerName , String technicist);

    List<ContractRecord> findByOperator(String operator);

    List<ContractRecord> findByCustomerNameLikeAndOperator(String customerName , String operator);
}
