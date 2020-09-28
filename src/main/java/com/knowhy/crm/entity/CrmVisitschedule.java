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
@TableName("CRM_VisitSchedule")
public class CrmVisitschedule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String number;

    private String vname;

    private String destination;

    private LocalDate bdate;

    private String btime;

    private String creater;

    @TableField("createDate")
    private LocalDateTime createDate;


}
