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

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
