package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDAO extends JpaRepository<Activity , Integer> {
}
