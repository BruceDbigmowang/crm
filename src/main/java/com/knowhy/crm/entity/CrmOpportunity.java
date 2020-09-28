package com.knowhy.crm.entity;

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

    private String name;

    private String fax;

    @TableField("companyName")
    private String companyName;

    private String email;

    private String resource;

    private String principal;

    private String body;

    private String dept;

    private String contract;

    private String maker;

    private String phone;

    @TableField("makeDate")
    private LocalDate makeDate;

    private String mobile;

    @TableField("leafType")
    private String leafType;

    private String address;

    @TableField("leafNum")
    private String leafNum;

    private String creater;

    @TableField("createDate")
    private LocalDateTime createDate;


}
