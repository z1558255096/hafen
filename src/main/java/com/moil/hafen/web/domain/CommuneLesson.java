package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_commune_lesson")
public class CommuneLesson implements Serializable {
    private static final long serialVersionUID = 2856647267665896039L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String cover;
    private Integer categoryId;
    private transient String categoryName;
    private Double price;
    private Integer weight;
    private Integer status;//上架状态 0上架 1下架
    private Date startTime;
    private String description;
    private Integer isShow;//是否微官网展示 0 展示 1展示
    private String address;
    private String detail;
    private Integer refundRulesStatus;//展示退款规则 0展示 1不展示
    private String refundRules;
    private Integer delFlag;//0正常 1删除
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient List<CommuneLessonAdvance> communeLessonAdvanceList;
}
