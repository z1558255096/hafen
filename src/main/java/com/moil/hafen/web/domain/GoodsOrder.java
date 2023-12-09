package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_goods_order")
@ApiModel(value = "商品订单管理")
public class GoodsOrder implements Serializable {
    private static final long serialVersionUID = -460512300633861389L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
    @ApiModelProperty(value = "用户id")
    private Integer customerId;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
    @ApiModelProperty(value = "商品规格id")
    private Integer specsId;
    @ApiModelProperty(value = "商品主图")
    private String img;
    @ApiModelProperty(value = "商品规格")
    private String specs;
    @ApiModelProperty(value = "商品总价")
    private Double totalPrice;
    @ApiModelProperty(value = "实际支付金额")
    private Double actualPayment;
    @ApiModelProperty(value = "哈奋币")
    private Integer hafenCoin;
    @ApiModelProperty(value = "优惠券金额")
    private Double couponAmount;
    @ApiModelProperty(value = "优惠券id")
    private Integer couponId;
    @ApiModelProperty(value = "商品价格")
    private Double price;
    @ApiModelProperty(value = "地址id")
    private Integer addressId;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "收货人")
    private String consignee;
    @ApiModelProperty(value = "收货人手机号")
    private String consigneeMobile;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "400待支付 401关闭 600待发货 700已发货待收货 1000已完成")
    private Integer orderStatus;
    @ApiModelProperty(value = "售后类型 1仅退款 2退货退款 3换货")
    private Integer afterSalesType;
    @ApiModelProperty(value = "售后状态 100待审核 101拒绝 102待寄出 103退货待收货 104换货待收货 200已退款 201以换货")
    private Integer afterSalesStatus;
    @ApiModelProperty(value = "数量")
    private Integer count;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "支付方式 0微信 1线下支付")
    private Integer payMethod;
    @ApiModelProperty(value = "物流单号")
    private String logisticsSn;
    @ApiModelProperty(value = "物流公司")
    private String deliveryCode;
    @ApiModelProperty(value = "物流公司名称")
    private String deliveryName;
    @ApiModelProperty(value = "发货时间")
    private Date afterSalesTime;
    @ApiModelProperty(value = "售后原因")
    private String afterSalesReason;
    @ApiModelProperty(value = "售后凭证")
    private String afterSalesCertificate;
    @ApiModelProperty(value = "售后物流单号")
    private String afterSalesLogisticsSn;
    @ApiModelProperty(value = "审批原因")
    private String approvalReason;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;
    @ApiModelProperty(value = "创建开始时间")
    private transient String createTimeFrom;
    @ApiModelProperty(value = "创建结束时间")
    private transient String createTimeTo;
    @ApiModelProperty(value = "商品订单日志管理")
    private transient List<GoodsOrderLog> goodsOrderLogs;
    @ApiModelProperty(value = "货物订单物流")
    private transient List<GoodsOrderLogistics> goodsOrderLogistics;

}
