package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CRM_Scheme")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Scheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String reqNum;
    String customerCode;
    String customerName;
    String firstFile;
    String firstMaker;
    String firstMakerName;
    LocalDate firstMakeDate;
    String secondFile;
    String secondMaker;
    String secondMakerName;
    LocalDate secondMakeDate;
    String personChange;
    String assetChange;
    String reduceChange;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReqNum() {
        return reqNum;
    }

    public void setReqNum(String reqNum) {
        this.reqNum = reqNum;
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

    public String getFirstFile() {
        return firstFile;
    }

    public void setFirstFile(String firstFile) {
        this.firstFile = firstFile;
    }

    public String getFirstMaker() {
        return firstMaker;
    }

    public void setFirstMaker(String firstMaker) {
        this.firstMaker = firstMaker;
    }

    public String getFirstMakerName() {
        return firstMakerName;
    }

    public void setFirstMakerName(String firstMakerName) {
        this.firstMakerName = firstMakerName;
    }

    public LocalDate getFirstMakeDate() {
        return firstMakeDate;
    }

    public void setFirstMakeDate(LocalDate firstMakeDate) {
        this.firstMakeDate = firstMakeDate;
    }

    public String getSecondFile() {
        return secondFile;
    }

    public void setSecondFile(String secondFile) {
        this.secondFile = secondFile;
    }

    public String getSecondMaker() {
        return secondMaker;
    }

    public void setSecondMaker(String secondMaker) {
        this.secondMaker = secondMaker;
    }

    public String getSecondMakerName() {
        return secondMakerName;
    }

    public void setSecondMakerName(String secondMakerName) {
        this.secondMakerName = secondMakerName;
    }

    public LocalDate getSecondMakeDate() {
        return secondMakeDate;
    }

    public void setSecondMakeDate(LocalDate secondMakeDate) {
        this.secondMakeDate = secondMakeDate;
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
}
