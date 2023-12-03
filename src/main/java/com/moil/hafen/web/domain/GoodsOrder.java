package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_goods_order")
public class GoodsOrder implements Serializable {
    private static final long serialVersionUID = -460512300633861389L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String orderSn;
    private Integer customerId;
    private String nickName;
    private String goodsName;
    private Integer goodsId;
    private Integer specsId;
    private String img;
    private String specs;
    private Double totalPrice;
    private Double actualPayment;
    private Double hafenCoin;
    private Double couponAmount;
    private Integer couponId;
    private Double price;
    private Integer addressId;
    private String address;
    private String consignee;
    private String consigneeMobile;
    private String remark;
    private Integer orderStatus;
    private Integer afterSalesType;//售后类型 1仅退款 2退货退款 3换货
    private Integer afterSalesStatus;//
    private Integer count;
    private Date payTime;
    private Integer payMethod;
    private String logisticsSn;
    private String deliveryCode;
    private String deliveryName;

    private Date afterSalesTime;
    private String afterSalesReason;
    private String afterSalesCertificate;
    private String afterSalesLogisticsSn;

    private String approvalReason;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient List<GoodsOrderLog> goodsOrderLogs;
    private transient List<GoodsOrderLogistics> goodsOrderLogistics;

}
