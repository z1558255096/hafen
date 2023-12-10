package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "用户地址管理")
public class CustomerAddress implements Serializable {
    private static final long serialVersionUID = 121544520445048160L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer customerId;
    @ApiModelProperty(value = "收货人")
    private String consignee;
    @ApiModelProperty(value = "性别")
    private String sex;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "区县")
    private String district;
    @ApiModelProperty(value = "详细地址")
    private String address;
    @ApiModelProperty(value = "0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty(value = "默认收货地址 0非默认 1默认")
    private Integer status;//默认收货地址 0非默认 1默认

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
