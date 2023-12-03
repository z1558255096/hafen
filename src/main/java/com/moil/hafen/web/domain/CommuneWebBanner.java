package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_web_banner")
public class CommuneWebBanner implements Serializable {
    private static final long serialVersionUID = -2651543878139979704L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String img;
    private String content;
    private Integer weight;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
