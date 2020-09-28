package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.TravelSum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelSumDAO extends JpaRepository<TravelSum, Integer> {

    void deleteByTravelID(String travelID);

    List<TravelSum> findByTravelID(String travelID);
}
