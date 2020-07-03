package com.knowhy.crm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;

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
 * @since 2020-07-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_SalesPlan")
public class CrmSalesplan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("salesPlanNumber")
    private String salesPlanNumber;

    private String company;

    @TableField("salesPlanDesc")
    private String salesPlanDesc;

    @TableField("costEstimate")
    private BigDecimal costEstimate;

    @TableField("costType")
    private String costType;

    @TableField("costCenter")
    private String costCenter;

    private BigDecimal amount;

    @TableField("appliedAmount")
    private BigDecimal appliedAmount;

    @TableField("usedAmount")
    private BigDecimal usedAmount;

    @TableField("totalStatus")
    private String totalStatus;

    @TableField("firstVisitStatus")
    private String firstVisitStatus;

    @TableField("protocalStatus")
    private String protocalStatus;

    @TableField("inquiryStatus")
    private String inquiryStatus;

    @TableField("reportStatus")
    private String reportStatus;

    @TableField("solutionStatus")
    private String solutionStatus;

    @TableField("contractStatus")
    private String contractStatus;

    @TableField("erpStatus")
    private String erpStatus;

    private String creater;

    @TableField("createTime")
    private Date createTime;

    @TableField("ForwardId")
    private Integer ForwardId;

    @TableField("Forwarder")
    private String Forwarder;


}
