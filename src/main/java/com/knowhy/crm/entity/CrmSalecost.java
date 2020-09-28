package com.knowhy.crm.entity;

import java.math.BigDecimal;
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
 * @since 2020-07-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_SaleCost")
public class CrmSalecost extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("costType")
    private String costType;

    @TableField("happenDate")
    private LocalDate happenDate;

    @TableField("costDescribe")
    private String costDescribe;

    private String contract;

    @TableField("costAmount")
    private BigDecimal costAmount;

    private String principal;

    private String creater;

    @TableField("createDate")
    private LocalDateTime createDate;


}
