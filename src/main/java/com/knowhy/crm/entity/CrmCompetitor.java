package com.knowhy.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2020-07-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_Competitor")
public class CrmCompetitor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("competitorName")
    private String competitorName;

    private String owner;

    private String province;

    private String city;

    private String address;

    private String website;

    private String manace;

    private String creater;

    @TableField("createDate")
    private LocalDateTime createDate;


    @TableField("updatePerson")
    private String updatePerson;

    @TableField("updateDate")
    private LocalDateTime updateDate;

    private String customer;

    private String product;

    private String advantage;


}
