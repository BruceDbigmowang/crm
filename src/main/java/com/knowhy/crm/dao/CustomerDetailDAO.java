package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailDAO extends JpaRepository<CustomerDetail , Integer> {
}
