package com.moil.hafen.web.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = 2975984685553527870L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer categoryId;
    private String mainImg;
    private String img;
    private String detail;
    private Integer weight;
    private Integer status;//0上架 1下架
    private Integer delFlag;//0正常 1删除

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient List<GoodsSpecs> goodsSpecsList;
    private transient String categoryName;
    private transient String priceStr;
    private transient Double price;

}
