package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product , Integer> {
}
