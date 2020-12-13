package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SecretFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecretFileDAO extends JpaRepository<SecretFile , Integer> {

    List<SecretFile> findBySalePlanId(String salePlanID);
}
