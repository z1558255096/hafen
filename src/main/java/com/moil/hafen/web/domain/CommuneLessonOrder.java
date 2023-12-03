package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_commune_lesson_order")
public class CommuneLessonOrder implements Serializable {
    private static final long serialVersionUID = 143246867533359177L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String orderSn;
    private Integer customerId;
    private String nickName;
    private String mobile;
    private Integer lessonId;
    private String lessonName;
    private Double price;
    private Integer count;
    private Double hafenCoin;
    private Double couponAmount;
    private Integer couponId;
    private String couponName;
    private Double actualPayment;
    private Double refundAmount;
    private String afterSalesReason;
    private Date afterSalesTime;
    private Integer status;//0待支付 1待核销 2已完成 3已关闭 4申请退课 5已退课
    private Date payTime;
    private Integer payMethod;//支付方式 0微信 1线下支付
    private Integer orderSource;//订单来源0用户下单 1后台新增 2导入
    private Integer type;//课程类型 1科技营 2体适能
    private String remark;
    private transient List<CommuneLessonOrderAttr> communeLessonOrderAttrList;


    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
