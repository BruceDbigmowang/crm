package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CRM_Stock")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String salePlanID;
    String type;
    BigDecimal numone;
    BigDecimal numtwo;
    BigDecimal numthree;
    BigDecimal numfour;
    BigDecimal numfive;
    BigDecimal sum;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getNumone() {
        return numone;
    }

    public void setNumone(BigDecimal numone) {
        this.numone = numone;
    }

    public BigDecimal getNumtwo() {
        return numtwo;
    }

    public void setNumtwo(BigDecimal numtwo) {
        this.numtwo = numtwo;
    }

    public BigDecimal getNumthree() {
        return numthree;
    }

    public void setNumthree(BigDecimal numthree) {
        this.numthree = numthree;
    }

    public BigDecimal getNumfour() {
        return numfour;
    }

    public void setNumfour(BigDecimal numfour) {
        this.numfour = numfour;
    }

    public BigDecimal getNumfive() {
        return numfive;
    }

    public void setNumfive(BigDecimal numfive) {
        this.numfive = numfive;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
