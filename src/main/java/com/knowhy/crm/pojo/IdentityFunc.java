package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.knowhy.crm.pk.PrimaryKeyIdentityFunc;


import javax.persistence.*;

@Entity
@Table(name = "CRM_IdentityFunc")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
@IdClass(PrimaryKeyIdentityFunc.class)
public class IdentityFunc{
    @Id
    @Column(name = "identityId")
    int identityId;

    @Id
    @Column(name = "funcId")
    int funcId;

    public int getIdentityId() {
        return identityId;
    }

    public void setIdentityId(int identityId) {
        this.identityId = identityId;
    }

    public int getFuncId() {
        return funcId;
    }

    public void setFuncId(int funcId) {
        this.funcId = funcId;
    }
}
