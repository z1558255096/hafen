package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_coupon_customer")
@ApiModel(value = "优惠券用户管理")
public class CouponCustomer implements Serializable {
    private static final long serialVersionUID = -32728279143787282L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("优惠券id")
    private Integer couponId;
    private Integer customerId;
    private Integer status;//使用状态 0未使用 1已使用
    private Date useTime;
    private String createName;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    public CouponCustomer(){

    }
    public CouponCustomer(Integer couponId, Integer customerId, Integer status, String createName, Date createTime){
        this.couponId = couponId;
        this.customerId = customerId;
        this.status = status;
        this.createName = createName;
        this.createTime = createTime;
    }
}
