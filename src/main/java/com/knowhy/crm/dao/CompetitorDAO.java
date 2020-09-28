package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitorDAO extends JpaRepository<Competitor , Integer> {
    List<Competitor> findByCompetitorNameLike(String name);
}
