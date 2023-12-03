package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_commune_ticket_advance")
public class CommuneTicketAdvance implements Serializable {
    private static final long serialVersionUID = 7033507297723837218L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer ticketId;
    private Integer type;//类型： 0单选，1多选，2文本输入
    private String title;
    private String imgOption;
    private Integer index;
    private transient List<CommuneTicketAdvanceOption> communeTicketAdvanceOptionList;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
