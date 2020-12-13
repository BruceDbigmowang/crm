package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "CRM_Area")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String areaName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
