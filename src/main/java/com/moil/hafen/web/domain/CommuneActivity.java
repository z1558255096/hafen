package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_activity")
public class CommuneActivity implements Serializable {
    private static final long serialVersionUID = -3474104737272813515L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Date activityTime;
    private Integer isShow;//是否微官网展示 0 展示 1展示
    private Integer weight;
    private String cover;
    private String detail;
    private Integer status;//上架状态 0上架 1下架
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient String activityTimeFrom;
    private transient String activityTimeTo;
}
