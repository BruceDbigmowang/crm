package com.knowhy.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
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
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("CRM_AddressList")
public class CrmAddresslist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("customerCode")
    private String customerCode;

    @TableField("customerName")
    private String customerName;

    private String address;

    private String area;

    private String postcode;

    private String contacts;

    private String phone;

    @TableField("createDate")
    private LocalDate createDate;

    private String creater;

    private String describe;

    @TableField("mainAddress")
    private String mainAddress;


}
