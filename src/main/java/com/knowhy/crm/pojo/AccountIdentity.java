package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.knowhy.crm.pk.PrimaryKeyAccountIdentity;

import javax.persistence.*;

@Entity
@Table(name = "CRM_IdentityAccount")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
@IdClass(PrimaryKeyAccountIdentity.class)
public class AccountIdentity {
    @Id
    @Column(name = "identityId")
    int identityId;

    @Id
    @Column(name = "account")
    String account;

    public int getIdentityId() {
        return identityId;
    }

    public void setIdentityId(int identityId) {
        this.identityId = identityId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
