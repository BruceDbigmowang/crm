package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CRM_SchemeRecord")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class SchemeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String salePlanID;
    String customerCode;
    String customerName;
    String fileName;
    String fileType;
    String personChange;
    String assetChange;
    String reduceChange;
    String creater;
    LocalDate createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalePlanID() {
        return salePlanID;
    }

    public void setSalePlanID(String salePlanID) {
        this.salePlanID = salePlanID;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getPersonChange() {
        return personChange;
    }

    public void setPersonChange(String personChange) {
        this.personChange = personChange;
    }

    public String getAssetChange() {
        return assetChange;
    }

    public void setAssetChange(String assetChange) {
        this.assetChange = assetChange;
    }

    public String getReduceChange() {
        return reduceChange;
    }

    public void setReduceChange(String reduceChange) {
        this.reduceChange = reduceChange;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
