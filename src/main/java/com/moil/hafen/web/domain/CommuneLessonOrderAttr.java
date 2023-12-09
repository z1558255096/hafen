package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_commune_lesson_order_attr")
@ApiModel("公社课程订单属性")
public class CommuneLessonOrderAttr implements Serializable {
    private static final long serialVersionUID = 3353192342594159141L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("订单id")
    private Integer orderId;
    @ApiModelProperty("属性名称")
    private String advanceName;
    @ApiModelProperty("属性值")
    private String advanceValue;
    @ApiModelProperty("课程高级设置id")
    private Integer advanceId;

}
