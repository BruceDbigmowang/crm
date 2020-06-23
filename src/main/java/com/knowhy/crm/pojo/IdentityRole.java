package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.knowhy.crm.pk.PrimaryKeyIdentityRole;


import javax.persistence.*;

@Entity
@Table(name = "CRM_identityRole")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
@IdClass(PrimaryKeyIdentityRole.class)
public class IdentityRole{
    @Id
    @Column(name = "identityId")
    int identityId;

    @Id
    @Column(name = "roleId")
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
