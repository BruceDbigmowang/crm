package com.konwhy.crm.dao;


import com.konwhy.crm.pk.PrimaryKeyAccountRole;
import com.konwhy.crm.pojo.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleDAO extends JpaRepository<UserRole, PrimaryKeyAccountRole> {
    List<UserRole> findByAccount(String account);
}
