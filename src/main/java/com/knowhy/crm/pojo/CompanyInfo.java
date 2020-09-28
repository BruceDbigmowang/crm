package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CRM_CompanyInfo")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class CompanyInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "CompanyName")
    String companyName;
    @Column(name = "Contact")
    String contact;
    @Column(name = "ContactWay")
    String contactWay;
    @Column(name = "WechatNum")
    String wechatNum;
    @Column(name = "RegisterMoney")
    BigDecimal registerMoney;
    @Column(name = "EstablishTime")
    String establishTime;
    @Column(name = "BussinessNature")
    String bussinessNature;
    @Column(name = "SonCompanyNum")
    String sonCompanyNum;
    @Column(name = "EmployeeNum")
    String employeeNum;
    @Column(name = "Industry")
    String industry;
    @Column(name = "IndustryNature")
    String industryNature;
    @Column(name = "Product")
    String product;
    @Column(name = "Picture")
    String picture;
    @Column(name = "CreateDate")
    String createDate;
    @Column(name = "CreateTime")
    Date createTime;
    @Column(name = "salePlanID")
    String salePlanID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
    }

    public BigDecimal getRegisterMoney() {
        return registerMoney;
    }

    public void setRegisterMoney(BigDecimal registerMoney) {
        this.registerMoney = registerMoney;
    }

    public String getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public String getBussinessNature() {
        return bussinessNature;
    }

    public void setBussinessNature(String bussinessNature) {
        this.bussinessNature = bussinessNature;
    }

    public String getSonCompanyNum() {
        return sonCompanyNum;
    }

    public void setSonCompanyNum(String sonCompanyNum) {
        this.sonCompanyNum = sonCompanyNum;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryNature() {
        return industryNature;
    }

    public void setIndustryNature(String industryNature) {
        this.industryNature = industryNature;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSalePlanID() {
        return salePlanID;
    }

    public void setSalePlanID(String salePlanID) {
        this.salePlanID = salePlanID;
    }
}
