package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Satisfaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SatisfactionDAO extends JpaRepository<Satisfaction, Integer> {

    List<Satisfaction> findByStatusAndUsing(String status , String using);

    List<Satisfaction> findByCustomerNameLikeAndStatusAndUsing(String csutomerName , String status , String using);

    List<Satisfaction> findByCustomerCode(String customerCode);
}
