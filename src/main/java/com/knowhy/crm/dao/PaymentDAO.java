package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentDAO extends JpaRepository<Payment , Integer> {
    List<Payment> findBySalePlanID(String salePlanID);
}
