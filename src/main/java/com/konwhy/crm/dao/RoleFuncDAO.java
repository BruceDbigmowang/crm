package com.konwhy.crm.dao;


import com.konwhy.crm.pk.PrimaryKeyRoleFunc;
import com.konwhy.crm.pojo.RoleFunc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleFuncDAO extends JpaRepository<RoleFunc, PrimaryKeyRoleFunc> {
    List<RoleFunc> findByRid(int rid);
}
