package com.knowhy.crm.pk;

import java.io.Serializable;

public class PrimaryKeyAccount implements Serializable {
    Integer id;
    String account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
