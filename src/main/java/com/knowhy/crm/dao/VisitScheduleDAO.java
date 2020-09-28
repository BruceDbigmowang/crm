package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.VisitSchedule;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitScheduleDAO extends JpaRepository<VisitSchedule, Integer> {
    List<VisitSchedule> findByNumber(String number);
    List<VisitSchedule> findByVname(String name);
}
