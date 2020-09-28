package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDAO extends JpaRepository<Supplier , Integer> {
}
