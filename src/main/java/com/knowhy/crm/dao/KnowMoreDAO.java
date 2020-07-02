package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.KnowMore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowMoreDAO extends JpaRepository<KnowMore, Integer> {

    KnowMore findByCid(int cid);
}
