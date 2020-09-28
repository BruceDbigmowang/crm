package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CustomerDAO extends JpaRepository<Customer , String> {

    List<Customer> findByCreateDateBetween(LocalDate start , LocalDate end);

    List<Customer> findByNameLike(String name);

    List<Customer> findByPhoneLike(String phone);

    List<Customer> findByCreateDate(LocalDate createDate);

}
