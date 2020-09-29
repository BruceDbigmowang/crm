package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Postpone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostponeDAO extends JpaRepository<Postpone , String> {

    List<Postpone> findByMakeDate(LocalDate makeDate);

}
