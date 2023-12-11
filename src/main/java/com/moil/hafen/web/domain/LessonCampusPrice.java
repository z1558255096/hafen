package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_lesson_campus_price")
@ApiModel("课程校区价格")
public class LessonCampusPrice implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("课程id")
    private Integer lessonId;
    @ApiModelProperty("校区id")
    private Integer campusId;
    @ApiModelProperty("校区名称")
    private String campusName;
    @ApiModelProperty("课时数")
    private Integer lessonCount;
    @ApiModelProperty("价格")
    private Double price;
    @ApiModelProperty("实际价格")
    private Double actualPrice;
    @ApiModelProperty("课程类型 1科技营 2体适能 3公社课程")
    private Integer type;
    @ApiModelProperty(value = "0正常 1删除")
    private Integer delFlag;//0正常 1删除

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
