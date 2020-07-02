package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Manufacture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufactureDAO extends JpaRepository<Manufacture , Integer> {
    List<Manufacture> findByCid(int cid);
}
