package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceDAO extends JpaRepository<Province , Integer> {

    List<Province> findByProvinceName(String provinceName);
}
