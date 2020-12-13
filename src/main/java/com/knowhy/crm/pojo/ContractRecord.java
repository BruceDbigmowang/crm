package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CRM_FileList")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class ContractRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String salePlanID;
    String contractFileID;
    String contractFileName;
    String fileName;
    String customerCode;
    String customerName;
    String productCode;
    String productName;
    String serverContent;
    int trainAsk;
    int serverAsk;
    String clearWay;
    String currency;
    BigDecimal parities;
    BigDecimal annualMoney;
    LocalDate concludeDate;
    LocalDate effectiveDate;
    LocalDate deadline;
    String contractStatus;
    String account;
    String name;
    String contractExplain;
    LocalDate createDate;
    String price;
    String steward;
    String technicist;
    String operator;
    String server;
    String principal;
    @Column(name = "cabinetAmount")
    int cabineAmount;
    @Column(name = "cabinetNum")
    String cabineNum;
    @Column(name = "cabinetSpend")
    BigDecimal cabineSpend;
    String useTDMP;
    @Column(name = "TDMPSpend")
    BigDecimal tdmpSpend;
    int payment;
    String otherServer;
    int remind;
    int dispose;

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

    public String getContractFileID() {
        return contractFileID;
    }

    public void setContractFileID(String contractFileID) {
        this.contractFileID = contractFileID;
    }

    public String getContractFileName() {
        return contractFileName;
    }

    public void setContractFileName(String contractFileName) {
        this.contractFileName = contractFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getServerContent() {
        return serverContent;
    }

    public void setServerContent(String serverContent) {
        this.serverContent = serverContent;
    }

    public int getTrainAsk() {
        return trainAsk;
    }

    public void setTrainAsk(int trainAsk) {
        this.trainAsk = trainAsk;
    }

    public int getServerAsk() {
        return serverAsk;
    }

    public void setServerAsk(int serverAsk) {
        this.serverAsk = serverAsk;
    }

    public String getClearWay() {
        return clearWay;
    }

    public void setClearWay(String clearWay) {
        this.clearWay = clearWay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getParities() {
        return parities;
    }

    public void setParities(BigDecimal parities) {
        this.parities = parities;
    }

    public BigDecimal getAnnualMoney() {
        return annualMoney;
    }

    public void setAnnualMoney(BigDecimal annualMoney) {
        this.annualMoney = annualMoney;
    }

    public LocalDate getConcludeDate() {
        return concludeDate;
    }

    public void setConcludeDate(LocalDate concludeDate) {
        this.concludeDate = concludeDate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractExplain() {
        return contractExplain;
    }

    public void setContractExplain(String contractExplain) {
        this.contractExplain = contractExplain;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSteward() {
        return steward;
    }

    public void setSteward(String steward) {
        this.steward = steward;
    }

    public String getTechnicist() {
        return technicist;
    }

    public void setTechnicist(String technicist) {
        this.technicist = technicist;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public int getCabineAmount() {
        return cabineAmount;
    }

    public void setCabineAmount(int cabineAmount) {
        this.cabineAmount = cabineAmount;
    }

    public String getCabineNum() {
        return cabineNum;
    }

    public void setCabineNum(String cabineNum) {
        this.cabineNum = cabineNum;
    }

    public BigDecimal getCabineSpend() {
        return cabineSpend;
    }

    public void setCabineSpend(BigDecimal cabineSpend) {
        this.cabineSpend = cabineSpend;
    }

    public String getUseTDMP() {
        return useTDMP;
    }

    public void setUseTDMP(String useTDMP) {
        this.useTDMP = useTDMP;
    }

    public BigDecimal getTdmpSpend() {
        return tdmpSpend;
    }

    public void setTdmpSpend(BigDecimal tdmpSpend) {
        this.tdmpSpend = tdmpSpend;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getOtherServer() {
        return otherServer;
    }

    public void setOtherServer(String otherServer) {
        this.otherServer = otherServer;
    }

    public int getRemind() {
        return remind;
    }

    public void setRemind(int remind) {
        this.remind = remind;
    }

    public int getDispose() {
        return dispose;
    }

    public void setDispose(int dispose) {
        this.dispose = dispose;
    }
}
