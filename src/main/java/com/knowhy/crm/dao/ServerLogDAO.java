package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ServerLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerLogDAO extends JpaRepository<ServerLog , Integer> {

    List<ServerLog> findBySalePlanID(String salePlanID);
}
