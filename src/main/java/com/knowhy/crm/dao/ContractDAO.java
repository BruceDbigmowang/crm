package com.knowhy.crm.dao;


import com.knowhy.crm.pojo.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractDAO extends JpaRepository<Contract, Integer> {
}
