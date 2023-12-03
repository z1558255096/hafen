package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_student_lesson")
public class StudentLesson implements Serializable {
    private static final long serialVersionUID = -6855743166580085864L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer studentId;
    private Integer customerId;
    private Integer lessonId;
    private Integer type;//课程类型 1科技营 2体适能 3公社课程

    private Date createTime;

    private transient String createTimeFrom;
    private transient String createTimeTo;
}
