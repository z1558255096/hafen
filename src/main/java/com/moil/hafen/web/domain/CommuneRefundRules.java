package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_refund_rules")
public class CommuneRefundRules implements Serializable {
    private static final long serialVersionUID = -5376106139137366590L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer type;//0:公社-门票退款;1:公社-课程退款
    private Integer lastDay;
    private Integer refundPercent;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
