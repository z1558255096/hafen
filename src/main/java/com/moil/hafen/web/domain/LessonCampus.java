package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_lesson_campus")
public class LessonCampus implements Serializable {
    private static final long serialVersionUID = 7107102711568912919L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer lessonId;
    private Integer campusId;
    private Integer type;//课程类型 1科技营 2体适能 3公社课程

    public LessonCampus(Integer lessonId, Integer campusId, Integer type) {
        this.lessonId = lessonId;
        this.campusId = campusId;
        this.type = type;
    }

    public LessonCampus() {
    }
}
