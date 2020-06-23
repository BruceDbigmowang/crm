package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "CRM_SalesPlan")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class SalesPlan {
    @Id
    @Column(name = "salesPlanId")
    String no;

    @Column(name = "company")
    String company;
    @Column(name = "salesPlanDesc")
    String describe;
    @Column(name = "totalStatus")
    String status;
    @Column(name = "firstVisitStatus")
    String fvStatus;
    @Column(name = "protocalStatus")
    String pStatus;
    @Column(name = "inquiryStatus")
    String iStatus;
    @Column(name = "reportStatus")
    String rStatus;
    @Column(name = "solutionStatus")
    String sStatus;
    @Column(name = "contractStatus")
    String cStatus;
    @Column(name = "erpStatus")
    String eStatus;
    @Column(name = "creater")
    String creater;
    @Column(name = "createTime")
    Date createTime;
    @Column(name = "ForwardId")
    int forwardId;
    @Column(name = "Forwarder")
    String forwarder;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFvStatus() {
        return fvStatus;
    }

    public void setFvStatus(String fvStatus) {
        this.fvStatus = fvStatus;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getiStatus() {
        return iStatus;
    }

    public void setiStatus(String iStatus) {
        this.iStatus = iStatus;
    }

    public String getrStatus() {
        return rStatus;
    }

    public void setrStatus(String rStatus) {
        this.rStatus = rStatus;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public String getcStatus() {
        return cStatus;
    }

    public void setcStatus(String cStatus) {
        this.cStatus = cStatus;
    }

    public String geteStatus() {
        return eStatus;
    }

    public void seteStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getForwardId() {
        return forwardId;
    }

    public void setForwardId(int forwardId) {
        this.forwardId = forwardId;
    }

    public String getForwarder() {
        return forwarder;
    }

    public void setForwarder(String forwarder) {
        this.forwarder = forwarder;
    }
}
