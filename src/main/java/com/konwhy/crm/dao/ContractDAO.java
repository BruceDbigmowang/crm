package com.konwhy.crm.dao;


import com.konwhy.crm.pojo.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractDAO extends JpaRepository<Contract, Integer> {
}
