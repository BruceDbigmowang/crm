package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.knowhy.crm.pk.PrimaryKeyRoleFunc;

import javax.persistence.*;

@Entity
@Table(name = "roleAndFunc")
@IdClass(PrimaryKeyRoleFunc.class)
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class RoleFunc {
    @Id
    @Column(name = "roleId")
    int rid;

    @Id
    @Column(name = "funcId")
    int fid;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}
