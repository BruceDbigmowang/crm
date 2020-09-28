package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CRM_DataDetail")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class DataDetail {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int cid;
    BigDecimal ztzb;
    BigDecimal xdzb;
    BigDecimal jdzb;
    BigDecimal szzb;
    BigDecimal qtOne;
    BigDecimal cdpzb;
    BigDecimal xdpzb;
    BigDecimal tdpzb;
    BigDecimal zdpzb;
    BigDecimal qtTwo;
    BigDecimal cbn;
    BigDecimal pcd;
    BigDecimal hjzb;
    String hasGSG;
    String gsgBrand;
    String stockMoney;
    BigDecimal stockOne;
    BigDecimal stockTwo;
    BigDecimal stockThree;
    BigDecimal stockFour;
    BigDecimal normTool;
    BigDecimal unnormTool;
    String hasERP;
    String erpBrand;
    String hasMES;
    String mesBrand;
    String shiftManage;
    String productRest;
    int productNum;
    int stockPerson;
    int grantPerson;
    String grantWay;
    String returnWay;
    String payCycle;
    String payWay;
    String repair;
    BigDecimal repairSpend;
    String optimize;
    String appeal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public BigDecimal getZtzb() {
        return ztzb;
    }

    public void setZtzb(BigDecimal ztzb) {
        this.ztzb = ztzb;
    }

    public BigDecimal getXdzb() {
        return xdzb;
    }

    public void setXdzb(BigDecimal xdzb) {
        this.xdzb = xdzb;
    }

    public BigDecimal getJdzb() {
        return jdzb;
    }

    public void setJdzb(BigDecimal jdzb) {
        this.jdzb = jdzb;
    }

    public BigDecimal getSzzb() {
        return szzb;
    }

    public void setSzzb(BigDecimal szzb) {
        this.szzb = szzb;
    }

    public BigDecimal getQtOne() {
        return qtOne;
    }

    public void setQtOne(BigDecimal qtOne) {
        this.qtOne = qtOne;
    }

    public BigDecimal getCdpzb() {
        return cdpzb;
    }

    public void setCdpzb(BigDecimal cdpzb) {
        this.cdpzb = cdpzb;
    }

    public BigDecimal getXdpzb() {
        return xdpzb;
    }

    public void setXdpzb(BigDecimal xdpzb) {
        this.xdpzb = xdpzb;
    }

    public BigDecimal getTdpzb() {
        return tdpzb;
    }

    public void setTdpzb(BigDecimal tdpzb) {
        this.tdpzb = tdpzb;
    }

    public BigDecimal getZdpzb() {
        return zdpzb;
    }

    public void setZdpzb(BigDecimal zdpzb) {
        this.zdpzb = zdpzb;
    }

    public BigDecimal getQtTwo() {
        return qtTwo;
    }

    public void setQtTwo(BigDecimal qtTwo) {
        this.qtTwo = qtTwo;
    }

    public BigDecimal getCbn() {
        return cbn;
    }

    public void setCbn(BigDecimal cbn) {
        this.cbn = cbn;
    }

    public BigDecimal getPcd() {
        return pcd;
    }

    public void setPcd(BigDecimal pcd) {
        this.pcd = pcd;
    }

    public BigDecimal getHjzb() {
        return hjzb;
    }

    public void setHjzb(BigDecimal hjzb) {
        this.hjzb = hjzb;
    }

    public String getHasGSG() {
        return hasGSG;
    }

    public void setHasGSG(String hasGSG) {
        this.hasGSG = hasGSG;
    }

    public String getGsgBrand() {
        return gsgBrand;
    }

    public void setGsgBrand(String gsgBrand) {
        this.gsgBrand = gsgBrand;
    }

    public String getStockMoney() {
        return stockMoney;
    }

    public void setStockMoney(String stockMoney) {
        this.stockMoney = stockMoney;
    }

    public BigDecimal getStockOne() {
        return stockOne;
    }

    public void setStockOne(BigDecimal stockOne) {
        this.stockOne = stockOne;
    }

    public BigDecimal getStockTwo() {
        return stockTwo;
    }

    public void setStockTwo(BigDecimal stockTwo) {
        this.stockTwo = stockTwo;
    }

    public BigDecimal getStockThree() {
        return stockThree;
    }

    public void setStockThree(BigDecimal stockThree) {
        this.stockThree = stockThree;
    }

    public BigDecimal getStockFour() {
        return stockFour;
    }

    public void setStockFour(BigDecimal stockFour) {
        this.stockFour = stockFour;
    }

    public BigDecimal getNormTool() {
        return normTool;
    }

    public void setNormTool(BigDecimal normTool) {
        this.normTool = normTool;
    }

    public BigDecimal getUnnormTool() {
        return unnormTool;
    }

    public void setUnnormTool(BigDecimal unnormTool) {
        this.unnormTool = unnormTool;
    }

    public String getHasERP() {
        return hasERP;
    }

    public void setHasERP(String hasERP) {
        this.hasERP = hasERP;
    }

    public String getErpBrand() {
        return erpBrand;
    }

    public void setErpBrand(String erpBrand) {
        this.erpBrand = erpBrand;
    }

    public String getHasMES() {
        return hasMES;
    }

    public void setHasMES(String hasMES) {
        this.hasMES = hasMES;
    }

    public String getMesBrand() {
        return mesBrand;
    }

    public void setMesBrand(String mesBrand) {
        this.mesBrand = mesBrand;
    }

    public String getShiftManage() {
        return shiftManage;
    }

    public void setShiftManage(String shiftManage) {
        this.shiftManage = shiftManage;
    }

    public String getProductRest() {
        return productRest;
    }

    public void setProductRest(String productRest) {
        this.productRest = productRest;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
        this.productNum = productNum;
    }

    public int getStockPerson() {
        return stockPerson;
    }

    public void setStockPerson(int stockPerson) {
        this.stockPerson = stockPerson;
    }

    public int getGrantPerson() {
        return grantPerson;
    }

    public void setGrantPerson(int grantPerson) {
        this.grantPerson = grantPerson;
    }

    public String getGrantWay() {
        return grantWay;
    }

    public void setGrantWay(String grantWay) {
        this.grantWay = grantWay;
    }

    public String getReturnWay() {
        return returnWay;
    }

    public void setReturnWay(String returnWay) {
        this.returnWay = returnWay;
    }

    public String getPayCycle() {
        return payCycle;
    }

    public void setPayCycle(String payCycle) {
        this.payCycle = payCycle;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public BigDecimal getRepairSpend() {
        return repairSpend;
    }

    public void setRepairSpend(BigDecimal repairSpend) {
        this.repairSpend = repairSpend;
    }

    public String getOptimize() {
        return optimize;
    }

    public void setOptimize(String optimize) {
        this.optimize = optimize;
    }

    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }
}
