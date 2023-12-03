package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_audition_student")
public class LessonAuditionStudent implements Serializable {
    private static final long serialVersionUID = 3752507441098024844L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer auditionId;
    private Integer studentId;
    private Integer customerId;
    private transient String studentName;
    private transient String mobile;
    private Integer status;//状态 0 预约  1取消预约
    private String attendStatus;//到课状态 到课、请假、旷课
    private String cancelPerson;
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
