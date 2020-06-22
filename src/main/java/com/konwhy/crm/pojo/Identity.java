package com.konwhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "CRM_Identity")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Identity {
    @Id
    @Column(name = "identityId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "identityDesc")
    String decribe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDecribe() {
        return decribe;
    }

    public void setDecribe(String decribe) {
        this.decribe = decribe;
    }
}
