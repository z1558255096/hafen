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
 * @Description 哈奋币规则-使用
 * @Version 1.0.0
 * @Date 2023/12/10 06:59
 */
@Data
@TableName("t_hafen_coin_use")
@ApiModel(description="哈奋币规则-使用")
public class HafenCoinUse implements Serializable {
    private static final long serialVersionUID = -2583294882208825937L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("事件名称")
    private String name;
    @ApiModelProperty("兑换比例")
    private Integer number;
    @ApiModelProperty("补充规则")
    private Integer rules;
    @ApiModelProperty("抵扣上限")
    private Integer upperLimit;

    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
