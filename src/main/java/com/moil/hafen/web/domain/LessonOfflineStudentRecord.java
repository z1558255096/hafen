package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_lesson_offline_student_record")
public class LessonOfflineStudentRecord implements Serializable {
    private static final long serialVersionUID = -1742202050973727599L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer lessonId;
    private Integer customerId;
    private Integer studentId;
    private Integer count;
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
