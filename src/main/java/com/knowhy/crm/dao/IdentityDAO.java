package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityDAO extends JpaRepository<Identity, Integer> {
}
