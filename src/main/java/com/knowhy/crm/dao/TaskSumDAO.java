package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.TaskSum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskSumDAO extends JpaRepository<TaskSum , Integer> {

    List<TaskSum> findByPrincipalAndTaskLevelAndAuthorityOrderByDeadlineAsc(String principal , int level , String authority);

    List<TaskSum> findByTaskLevelAndAuthorityOrderByDeadlineAsc(int level , String authority);

    List<TaskSum> findBySalePlanIDAndTask(String salePlanID , String task);

    List<TaskSum> findBySalePlanID(String salePlanID);

    List<TaskSum> findByPrincipalAndDeadline(String principal , LocalDate deadline);
    List<TaskSum> findByPrincipalAndBdate(String principal , LocalDate deadline);

    List<TaskSum> findBySalePlanIDAndTaskOrderByDeadlineAsc(String salePlanID , String task);

    void deleteBySalePlanID(String salePlanID);

    void deleteBySalePlanIDAndTask(String salePlanID , String task);
}
