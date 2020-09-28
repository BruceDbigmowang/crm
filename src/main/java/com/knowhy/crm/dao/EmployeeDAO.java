package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDAO extends JpaRepository<Employee, Integer> {
}
