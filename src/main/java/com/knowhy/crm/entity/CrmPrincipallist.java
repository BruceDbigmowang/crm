package com.knowhy.crm.entity;

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
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_PrincipalList")
public class CrmPrincipallist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @TableField("customerCode")
    private String customerCode;

    @TableField("customerName")
    private String customerName;

    private String name;

    private String phone;

    private String email;

    private String wechat;

    private String sex;

    private String position;

    private String hobby;

    private String relationship;

    private LocalDate birthday;

    @TableField("mainPrincipal")
    private String mainPrincipal;

    @TableField("createDate")
    private LocalDate createDate;

    private String creater;


}
