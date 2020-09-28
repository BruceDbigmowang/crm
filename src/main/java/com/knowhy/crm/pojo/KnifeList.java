package com.knowhy.crm.pojo;

import com.carrotsearch.hppc.HashOrderMixing;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "CRM_KnifeList")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class KnifeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String salePlanID;
    String workpiece;
    String processCode;
    String processName;
    String materialCode;
    String brand;
    String type;
    String model;
    Integer bladeNum;
    Integer knifeNum;
    String fixtureCode;
    String fixtureBrand;
    String coolWay;
    String rotateSpeed;
    String aoligei;
    String machineNum;
    String life;
    String asset;

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

    public String getWorkpiece() {
        return workpiece;
    }

    public void setWorkpiece(String workpiece) {
        this.workpiece = workpiece;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getBladeNum() {
        return bladeNum;
    }

    public void setBladeNum(Integer bladeNum) {
        this.bladeNum = bladeNum;
    }

    public Integer getKnifeNum() {
        return knifeNum;
    }

    public void setKnifeNum(Integer knifeNum) {
        this.knifeNum = knifeNum;
    }

    public String getFixtureCode() {
        return fixtureCode;
    }

    public void setFixtureCode(String fixtureCode) {
        this.fixtureCode = fixtureCode;
    }

    public String getFixtureBrand() {
        return fixtureBrand;
    }

    public void setFixtureBrand(String fixtureBrand) {
        this.fixtureBrand = fixtureBrand;
    }

    public String getCoolWay() {
        return coolWay;
    }

    public void setCoolWay(String coolWay) {
        this.coolWay = coolWay;
    }

    public String getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(String rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public String getAoligei() {
        return aoligei;
    }

    public void setAoligei(String aoligei) {
        this.aoligei = aoligei;
    }

    public String getMachineNum() {
        return machineNum;
    }

    public void setMachineNum(String machineNum) {
        this.machineNum = machineNum;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }
}
