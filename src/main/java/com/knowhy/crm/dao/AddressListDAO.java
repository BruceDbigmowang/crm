package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.AddressList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressListDAO extends JpaRepository<AddressList , Integer> {

    List<AddressList> findByCustomerCode(String customerCode);
    List<AddressList> findByCustomerNameLike(String customerName);
    List<AddressList> findByAddressLike(String address);
}
