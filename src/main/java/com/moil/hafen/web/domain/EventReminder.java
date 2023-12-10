package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_event_reminder")
@ApiModel("家校设置-提醒规则")
public class EventReminder implements Serializable {
    private static final long serialVersionUID = -2160162775191739697L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("提醒类型 1-上课提醒，2-活动提醒")
    private int type;
    @ApiModelProperty("当天小时")
    private int currentDayHour;
    @ApiModelProperty("前几天")
    private int beginDay;
    @ApiModelProperty("每天几点提醒")
    private int everydayHour;
    @ApiModelProperty("状态：1-正常;0-禁用")
    private Integer status;
    private Date createTime;
    private Date modifyTime;
    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
