package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_notice")
public class Notice implements Serializable {
    private static final long serialVersionUID = 1023144742224360544L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    private String img;
    private String showTo;
    private String content;
    private Integer mainStatus;//首页展示 0不展示;1展示
    private Integer remind;//强提醒状态 0不提醒 1强提醒
    private Integer delFlag;//0正常 1删除

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
