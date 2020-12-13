package com.knowhy.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2020-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_SalesPlan")
public class CrmSalesplan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("salesPlanNumber")
    private String salesPlanNumber;

    @TableField("customerCode")
    private String customerCode;

    @TableField("customerName")
    private String customerName;

    private String describe;

    @TableField("makeDate")
    private LocalDate makeDate;

    private String creater;

    @TableField("createrName")
    private String createrName;

    @TableField("planStatus")
    private String planStatus;

    private String principal;

    @TableField("principalName")
    private String principalName;

    @TableField("allOperator")
    private String allOperator;

    private Integer step;

    @TableField("updateDate")
    private LocalDate updateDate;

    @TableField("spendTime")
    private Integer spendTime;

    private LocalDate deadline;

    private String assistant;

    private String operator;

    @TableField("lastTime")
    private LocalDate lastTime;


}
