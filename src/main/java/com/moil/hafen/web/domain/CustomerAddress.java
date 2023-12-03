package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-09-14 14:37
 **/
@Data
@TableName("t_customer_address")
public class CustomerAddress implements Serializable {
    private static final long serialVersionUID = 121544520445048160L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private String consignee;

    private String sex;

    private String mobile;

    private String province;

    private String city;

    private String district;

    private String address;

    private Integer status;//默认收货地址 0非默认 1默认

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
