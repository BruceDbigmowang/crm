package com.konwhy.crm.pk;

import java.io.Serializable;

public class PrimaryKeyAccountIdentity implements Serializable {
    int identityId;
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
