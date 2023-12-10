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
@TableName("t_goods_specs")
@ApiModel(value = "商品规格管理")
public class GoodsSpecs implements Serializable {
    private static final long serialVersionUID = 4784090008411901764L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "规格")
    private String specs;
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
    @ApiModelProperty(value = "价格")
    private Double price;
    @ApiModelProperty(value = "库存")
    private Integer stock;
    @ApiModelProperty(value = "0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
