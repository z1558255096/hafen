package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
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
    private int type;
    private int currentDayHour;
    private int beginDay;
    private int everydayHour;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
