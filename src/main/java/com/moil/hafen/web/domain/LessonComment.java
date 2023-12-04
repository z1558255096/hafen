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
@TableName("t_lesson_comment")
@ApiModel("课程评论")
public class LessonComment implements Serializable {
    private static final long serialVersionUID = 6001547019608769075L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("课程id")
    private Integer lessonId;
    @ApiModelProperty("用户id")
    private Integer customerId;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("分数")
    private Integer score;
    @ApiModelProperty("图片")
    private String img;
    @ApiModelProperty("评论")
    private String description;
    @ApiModelProperty("状态 0上架 1下架")
    private Integer status;
    @ApiModelProperty("课程类型 1科技营 2体适能 3公社课程")
    private Integer type;//课程类型 1科技营 2体适能 3公社课程
    @ApiModelProperty("上课类型 1线上 2线下")
    private Integer item;//上课类型 1线上 2线下

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

}
