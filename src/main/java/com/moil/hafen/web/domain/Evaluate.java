package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_evaluate")
public class Evaluate implements Serializable {
    private static final long serialVersionUID = -3856613070915730874L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer customerId;
    private Integer lessonId;
    private String nickName;
    private String avatar;
    private Integer source;//1线上课程 2线下课程,3公社门票
    private Integer star;
    private String content;
    private String img;

    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
