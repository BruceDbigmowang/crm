package com.knowhy.crm.dao;


import com.knowhy.crm.pk.PrimaryKeyRoleFunc;
import com.knowhy.crm.pojo.RoleFunc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleFuncDAO extends JpaRepository<RoleFunc, PrimaryKeyRoleFunc> {
    List<RoleFunc> findByRid(int rid);
    List<RoleFunc> findByFid(int fid);

    void deleteByRid(int rId);
    void deleteByRidAndFid(int rid , int fid);
}
