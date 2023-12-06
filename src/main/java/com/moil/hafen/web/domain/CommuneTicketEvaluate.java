package com.moil.hafen.web.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_ticket_evaluate")
@ApiModel("门票评价信息")
public class CommuneTicketEvaluate implements Serializable {
    private static final long serialVersionUID = 5876645447159453361L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("门票Id")
    private String ticketId;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户昵称")
    private transient String nickName;
    @ApiModelProperty("手机号码")
    private transient String phone;
    @ApiModelProperty("评分")
    private Integer score;
    @ApiModelProperty("描述")
    private String detail;
    @ApiModelProperty("图片")
    private String fileIds;
    @ApiModelProperty("上架状态 0上架 1下架")
    private Integer status;//上架状态 0上架 1下架
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
}
