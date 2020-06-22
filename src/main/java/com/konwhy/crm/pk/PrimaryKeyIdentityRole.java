package com.konwhy.crm.pk;

import java.io.Serializable;

public class PrimaryKeyIdentityRole implements Serializable {
    int identityId;
    int roleId;

    public int getIdentityId() {
        return identityId;
    }

    public void setIdentityId(int identityId) {
        this.identityId = identityId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
