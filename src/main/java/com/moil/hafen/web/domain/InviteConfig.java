package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_invite_config")
public class InviteConfig implements Serializable {
    private static final long serialVersionUID = -5005975147259968567L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("事件名称")
    private String name;
    private Integer first;
    private Integer second;
    private Integer rules;
}
