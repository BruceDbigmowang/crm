package com.knowhy.crm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.knowhy.crm.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bruce
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_Opportunity")
public class CrmOpportunity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private int id;

    private String body;

    private String fax;

    private String resource;

    private String email;

    @TableField("companyName")
    private String companyName;

    private String principal;

    private String contract;

    private String dept;

    private String mobile;

    private String phone;

    @TableField("leafType")
    private String leafType;

    @TableField("leafNum")
    private String leafNum;

    String province;

    String city;

    String area;

    private String address;

    private String maker;

    @TableField("makeDate")
    private LocalDate makeDate;

    private String creater;

    @TableField("createDate")
    private LocalDateTime createDate;


}
