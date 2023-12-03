package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_lesson_campus_price")
public class LessonCampusPrice implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer lessonId;
    private Integer campusId;

    private String campusName;
    private Integer lessonCount;
    private Double price;
    private Double actualPrice;

    private Integer type;//课程类型 1科技营 2体适能 3公社课程

    public LessonCampusPrice(Integer lessonId, Integer campusId, String campusName, Integer lessonCount, Double price, Double actualPrice, Integer type) {
        this.lessonId = lessonId;
        this.campusId = campusId;
        this.campusName = campusName;
        this.lessonCount = lessonCount;
        this.price = price;
        this.actualPrice = actualPrice;
        this.type = type;
    }

    public LessonCampusPrice() {
    }
}
