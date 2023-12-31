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
@TableName("t_commune_lesson")
@ApiModel("公社课程")
public class CommuneLesson implements Serializable {
    private static final long serialVersionUID = 2856647267665896039L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("课程标题")
    private String name;
    @ApiModelProperty("课程封面")
    private String cover;
    @ApiModelProperty("课程类别id")
    private Integer categoryId;
    @ApiModelProperty("课程类别名称")
    private transient String categoryName;
    private Double price;
    @ApiModelProperty("小程序地址")
    private String mpUrl;
    @ApiModelProperty("纬度")
    private String lat;
    @ApiModelProperty("经度")
    private String lng;
    @ApiModelProperty("权重")
    private Integer weight;
    @ApiModelProperty("上架状态 0上架 1下架")
    private Integer status;//上架状态 0上架 1下架
    @ApiModelProperty("课程开始时间")
    private String startTime;
    @ApiModelProperty("课程结束时间")
    private String endTime;
    @ApiModelProperty("课程描述")
    private String description;
    @ApiModelProperty("是否微官网展示 0 展示 1不展示")
    private Integer isShow;//是否微官网展示 0 展示 1不展示
    @ApiModelProperty("课程地址")
    private String address;
    @ApiModelProperty("课程详情")
    private String detail;
    @ApiModelProperty("展示退款规则 0展示 1不展示")
    private Integer refundRulesStatus;//展示退款规则 0展示 1不展示
    @ApiModelProperty("退款规则")
    private String refundRules;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    @ApiModelProperty("高级设置")
    private transient List<CommuneLessonAdvance> communeLessonAdvanceList;
}
