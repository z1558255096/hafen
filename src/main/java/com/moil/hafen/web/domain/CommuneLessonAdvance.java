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
@TableName("t_commune_lesson_advance")
@ApiModel("公社课程高级设置")
public class CommuneLessonAdvance implements Serializable {
    private static final long serialVersionUID = 3652725988022270653L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("课程id")
    private Integer lessonId;
    @ApiModelProperty("类型： 0单选，1多选，2文本输入")
    private Integer type;//类型： 0单选，1多选，2文本输入
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("选项")
    private String option;
    @ApiModelProperty("权重")
    private Integer index;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//0正常 1删除
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
