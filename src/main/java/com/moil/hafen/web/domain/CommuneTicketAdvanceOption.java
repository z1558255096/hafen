package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_commune_ticket_advance_option")
@ApiModel("公社高级设置选项")
public class CommuneTicketAdvanceOption implements Serializable {
    private static final long serialVersionUID = -7711788557099009809L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("门票id")
    private Integer ticketId;
    @ApiModelProperty("公社高级设置id")
    private Integer advanceId;
    @ApiModelProperty("选项标题")
    private String title;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("排序")
    private Integer index;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//0正常 1删除
}
