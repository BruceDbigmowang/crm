package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.BussinessTravel;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BussinessTravelDAO extends JpaRepository<BussinessTravel , String> {
    List<BussinessTravel> findByMakeDate(LocalDate now);

    List<BussinessTravel> findByCustomerCodeAndProcessCodeAndMakerAccount(String customerCode , String processCode , String maker);
    List<BussinessTravel> findByCustomerCodeAndProcessCodeAndMakerAccountAndTravelStatus(String customerCode , String processCode , String maker , String status);
}
