package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.IdentityRole;
import com.knowhy.crm.pk.PrimaryKeyIdentityRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityRoleDAO extends JpaRepository<IdentityRole, PrimaryKeyIdentityRole> {
}
