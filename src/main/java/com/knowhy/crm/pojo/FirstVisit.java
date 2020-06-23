package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "CRM_FirstVisit")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class FirstVisit {
    @Id
    @Column(name = "vNumber")
    String no;

    @Column(name = "salesPlanId")
    String sNo;
    @Column(name = "spend")
    BigDecimal spend;
    @Column(name = "approveFlowId")
    int approveFlowId;
    @Column(name = "approveStatus")
    String status;
    @Column(name = "creater")
    String creater;
    @Column(name = "createTime")
    Date createTime;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public int getApproveFlowId() {
        return approveFlowId;
    }

    public void setApproveFlowId(int approveFlowId) {
        this.approveFlowId = approveFlowId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
