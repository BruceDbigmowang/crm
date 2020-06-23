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
 * @since 2020-06-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_Account")
public class CrmAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String account;

    private String name;

    private String password;

    private String salt;

    private String company;

    private String phone;

    private String email;

    private String note;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("lastUpdateTime")
    private LocalDateTime lastUpdateTime;


}
