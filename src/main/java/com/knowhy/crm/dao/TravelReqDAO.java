package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.TravelReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TravelReqDAO extends JpaRepository<TravelReq , String> {

    List<TravelReq> findByMakeDate(LocalDate makeDate);

    List<TravelReq> findByMaker(String maker);

    List<TravelReq> findByStatus(String status);

}
