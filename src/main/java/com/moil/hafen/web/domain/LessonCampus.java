package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_lesson_campus")
@ApiModel("课程校区")
public class LessonCampus implements Serializable {
    private static final long serialVersionUID = 7107102711568912919L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("课程id")
    private Integer lessonId;
    @ApiModelProperty("校区id")
    private Integer campusId;
    @ApiModelProperty("课程类型 1科技营 2体适能 3公社课程")
    private Integer type;
    @ApiModelProperty(value = "0正常 1删除")
    private Integer delFlag;//0正常 1删除

    public LessonCampus(Integer lessonId, Integer campusId, Integer type) {
        this.lessonId = lessonId;
        this.campusId = campusId;
        this.type = type;
    }

    public LessonCampus() {
    }
}
