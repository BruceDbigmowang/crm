package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CRM_TravelSum")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class TravelSum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String travelID;
    String travelSum;
    String needSupport;
    String trouble;
    String oppose;
    String nextPlan;
    String maker;
    String makerName;
    LocalDate makeDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTravelID() {
        return travelID;
    }

    public void setTravelID(String travelID) {
        this.travelID = travelID;
    }

    public String getTravelSum() {
        return travelSum;
    }

    public void setTravelSum(String travelSum) {
        this.travelSum = travelSum;
    }

    public String getNeedSupport() {
        return needSupport;
    }

    public void setNeedSupport(String needSupport) {
        this.needSupport = needSupport;
    }

    public String getTrouble() {
        return trouble;
    }

    public void setTrouble(String trouble) {
        this.trouble = trouble;
    }

    public String getOppose() {
        return oppose;
    }

    public void setOppose(String oppose) {
        this.oppose = oppose;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public LocalDate getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(LocalDate makeDate) {
        this.makeDate = makeDate;
    }
}
