package com.knowhy.crm.pojo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CRM_InquiryContent")
public class InquiryContent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "number")
    String no;
    @Column(name = "material")
    String material;
    @Column(name = "inventory")
    String inventory;
    @Column(name = "supplier")
    String supplier;
    @Column(name = "stock")
    BigDecimal stock;
    @Column(name = "inStockTime")
    long stockTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public long getStockTime() {
        return stockTime;
    }

    public void setStockTime(long stockTime) {
        this.stockTime = stockTime;
    }
}
