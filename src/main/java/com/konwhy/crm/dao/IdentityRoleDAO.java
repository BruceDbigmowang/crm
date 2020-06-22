package com.konwhy.crm.dao;

import com.konwhy.crm.pk.PrimaryKeyIdentityRole;
import com.konwhy.crm.pojo.IdentityRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityRoleDAO extends JpaRepository<IdentityRole, PrimaryKeyIdentityRole> {
}
