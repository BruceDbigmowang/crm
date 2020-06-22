package com.konwhy.crm.dao;

import com.konwhy.crm.pojo.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityDAO extends JpaRepository<Identity, Integer> {
}
