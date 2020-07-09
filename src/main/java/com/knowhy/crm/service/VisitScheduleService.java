package com.knowhy.crm.service;

import com.knowhy.crm.dao.VisitScheduleDAO;
import com.knowhy.crm.pojo.VisitSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitScheduleService {

    @Autowired
    VisitScheduleDAO visitScheduleDAO;

    public void save(VisitSchedule visitSchedule){
        visitScheduleDAO.save(visitSchedule);
    }
}
