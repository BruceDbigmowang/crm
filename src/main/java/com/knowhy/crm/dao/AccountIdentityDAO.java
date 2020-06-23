package com.knowhy.crm.dao;

import com.knowhy.crm.pk.PrimaryKeyAccountIdentity;
import com.knowhy.crm.pojo.AccountIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountIdentityDAO extends JpaRepository<AccountIdentity, PrimaryKeyAccountIdentity> {
}
