package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_hafen_coin_rules_poster")
@ApiModel("哈奋币规则海报")
public class HafenCoinRulesPoster implements Serializable {
    private static final long serialVersionUID = -9012951446694383399L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    private Date createTime;
    private Date modifyTime;
}
