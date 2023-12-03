package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_article")
public class Article implements Serializable {
    private static final long serialVersionUID = -4946450841621286737L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private Integer categoryId;
    private Integer recommendStatus;//推荐 0不推荐 1推荐
    private String cover;
    private String content;
    private Integer weight;
    private Integer likeCount;
    private Integer collectCount;
    private Integer delFlag;//0正常 1删除
    private Integer status;//0上架 1下架

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
