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
@TableName("t_commune_web_banner")
@ApiModel("公社微官网Banner")
public class CommuneWebBanner implements Serializable {
    private static final long serialVersionUID = -2651543878139979704L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("标题")
    private String content;
    @ApiModelProperty("权重")
    private Integer weight;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
