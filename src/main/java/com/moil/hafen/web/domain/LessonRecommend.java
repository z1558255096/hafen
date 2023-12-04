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
@TableName("t_lesson_recommend")
@ApiModel(value = "课程推荐")
public class LessonRecommend implements Serializable {
    private static final long serialVersionUID = -6739980377424284174L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    @ApiModelProperty(value = "上课形式 1线上 2线下")
    private Integer mode;//上课形式 1线上 2线下
    private transient String lessonName;
    private Integer lessonId;
    private Integer weight;
    private Integer status;//0上架 1下架
    private Double price;
    private Double actualPrice;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
