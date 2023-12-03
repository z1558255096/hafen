package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_hafen_coin")
public class HafenCoin implements Serializable {
    private static final long serialVersionUID = -1071613475047719826L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer customerId;
    private Integer coin;
    private Integer type;
    private String source;
    private transient Integer totalCoin;
    private String remark;
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient Integer sumCoin;
}
