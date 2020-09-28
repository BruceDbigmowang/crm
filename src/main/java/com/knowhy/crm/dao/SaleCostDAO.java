package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SaleCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleCostDAO extends JpaRepository<SaleCost , Integer> {

    List<SaleCost> findByCostTypeLikeAndPrincipalLike(String costType , String principal);
}
