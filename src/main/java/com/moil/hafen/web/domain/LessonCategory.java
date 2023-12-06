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
@TableName("t_lesson_category")
@ApiModel("课程分类")
public class LessonCategory implements Serializable {
    private static final long serialVersionUID = -4170422773961779058L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("课程名称")
    private String name;
    @ApiModelProperty("权重")
    private Integer weight;
    @ApiModelProperty("课程类型 1科技营 2体适能 3公社课程")
    private Integer type;
    @ApiModelProperty("课程数量")
    private Integer count;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
