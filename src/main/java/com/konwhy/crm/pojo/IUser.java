package com.konwhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konwhy.crm.pk.PrimaryKeyAccount;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CRM_Account")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
//@IdClass(PrimaryKeyAccount.class)
public class IUser{
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Integer id;
    @Id
    @Column(name = "account")
    String account;

    @Column(name = "name")
    String name;
    @Column(name = "password")
    String password;
    @Column(name = "salt")
    String salt;
    @Column(name = "company")
    String company;
    @Column(name = "phone")
    String phone;
    @Column(name = "email")
    String email;
    @Column(name = "status")
    String status;
    @Column(name = "note")
    String note;
    @Column(name = "createTime")
    Date createTime;
    @Column(name = "lastUpdateTime")
    Date updateTime;



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
