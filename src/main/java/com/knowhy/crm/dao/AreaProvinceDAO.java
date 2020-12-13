package com.knowhy.crm.dao;

import com.knowhy.crm.pk.PrimaryKeyAreaProvince;
import com.knowhy.crm.pojo.AreaProvince;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaProvinceDAO extends JpaRepository<AreaProvince , PrimaryKeyAreaProvince> {
    List<AreaProvince> findByProvinceId(int provinceId);

    List<AreaProvince> findByAreaId(int areaId);

    void deleteByProvinceId(int provinceId);
}
