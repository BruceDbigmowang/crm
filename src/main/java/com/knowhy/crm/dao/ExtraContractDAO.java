package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ExtraContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraContractDAO extends JpaRepository<ExtraContract , Integer> {
    List<ExtraContract> findBySalePlanID(String salePlanID);
}
