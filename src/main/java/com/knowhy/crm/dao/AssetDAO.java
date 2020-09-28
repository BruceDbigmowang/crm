package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetDAO extends JpaRepository<Asset , Integer> {
}
