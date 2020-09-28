package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Reimburse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReimburseDAO extends JpaRepository<Reimburse , String> {

    List<Reimburse> findByMakeDate(LocalDate now);
}
