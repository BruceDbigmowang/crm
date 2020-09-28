package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpportunityDAO extends JpaRepository<Opportunity , Integer> {

    List<Opportunity> findByAddressLike(String address);

    List<Opportunity> findByResourceLike(String resource);
}
