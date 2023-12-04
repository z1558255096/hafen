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
@TableName("t_lesson_audition_student")
@ApiModel("试听学生")
public class LessonAuditionStudent implements Serializable {
    private static final long serialVersionUID = 3752507441098024844L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("试听id")
    private Integer auditionId;
    @ApiModelProperty("学生id")
    private Integer studentId;
    @ApiModelProperty("客户id")
    private Integer customerId;
    @ApiModelProperty("学生姓名")
    private transient String studentName;
    @ApiModelProperty("学生手机号")
    private transient String mobile;
    @ApiModelProperty("状态 0 预约  1取消预约")
    private Integer status;
    @ApiModelProperty("到课状态 到课、请假、旷课")
    private String attendStatus;//到课状态 到课、请假、旷课
    @ApiModelProperty("取消人员")
    private String cancelPerson;
    @ApiModelProperty("取消时间")
    private Date cancelTime;
    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    public LessonAuditionStudent() {
    }

    public LessonAuditionStudent(Integer auditionId, Integer studentId, Integer customerId, Integer status, String attendStatus, Date createTime, Date modifyTime) {
        this.auditionId = auditionId;
        this.studentId = studentId;
        this.customerId = customerId;
        this.status = status;
        this.attendStatus = attendStatus;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
    }
}
