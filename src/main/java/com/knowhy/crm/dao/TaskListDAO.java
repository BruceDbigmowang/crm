package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListDAO extends JpaRepository<TaskList , Integer> {

    List<TaskList> findBySalePlanID(String salePlanID);
    List<TaskList> findBySalePlanIDAndStepCode(String salePlanID , String stepCode);
    List<TaskList> findBySalePlanIDAndTaskStatus(String salePlanID , String status);
}
