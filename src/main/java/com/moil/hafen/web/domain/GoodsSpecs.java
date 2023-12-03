package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_goods_specs")
public class GoodsSpecs implements Serializable {
    private static final long serialVersionUID = 4784090008411901764L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String specs;
    private Integer goodsId;
    private Double price;
    private Integer stock;
    private Integer delFlag;//0正常 1删除

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
