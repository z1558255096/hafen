package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_online_order")
@ApiModel(value = "线上课程订单")
public class LessonOnlineOrder implements Serializable {
    private static final long serialVersionUID = 1661730053418276473L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String orderSn;
    private Integer customerId;
    private String nickName;
    private String mobile;
    private Integer lessonId;
    private String lessonName;
    private Double price;
    private Integer hafenCoin;
    private Double couponAmount;
    private Integer couponId;
    private String couponName;
    private Double actualPayment;
    private Double refundAmount;
    private Integer status;//0待支付 1已完成 2已关闭 3退款中 4已退课
    private String afterSalesReason;
    private Date afterSalesTime;
    private Date payTime;
    private Integer payMethod;//支付方式 0微信 1线下支付
    private Integer orderSource;//订单来源0用户下单 1后台新增
    private Integer type;//课程类型 1科技营 2体适能
    private String remark;
    private String studentName;
    private String contactNumber;


    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
