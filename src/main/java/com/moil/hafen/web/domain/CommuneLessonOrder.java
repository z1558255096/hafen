package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_commune_lesson_order")
@ApiModel("公社课程订单")
public class CommuneLessonOrder implements Serializable {
    private static final long serialVersionUID = 143246867533359177L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("订单编号")
    private String orderSn;
    @ApiModelProperty("用户id")
    private Integer customerId;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户手机号")
    private String mobile;
    @ApiModelProperty("课程id")
    private Integer lessonId;
    @ApiModelProperty("课程名称")
    private String lessonName;
    @ApiModelProperty("课程价格")
    private Double price;
    @ApiModelProperty("课程数量")
    private Integer count;
    @ApiModelProperty("哈奋币")
    private Integer hafenCoin;
    @ApiModelProperty("优惠券金额")
    private Double couponAmount;
    @ApiModelProperty("优惠券id")
    private Integer couponId;
    @ApiModelProperty("优惠券名称")
    private String couponName;
    @ApiModelProperty("实际支付金额")
    private Double actualPayment;
    @ApiModelProperty("退款金额")
    private Double refundAmount;
    @ApiModelProperty("退款原因")
    private String afterSalesReason;
    @ApiModelProperty("退款时间")
    private Date afterSalesTime;
    @ApiModelProperty("0待支付 1待核销 2已完成 3已关闭 4申请退课 5已退课")
    private Integer status;//0待支付 1待核销 2已完成 3已关闭 4申请退课 5已退课
    @ApiModelProperty("支付时间")
    private Date payTime;
    @ApiModelProperty("支付方式 0微信 1线下支付")
    private Integer payMethod;//支付方式 0微信 1线下支付
    @ApiModelProperty("订单来源0用户下单 1后台新增 2导入")
    private Integer orderSource;//订单来源0用户下单 1后台新增 2导入
    @ApiModelProperty("课程类型 1科技营 2体适能")
    private Integer type;//课程类型 1科技营 2体适能
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("公社课程订单属性")
    private transient List<CommuneLessonOrderAttr> communeLessonOrderAttrList;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty("修改时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
