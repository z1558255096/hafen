package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_goods_category")
public class GoodsCategory implements Serializable {
    private static final long serialVersionUID = 2374855791508747475L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer weight;
    private Integer delFlag;//0正常 1删除

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
    private transient int count;
}
