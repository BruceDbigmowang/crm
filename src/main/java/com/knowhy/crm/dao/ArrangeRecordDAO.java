package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ArrangeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArrangeRecordDAO extends JpaRepository<ArrangeRecord , Integer> {
    List<ArrangeRecord> findBySalePlanID(String salePlanID);
    List<ArrangeRecord> findBySalePlanIDAndType(String salePlanID , String type);
    List<ArrangeRecord> findBySalePlanIDAndStepAndType(String salePlanID , String step , String type);
}
