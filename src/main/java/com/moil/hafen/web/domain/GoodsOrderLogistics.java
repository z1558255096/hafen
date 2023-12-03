package com.moil.hafen.web.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_goods_order_logistics")
public class GoodsOrderLogistics implements Serializable {

    private static final long serialVersionUID = 7332895700902205461L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
    private Integer orderId;
    private Date createTime;

}
