package com.konwhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.konwhy.crm.pk.PrimaryKeyAccountRole;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PrimaryKeyAccountRole.class)
@Table(name = "AccountAndRole")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class UserRole implements Serializable {
    @Id
    @Column(name = "userAccount")
    String account;
    @Id
    @Column(name = "roleId")
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
