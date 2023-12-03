package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_web_header")
public class CommuneWebHeader implements Serializable {
    private static final long serialVersionUID = -8489378604859703358L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String media;
    private String logo;
    private String description;
    private String address;
    private String telephone;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
