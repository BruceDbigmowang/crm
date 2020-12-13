package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Task;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskDAO extends JpaRepository<Task , Integer> {

    List<Task> findBySalePlanID(String salePlanID);

    List<Task> findByDeadline(LocalDate deadline);

    List<Task> findByDeadlineBefore(LocalDate deadline);

    List<Task> findBySalePlanIDAndJobNameOrderByDeadlineAsc(String salePlanID , String jobName);

    List<Task> findBySalePlanIDAndJobNameAndJobLevel(String salePlanID , String jobName , int level);

    void deleteBySalePlanID(String salePlanID);

    void deleteBySalePlanIDAndJobName(String salePlanID , String jobName);
    void deleteByJobName(String jobName);
}
