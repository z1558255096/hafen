package com.moil.hafen.web.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_goods")
@ApiModel(value = "商品管理")
public class Goods implements Serializable {
    private static final long serialVersionUID = 2975984685553527870L;
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "类别id")
    private Integer categoryId;
    @ApiModelProperty(value = "商品主图")
    private String mainImg;
    @ApiModelProperty(value = "商品轮播图")
    private String img;
    @ApiModelProperty(value = "商品详情")
    private String detail;
    @ApiModelProperty(value = "权重")
    private Integer weight;
    @ApiModelProperty(value = "0上架 1下架")
    private Integer status;//
    @ApiModelProperty(value = "0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;
    @ApiModelProperty(value = "创建开始时间")
    private transient String createTimeFrom;
    @ApiModelProperty(value = "创建结束时间")
    private transient String createTimeTo;
    @ApiModelProperty(value = "商品规格")
    private transient List<GoodsSpecs> goodsSpecsList;
    @ApiModelProperty(value = "商品类别名称")
    private transient String categoryName;
    @ApiModelProperty(value = "商品价格")
    private transient String priceStr;
    @ApiModelProperty(value = "商品价格")
    private transient Double price;

}
