package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerDetailDAO extends JpaRepository<CustomerDetail , Integer> {

    List<CustomerDetail> findBySalePlanID(String salePlaanID);
}
