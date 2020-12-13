package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "CRM_OperateTask")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class OperateTask {
    @Id
    String id;
    String stepName;
    String customerTask;
    String selfTask;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getCustomerTask() {
        return customerTask;
    }

    public void setCustomerTask(String customerTask) {
        this.customerTask = customerTask;
    }

    public String getSelfTask() {
        return selfTask;
    }

    public void setSelfTask(String selfTask) {
        this.selfTask = selfTask;
    }
}
