package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanDAO extends JpaRepository<TravelPlan , Integer> {
}
