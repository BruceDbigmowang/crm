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
 * @since 2020-07-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_SaleSpend")
public class CrmSalespend extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("costType")
    private String costType;

    private String describe;

    private BigDecimal amount;

    @TableField("happenTime")
    private LocalDate happenTime;

    @TableField("happenDate")
    private String happenDate;

    private String contract;

    private String principal;

    private String creater;

    @TableField("createDate")
    private LocalDateTime createDate;


}
