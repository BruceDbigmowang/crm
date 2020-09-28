package com.knowhy.crm.service;

import com.knowhy.crm.dao.CompetitorDAO;
import com.knowhy.crm.pojo.Competitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitorService {
    @Autowired
    CompetitorDAO competitorDAO;

    public void saveCompetitor(Competitor competitor){
        competitorDAO.save(competitor);
    }

    public List<Competitor> findByName(String name){
        String newName = "%"+name+"%";
        return competitorDAO.findByCompetitorNameLike(newName);
    }
}
