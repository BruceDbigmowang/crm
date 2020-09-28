package com.knowhy.crm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.knowhy.crm.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Id;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bruce
 * @since 2020-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_Customer")
public class CrmCustomer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    @TableField("customerType")
    private String customerType;

    private String area;

    @TableField("superiorCustomer")
    private String superiorCustomer;

    @TableField("decisionMaker")
    private String decisionMaker;

    @TableField("decisionPhone")
    private String decisionPhone;

    private String principal;

    private String phone;

    private String email;

    private String wechat;

    private String industry;

    @TableField("companyAddress")
    private String companyAddress;

    @TableField("saleAmount")
    private BigDecimal saleAmount;

    @TableField("useAmount")
    private BigDecimal useAmount;

    @TableField("saleMan")
    private String saleMan;

    private String creater;

    @TableField("createDate")
    private LocalDate createDate;

    private String source;

    @TableField("followPerson")
    private String followPerson;

    @TableField("followName")
    private String followName;

    @TableField("followDate")
    private LocalDate followDate;

    @TableField("createrName")
    private String createrName;

    @TableField("followStatus")
    private String followStatus;


}
