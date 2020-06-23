package com.knowhy.crm.dao;

import com.knowhy.crm.pk.PrimaryKeyIdentityFunc;
import com.knowhy.crm.pojo.IdentityFunc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityFuncDAO extends JpaRepository<IdentityFunc, PrimaryKeyIdentityFunc> {
}
