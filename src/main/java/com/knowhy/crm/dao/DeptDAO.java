package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptDAO extends JpaRepository<Dept , String> {
    List<Dept> findByDeptNameLike(String deptName);
}
