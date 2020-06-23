package com.knowhy.crm.pk;

import java.io.Serializable;

public class PrimaryKeyAccountRole implements Serializable {
    String account;
    int id;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
