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

@Data
@TableName("t_commune_activity")
@ApiModel("公社活动")
public class CommuneActivity implements Serializable {
    private static final long serialVersionUID = -3474104737272813515L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("活动名称")
    private String name;
    @ApiModelProperty("活动时间")
    private Date activityTime;
    @ApiModelProperty("是否微官网展示 0 展示 1不展示")
    private Integer isShow;//是否微官网展示 0 展示 1不展示
    @ApiModelProperty("权重")
    private Integer weight;
    @ApiModelProperty("封面")
    private String cover;
    @ApiModelProperty("详情")
    private String detail;
    @ApiModelProperty("上架状态 0上架 1下架")
    private Integer status;//上架状态 0上架 1下架
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
    private transient String activityTimeFrom;
    private transient String activityTimeTo;
}
