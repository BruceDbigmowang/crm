package com.knowhy.crm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CRM_Func")
@JsonIgnoreProperties({"handler" , "hibernateLazyInitializer"})
public class Func {
    @Id
    @Column(name = "funcId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "funcName")
    String name;
    @Column(name = "funcUrl")
    String url;
    @Column(name = "funcCode")
    String code;
    @Column(name = "parentId")
    int pid;
    @Column(name = "funcType")
    int type;
    @Column(name = "funcStatus")
    String status;
    @Column(name = "sortNum")
    int num;
    @Column(name = "createTime")
    Date createTime;
    @Column(name = "updateTime")
    Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
