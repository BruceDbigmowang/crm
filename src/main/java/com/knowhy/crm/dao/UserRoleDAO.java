package com.knowhy.crm.dao;


import com.knowhy.crm.pojo.UserRole;
import com.knowhy.crm.pk.PrimaryKeyAccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleDAO extends JpaRepository<UserRole, PrimaryKeyAccountRole> {
    List<UserRole> findByAccount(String account);
}
