package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_about_us")
public class AboutUs implements Serializable {
    private static final long serialVersionUID = -7405287692942094334L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String content;
}
