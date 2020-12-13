package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.PrincipalList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrincipalListDAO extends JpaRepository<PrincipalList , Integer> {

    List<PrincipalList> findByCustomerCode(String customerCode);

    List<PrincipalList> findByNameLike(String name);

    List<PrincipalList> findByCustomerNameLike(String customerName);

    List<PrincipalList> findByCustomerCodeAndMainPrincipal(String customerCode , String mainprincipal);
}
