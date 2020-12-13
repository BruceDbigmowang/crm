package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.DeptGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptGroupDAO extends JpaRepository<DeptGroup , String> {
    List<DeptGroup> findByDeptCodeAndGroupCode(String deptCode , String groupCode);

    List<DeptGroup> findByDeptCodeAndGroupCodeAndRole(String deptCode , String groupCode , String role);
}
