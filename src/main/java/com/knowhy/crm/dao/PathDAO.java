package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Path;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PathDAO extends JpaRepository<Path, Integer> {

    List<Path> findByCreateTimeBetween(Date start , Date end);
}
