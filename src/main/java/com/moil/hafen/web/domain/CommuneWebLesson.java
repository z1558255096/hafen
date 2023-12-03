package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_web_lesson")
public class CommuneWebLesson implements Serializable {
    private static final long serialVersionUID = 8561593746467735369L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer lessonId;
    private transient String name;
    private transient String cover;
    private transient Double price;
    private Integer baseCount;
    private Integer rate;
    private Integer status;//是否热卖 0否 1是
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
