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
 * @since 2020-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_TravelPlan")
public class CrmTravelplan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("salePlanNumber")
    private String salePlanNumber;

    private String customer;

    private String target;

    private String province;

    private String city;

    private String address;

    private LocalDate bdate;

    private String creater;

    @TableField("createDate")
    private LocalDateTime createDate;


}
