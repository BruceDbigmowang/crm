package com.konwhy.crm.dao;

import com.konwhy.crm.pk.PrimaryKeyAccountIdentity;
import com.konwhy.crm.pojo.AccountIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountIdentityDAO extends JpaRepository<AccountIdentity, PrimaryKeyAccountIdentity> {
}
