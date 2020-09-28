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
 * @since 2020-09-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_TravelReq")
public class CrmTravelreq extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId("reqNum")
    private String reqNum;

    @TableField("leafId")
    private String leafId;

    private String account;

    private String name;

    private String type;

    @TableField("typeName")
    private String typeName;

    private String reason;

    @TableField("statusName")
    private String statusName;

    private String maker;

    @TableField("makerName")
    private String makerName;

    @TableField("makeDate")
    private LocalDate makeDate;


}
