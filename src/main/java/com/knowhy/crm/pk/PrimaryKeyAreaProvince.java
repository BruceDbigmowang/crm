package com.knowhy.crm.pk;

import java.io.Serializable;

public class PrimaryKeyAreaProvince implements Serializable {
    int areaId;
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
