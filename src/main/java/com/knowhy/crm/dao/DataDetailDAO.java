package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.DataDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataDetailDAO extends JpaRepository<DataDetail , Integer> {
    List<DataDetail> findByCid(int cid);
}
