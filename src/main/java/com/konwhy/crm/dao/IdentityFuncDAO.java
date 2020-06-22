package com.konwhy.crm.dao;

import com.konwhy.crm.pk.PrimaryKeyIdentityFunc;
import com.konwhy.crm.pojo.IdentityFunc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityFuncDAO extends JpaRepository<IdentityFunc, PrimaryKeyIdentityFunc> {
}
