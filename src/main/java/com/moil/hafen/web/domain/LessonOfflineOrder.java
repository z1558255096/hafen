package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_offline_order")
public class LessonOfflineOrder implements Serializable {
    private static final long serialVersionUID = -861310194040475670L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String orderSn;
    private Integer customerId;
    private String nickName;
    private String mobile;
    private Integer campusId;
    private String campusName;
    private Integer studentId;
    private String studentName;
    private Integer lessonId;
    private String lessonName;
    private Integer lessonCampusPriceId;
    private Double price;
    private Integer count;
    private Integer lessonCount;
    private Integer giveLessonCount;
    private Integer useLessonCount;
    private Double hafenCoin;
    private Double couponAmount;
    private Integer couponId;
    private String couponName;
    private Double actualPayment;
    private Double refundAmount;
    private String afterSalesReason;
    private Date afterSalesTime;
    private Integer status;//0待支付 1已完成 2已关闭 3退款中 4已退课
    private Date payTime;
    private Integer payMethod;//支付方式 0微信 1线下支付
    private Integer orderType;//订单类型 0缴费 1退费
    private Integer orderSource;//订单来源0用户下单 1后台新增 2导入
    private Integer type;//课程类型 1科技营 2体适能
    private String remark;


    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
