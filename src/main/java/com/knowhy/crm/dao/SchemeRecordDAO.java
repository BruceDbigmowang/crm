package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.SchemeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchemeRecordDAO extends JpaRepository<SchemeRecord , Integer> {

    List<SchemeRecord> findBySalePlanID(String salePlanID);
    List<SchemeRecord> findBySalePlanIDAndFileType(String salePlanID , String fileType);
}
