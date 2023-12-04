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
@TableName("t_lesson_offline_student_record")
@ApiModel(value = "线下课程学生记录")
public class LessonOfflineStudentRecord implements Serializable {
    private static final long serialVersionUID = -1742202050973727599L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "课程id")
    private Integer lessonId;
    @ApiModelProperty(value = "用户id")
    private Integer customerId;
    @ApiModelProperty(value = "学生id")
    private Integer studentId;
    @ApiModelProperty(value = "课程数量")
    private Integer count;
    @ApiModelProperty(value = "类型 缴费 课消 退费")
    private String type;//缴费 课消 退费

    private Date createTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;

    public LessonOfflineStudentRecord(Integer lessonId, Integer customerId, Integer studentId, Integer count, String type, Date createTime) {
        this.lessonId = lessonId;
        this.customerId = customerId;
        this.studentId = studentId;
        this.count = count;
        this.type = type;
        this.createTime = createTime;
    }
}
