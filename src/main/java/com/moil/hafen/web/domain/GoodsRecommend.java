package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_goods_recommend")
public class GoodsRecommend implements Serializable {
    private static final long serialVersionUID = 2713728884902689756L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer goodsId;
    private transient Double price;
    private Integer weight;
    private Integer status;//0上架 1下架
    private Date createTime;
    private transient String goodsName;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
