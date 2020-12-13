package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetDAO extends JpaRepository<Asset , Integer> {
    List<Asset> findBySalePlanID(String salePlanID);
}
