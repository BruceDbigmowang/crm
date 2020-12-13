package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.PayCycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayCycleDAO extends JpaRepository<PayCycle , Integer> {

    List<PayCycle> findBySalePlanID(String salePlanID);
}
