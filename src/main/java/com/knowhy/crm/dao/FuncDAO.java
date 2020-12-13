package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Func;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncDAO extends JpaRepository<Func, Integer> {
    List<Func> findByCode(String code);
}
