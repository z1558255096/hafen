package com.moil.hafen.web.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_commune_ticket")
public class CommuneTicket implements Serializable {
    private static final long serialVersionUID = 5876645447159453361L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String cover;
    private String name;
    private String openTime;
    private String description;
    private String mpUrl;
    private String address;
    private Integer isShow;//是否微官网展示 0 展示 1展示
    private Integer status;//上架状态 0上架 1下架
    private Double price;
    private String detail;
    private Integer refundRulesStatus;//展示退款规则 0展示 1不展示
    private String refundRules;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient List<CommuneTicketAdvance> communeTicketAdvanceList;
}
