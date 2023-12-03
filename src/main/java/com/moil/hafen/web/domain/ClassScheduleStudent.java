package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_class_schedule_student")
public class ClassScheduleStudent implements Serializable {
    private static final long serialVersionUID = -3747765749721847817L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer scheduleId;
    private Integer studentId;
    private transient String studentName;
    private transient String mobile;
    private Integer classInfoId;
    private Double teachingCount;
    private String attendStatus;//含几种值：到课、请假、旷课
    private Date createTime;

    public ClassScheduleStudent(Integer scheduleId, Integer studentId, Integer classInfoId, Double teachingCount, Date createTime) {
        this.scheduleId = scheduleId;
        this.studentId = studentId;
        this.classInfoId = classInfoId;
        this.teachingCount = teachingCount;
        this.createTime = createTime;
    }
}
