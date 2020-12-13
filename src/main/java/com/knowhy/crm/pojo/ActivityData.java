package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CRM_ActivityData")
@JsonIgnoreProperties({"handler" , "hibernateLaztInitializer"})
public class ActivityData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int mId;
    String activityFile;
    String customerFile;
    int clueNum;
    int customerOne;
    int customerTwo;
    BigDecimal customerMoney;
    String createrAccount;
    String createrName;
    LocalDate createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getActivityFile() {
        return activityFile;
    }

    public void setActivityFile(String activityFile) {
        this.activityFile = activityFile;
    }

    public String getCustomerFile() {
        return customerFile;
    }

    public void setCustomerFile(String customerFile) {
        this.customerFile = customerFile;
    }

    public int getClueNum() {
        return clueNum;
    }

    public void setClueNum(int clueNum) {
        this.clueNum = clueNum;
    }

    public int getCustomerOne() {
        return customerOne;
    }

    public void setCustomerOne(int customerOne) {
        this.customerOne = customerOne;
    }

    public int getCustomerTwo() {
        return customerTwo;
    }

    public void setCustomerTwo(int customerTwo) {
        this.customerTwo = customerTwo;
    }

    public BigDecimal getCustomerMoney() {
        return customerMoney;
    }

    public void setCustomerMoney(BigDecimal customerMoney) {
        this.customerMoney = customerMoney;
    }

    public String getCreaterAccount() {
        return createrAccount;
    }

    public void setCreaterAccount(String createrAccount) {
        this.createrAccount = createrAccount;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
