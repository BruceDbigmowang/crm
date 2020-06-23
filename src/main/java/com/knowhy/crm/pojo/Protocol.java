package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CRM_Protocol")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Protocol {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "salesPlanCode")
    String sNo;
    @Column(name = "protocolDesc")
    String describe;
    @Column(name = "attachment")
    String attachment;
    @Column(name = "attachmentPath")
    String path;
    @Column(name = "inquiryDate")
    Date inquiryDate;
    @Column(name = "approveFlowId")
    int approveId;
    @Column(name = "approveStatus")
    String approveStatus;
    @Column(name = "creater")
    String creater;
    @Column(name = "createtime")
    Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(Date inquiryDate) {
        this.inquiryDate = inquiryDate;
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
