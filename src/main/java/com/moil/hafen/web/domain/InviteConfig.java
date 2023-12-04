package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_invite_config")
@ApiModel("邀请规则")
public class InviteConfig implements Serializable {
    private static final long serialVersionUID = -5005975147259968567L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("事件名称")
    private String name;
    @ApiModelProperty("第一")
    private Integer first;
    @ApiModelProperty("第二")
    private Integer second;
    @ApiModelProperty("规则")
    private Integer rules;
}
