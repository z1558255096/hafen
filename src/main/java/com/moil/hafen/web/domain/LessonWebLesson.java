package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_web_lesson")
public class LessonWebLesson implements Serializable {
    private static final long serialVersionUID = -6504203656897389459L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer campusId;
    private Integer lessonId;
    private transient String cover;
    private transient String name;
    private transient Double price;
    private transient Double actualPrice;
    private Integer type;//课程类型 1科技营 2体适能
    private Integer mode;//上课形式 1线上 2线下
    private String description;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;


}
