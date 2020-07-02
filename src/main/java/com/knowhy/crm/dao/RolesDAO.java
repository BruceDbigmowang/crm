package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolesDAO extends JpaRepository<Roles, Integer> {

    @Query(value = "select distinct r.* from CRM_Account a , AccountAndRole ar , CRM_Role r  where " +
            "a.account = ar.userAccount and r.roleId = ar.roleId and a.account = ?1 ", nativeQuery = true)
    List<Roles> findByAccount(String account);

    List<Roles> findByRoleName(String name);
}
