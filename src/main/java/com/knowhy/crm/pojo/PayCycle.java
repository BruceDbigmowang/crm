package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CRM_PayCycle")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class PayCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String salePlanID;
    String customer;
    BigDecimal payone;
    BigDecimal paytwo;
    BigDecimal paythree;
    BigDecimal payfour;

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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public BigDecimal getPayone() {
        return payone;
    }

    public void setPayone(BigDecimal payone) {
        this.payone = payone;
    }

    public BigDecimal getPaytwo() {
        return paytwo;
    }

    public void setPaytwo(BigDecimal paytwo) {
        this.paytwo = paytwo;
    }

    public BigDecimal getPaythree() {
        return paythree;
    }

    public void setPaythree(BigDecimal paythree) {
        this.paythree = paythree;
    }

    public BigDecimal getPayfour() {
        return payfour;
    }

    public void setPayfour(BigDecimal payfour) {
        this.payfour = payfour;
    }
}
