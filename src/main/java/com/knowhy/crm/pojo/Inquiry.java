package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CRM_Inquiry")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Inquiry {
    @Id
    @Column(name = "iNumber")
    String iNo;

    @Column(name = "salesPlanNumber")
    String sNo;
    @Column(name = "spend")
    BigDecimal spend;
    @Column(name = "company")
    String company;
    @Column(name = "approveFlowId")
    int approveId;
    @Column(name = "approveStatus")
    String approveStatus;
    @Column(name = "creater")
    String creater;
    @Column(name = "createtime")
    Date createTime;

    public String getiNo() {
        return iNo;
    }

    public void setiNo(String iNo) {
        this.iNo = iNo;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public BigDecimal getSpend() {
        return spend;
    }

    public void setSpend(BigDecimal spend) {
        this.spend = spend;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getApproveId() {
        return approveId;
    }

    public void setApproveId(int approveId) {
        this.approveId = approveId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
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
}
