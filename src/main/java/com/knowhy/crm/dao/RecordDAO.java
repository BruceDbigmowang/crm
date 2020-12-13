package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordDAO extends JpaRepository<Record , Integer> {
    List<Record> findByAccount(String account);
    List<Record> findByCustomerNameLike(String customerName);
}
