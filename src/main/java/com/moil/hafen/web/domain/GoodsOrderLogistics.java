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
@TableName("t_goods_order_logistics")
@ApiModel(value = "商品订单物流管理")
public class GoodsOrderLogistics implements Serializable {

    private static final long serialVersionUID = 7332895700902205461L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "订单id")
    private Integer orderId;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
