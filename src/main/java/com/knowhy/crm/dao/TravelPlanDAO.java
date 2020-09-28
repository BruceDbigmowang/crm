package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPlanDAO extends JpaRepository<TravelPlan , Integer> {

    List<TravelPlan> findBySalePlanNumber(String salePlanNum);

    List<TravelPlan> findByCustomer(String customer);
}
