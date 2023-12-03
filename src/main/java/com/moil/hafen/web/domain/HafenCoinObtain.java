package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_hafen_coin_obtain")
@ApiModel(description="哈奋币规则-获取")
public class HafenCoinObtain implements Serializable {
    private static final long serialVersionUID = -2905123899320874185L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("事件名称")
    private String name;
    @ApiModelProperty("获得哈奋币")
    private Integer obtNumber;
    @ApiModelProperty("补充规则")
    private Integer rules;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
