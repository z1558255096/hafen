package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_banner")
public class Banner implements Serializable {
    private static final long serialVersionUID = -5309787068632310954L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String img;
    private Integer type;
    private String url;
    private String content;
    private Integer weight;
    private Integer delFlag;//0正常 1删除
    private Integer status;//0上架 1下架

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
