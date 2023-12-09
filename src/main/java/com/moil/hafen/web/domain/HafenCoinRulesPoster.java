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

/**
 * @Author 陈子杰
 * @Description 哈奋币规则海报
 * @Version 1.0.0
 * @Date 2023/12/10 06:16
 */
@Data
@TableName("t_hafen_coin_rules_poster")
@ApiModel("哈奋币规则海报")
public class HafenCoinRulesPoster implements Serializable {
    private static final long serialVersionUID = -9012951446694383399L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("海报内容")
    private String content;
    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;
    private Date createTime;
    private Date modifyTime;
}
