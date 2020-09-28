package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Contract;
import com.knowhy.crm.pojo.ContractFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ContractFlowDAO extends JpaRepository<ContractFlow , String> {
    List<ContractFlow> findByMakeDate(LocalDate makeDate);
}
