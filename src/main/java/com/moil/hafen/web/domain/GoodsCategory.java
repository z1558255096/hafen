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
@TableName("t_goods_category")
@ApiModel(value = "商品类目管理")
public class GoodsCategory implements Serializable {
    private static final long serialVersionUID = 2374855791508747475L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "类目名称")
    private String name;
    @ApiModelProperty(value = "权重")
    private Integer weight;
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
    @ApiModelProperty(value = "商品数量")
    private transient int count;
}
