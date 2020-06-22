package com.konwhy.crm.dao;

import com.konwhy.crm.pojo.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryDAO extends JpaRepository<Inquiry, String> {
}
