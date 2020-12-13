package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "CRM_Province")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String provinceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
