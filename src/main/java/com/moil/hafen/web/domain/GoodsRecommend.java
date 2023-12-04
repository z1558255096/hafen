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
@TableName("t_goods_recommend")
@ApiModel(value = "商品推荐管理")
public class GoodsRecommend implements Serializable {
    private static final long serialVersionUID = 2713728884902689756L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "商品id")
    private Integer goodsId;
    @ApiModelProperty(value = "价格")
    private transient Double price;
    @ApiModelProperty(value = "权重")
    private Integer weight;
    @ApiModelProperty(value = "0上架 1下架")
    private Integer status;//0上架 1下架
    @ApiModelProperty(value = "0正常 1删除")
    private Date createTime;
    @ApiModelProperty(value = "商品名称")
    private transient String goodsName;
    @ApiModelProperty(value = "创建开始时间")
    private transient String createTimeFrom;
    @ApiModelProperty(value = "创建结束时间")
    private transient String createTimeTo;

}
