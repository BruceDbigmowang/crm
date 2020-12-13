package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product , Integer> {

    List<Product> findBySalePlanID(String salePlanID);
}
