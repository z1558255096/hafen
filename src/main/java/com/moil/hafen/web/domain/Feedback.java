package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_feedback")
public class Feedback implements Serializable {
    private static final long serialVersionUID = 547214194047431257L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer customerId;
    private String nickname;
    private String mobile;
    private String type;
    private String reply;
    private String img;
    private Integer status;//状态 0未处理 1已处理

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
