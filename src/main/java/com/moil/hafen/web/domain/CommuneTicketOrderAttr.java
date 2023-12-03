package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_commune_ticket_order_attr")
public class CommuneTicketOrderAttr implements Serializable {
    private static final long serialVersionUID = 4986233601914421994L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer orderId;
    private String advanceName;
    private String advanceValue;
    private Integer advanceId;

}
