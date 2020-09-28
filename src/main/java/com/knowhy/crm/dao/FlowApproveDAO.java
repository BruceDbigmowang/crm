package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.FlowApprove;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowApproveDAO extends JpaRepository<FlowApprove , Integer> {

    List<FlowApprove> findByAccount(String account);
}
