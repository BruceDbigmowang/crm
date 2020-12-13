package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDAO extends JpaRepository<Employee, Integer> {
    List<Employee> findBySalePlanID(String salePlanID);
}
