package com.konwhy.crm.dao;

import com.konwhy.crm.pk.PrimaryKeyAccount;
import com.konwhy.crm.pojo.IUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserDAO extends JpaRepository<IUser, String> {
    List<IUser> findByPhone(String phone);
    List<IUser> findByAccount(String account);

    List<IUser> findByAccountAndEmail(String account, String email);
}
