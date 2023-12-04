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
@TableName("t_lesson_offline_order")
@ApiModel(value = "线下课订单管理")
public class LessonOfflineOrder implements Serializable {
    private static final long serialVersionUID = -861310194040475670L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "订单编号")
    private String orderSn;
    @ApiModelProperty(value = "用户id")
    private Integer customerId;
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    @ApiModelProperty(value = "用户手机号")
    private String mobile;
    @ApiModelProperty(value = "校区id")
    private Integer campusId;
    @ApiModelProperty(value = "校区名称")
    private String campusName;
    @ApiModelProperty(value = "学员id")
    private Integer studentId;
    @ApiModelProperty(value = "学员名称")
    private String studentName;
    @ApiModelProperty(value = "课程id")
    private Integer lessonId;
    @ApiModelProperty(value = "课程名称")
    private String lessonName;
    private Integer lessonCampusPriceId;
    @ApiModelProperty(value = "课程价格")
    private Double price;
    @ApiModelProperty(value = "课程数量")
    private Integer count;
    @ApiModelProperty(value = "课程总价")
    private Integer lessonCount;
    @ApiModelProperty(value = "赠送课程数量")
    private Integer giveLessonCount;
    @ApiModelProperty(value = "使用课程数量")
    private Integer useLessonCount;
    @ApiModelProperty(value = "课程总价")
    private Double hafenCoin;
    @ApiModelProperty(value = "优惠券金额")
    private Double couponAmount;
    @ApiModelProperty(value = "优惠券id")
    private Integer couponId;
    @ApiModelProperty(value = "优惠券名称")
    private String couponName;
    @ApiModelProperty(value = "实际付款")
    private Double actualPayment;
    @ApiModelProperty(value = "退款金额")
    private Double refundAmount;
    @ApiModelProperty(value = "退款原因")
    private String afterSalesReason;
    @ApiModelProperty(value = "售后服务时间")
    private Date afterSalesTime;
    @ApiModelProperty(value = "状态 0待支付 1已完成 2已关闭 3退款中 4已退课")
    private Integer status;//0待支付 1已完成 2已关闭 3退款中 4已退课
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "支付方式 0微信 1线下支付")
    private Integer payMethod;//支付方式 0微信 1线下支付
    @ApiModelProperty(value = "订单类型 0缴费 1退费")
    private Integer orderType;//订单类型 0缴费 1退费
    @ApiModelProperty(value = "订单来源0用户下单 1后台新增 2导入")
    private Integer orderSource;//订单来源0用户下单 1后台新增 2导入
    @ApiModelProperty(value = "课程类型 1科技营 2体适能")
    private Integer type;//课程类型 1科技营 2体适能
    @ApiModelProperty(value = "备注")
    private String remark;


    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
