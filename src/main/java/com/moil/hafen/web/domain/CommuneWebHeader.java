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
@TableName("t_commune_web_header")
@ApiModel("公社微官网头部信息")
public class CommuneWebHeader implements Serializable {
    private static final long serialVersionUID = -8489378604859703358L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("媒体信息")
    private String media;
    @ApiModelProperty("媒体信息地址")
    private String mediaUrl;
    @ApiModelProperty("logo")
    private String logo;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("纬度")
    private String lat;
    @ApiModelProperty("经度")
    private String lng;
    @ApiModelProperty("电话")
    private String telephone;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
