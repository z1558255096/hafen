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
@TableName("t_hafen_coin")
@ApiModel(value = "哈奋币管理")
public class HafenCoin implements Serializable {
    private static final long serialVersionUID = -1071613475047719826L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer customerId;
    @ApiModelProperty(value = "哈奋币")
    private Integer coin;
    //TODO 不确定
    @ApiModelProperty(value = "0收入 1支出")
    private Integer type;
    @ApiModelProperty(value = "来源")
    private String source;
    @ApiModelProperty(value = "总哈奋币")
    private transient Integer totalCoin;
    @ApiModelProperty(value = "备注")
    private String remark;
    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient Integer sumCoin;
}
