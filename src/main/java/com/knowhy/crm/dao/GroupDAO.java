package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupDAO extends JpaRepository<Group , String> {
    List<Group> findByDeptCode(String deptCode);
}
