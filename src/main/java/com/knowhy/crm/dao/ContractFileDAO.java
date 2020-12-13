package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ContractFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractFileDAO extends JpaRepository<ContractFile , Integer> {
    List<ContractFile> findByReqNum(String reqNum);

    List<ContractFile> findByCustomerNameLike(String customerName);
}
