package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.Nullable;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "CRM_SalesPlan")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
@Proxy(lazy = false)
public class SalesPlan {
    @Id
    @Column(name = "salesPlanNumber")
    String id;

    String customerCode;
    String customerName;
    String describe;
    LocalDate makeDate;
    String creater;
    String createrName;
    String planStatus;
    String principal;
    String principalName;
    String allOperator;
    @Nullable
    Integer step;
    LocalDate updateDate;
    Integer spendTime;
    LocalDate deadline;
    String assistant;
    String operator;
    LocalDate lastTime;
    String saleArrange;
    String operateArrange;
    String serviceWrite;
    String reportWrite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public LocalDate getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(LocalDate makeDate) {
        this.makeDate = makeDate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getAllOperator() {
        return allOperator;
    }

    public void setAllOperator(String allOperator) {
        this.allOperator = allOperator;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Integer spendTime) {
        this.spendTime = spendTime;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getAssistant() {
        return assistant;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LocalDate getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDate lastTime) {
        this.lastTime = lastTime;
    }

    public String getSaleArrange() {
        return saleArrange;
    }

    public void setSaleArrange(String saleArrange) {
        this.saleArrange = saleArrange;
    }

    public String getOperateArrange() {
        return operateArrange;
    }

    public void setOperateArrange(String operateArrange) {
        this.operateArrange = operateArrange;
    }

    public String getServiceWrite() {
        return serviceWrite;
    }

    public void setServiceWrite(String serviceWrite) {
        this.serviceWrite = serviceWrite;
    }

    public String getReportWrite() {
        return reportWrite;
    }

    public void setReportWrite(String reportWrite) {
        this.reportWrite = reportWrite;
    }
}
