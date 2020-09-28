package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Scheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchemeDAO extends JpaRepository<Scheme , Integer> {

    List<Scheme> findByReqNum(String reqNum);
}
