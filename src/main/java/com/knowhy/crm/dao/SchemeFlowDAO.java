package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SchemeFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SchemeFlowDAO extends JpaRepository<SchemeFlow , String> {

    List<SchemeFlow> findByMakeDate(LocalDate makeDate);
}
