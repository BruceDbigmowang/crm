package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.OfflineFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfflineFileDAO extends JpaRepository<OfflineFile , Integer> {
    List<OfflineFile> findBySalePlanID(String salePlanID);
    List<OfflineFile> findBySalePlanIDAndType(String salePlanID , String type);
}
