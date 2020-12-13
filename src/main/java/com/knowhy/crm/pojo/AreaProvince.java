package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.knowhy.crm.pk.PrimaryKeyAreaProvince;

import javax.persistence.*;

@Entity
@Table(name = "CRM_AreaProvince")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
@IdClass(PrimaryKeyAreaProvince.class)
public class AreaProvince {
    @Id
    int areaId;
    @Id
    int provinceId;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
