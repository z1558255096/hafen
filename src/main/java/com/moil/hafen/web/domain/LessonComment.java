package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_comment")
public class LessonComment implements Serializable {
    private static final long serialVersionUID = 6001547019608769075L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer lessonId;
    private Integer customerId;
    private String nickName;
    private String mobile;
    private Integer score;
    private String img;
    private String description;
    private Integer status;//0上架 1下架
    private Integer type;//课程类型 1科技营 2体适能 3公社课程
    private Integer item;//上课类型 1线上 2线下

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
