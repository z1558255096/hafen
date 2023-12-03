package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_commune_ticket_advance_option")
public class CommuneTicketAdvanceOption implements Serializable {
    private static final long serialVersionUID = -7711788557099009809L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer ticketId;
    private Integer advanceId;
    private String title;
    private String img;
    private Integer index;
}
