package com.knowhy.crm.pk;

import java.io.Serializable;

public class PrimaryKeyRoleFunc implements Serializable {
    int rid;
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
