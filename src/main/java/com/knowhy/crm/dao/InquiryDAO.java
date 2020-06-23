package com.knowhy.crm.dao;

import com.knowhy.crm.pojo.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryDAO extends JpaRepository<Inquiry, String> {
}
