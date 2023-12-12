package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_web_lesson")
@ApiModel("公社微官网课程")
public class CommuneWebLesson implements Serializable {
    private static final long serialVersionUID = 8561593746467735369L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("课程id")
    private Integer lessonId;
    @ApiModelProperty("课程名称")
    private transient String name;
    @ApiModelProperty("课程封面")
    private transient String cover;
    @ApiModelProperty("课程价格")
    private transient Double price;
    @ApiModelProperty("课程数量")
    private Integer baseCount;
    private Integer rate;
    @ApiModelProperty("是否热卖 0否 1是")
    private Integer status;//是否热卖 0否 1是
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
