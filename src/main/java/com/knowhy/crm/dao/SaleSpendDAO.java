package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SaleSpend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleSpendDAO extends JpaRepository<SaleSpend , Integer> {

    List<SaleSpend> findByCostTypeLikeAndPrincipalLike(String type , String principal);

}
