package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.ReportFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportFileDAO extends JpaRepository<ReportFile , Integer> {

    List<ReportFile> findBySalePlanID(String salePlanID);

}
