package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "CRM_Product")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String salePlanID;
    String productDate;
    String partNum;
    String partName;
    Integer allAmount;
    Integer amountOne;
    Integer amountTwo;
    Integer amountThree;
    String note;

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

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getPartNum() {
        return partNum;
    }

    public void setPartNum(String partNum) {
        this.partNum = partNum;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Integer getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(Integer allAmount) {
        this.allAmount = allAmount;
    }

    public Integer getAmountOne() {
        return amountOne;
    }

    public void setAmountOne(Integer amountOne) {
        this.amountOne = amountOne;
    }

    public Integer getAmountTwo() {
        return amountTwo;
    }

    public void setAmountTwo(Integer amountTwo) {
        this.amountTwo = amountTwo;
    }

    public Integer getAmountThree() {
        return amountThree;
    }

    public void setAmountThree(Integer amountThree) {
        this.amountThree = amountThree;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
