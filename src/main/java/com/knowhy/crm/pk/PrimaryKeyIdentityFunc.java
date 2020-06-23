package com.knowhy.crm.pk;

import java.io.Serializable;

public class PrimaryKeyIdentityFunc implements Serializable {
    int identityId;
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
