package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_attend_class_record")
public class AttendClassRecord implements Serializable {
    private static final long serialVersionUID = 8484537514119190068L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String lessonName;
    private Integer lessonId;
    private Date classDate;
    private String classTime;
    private String teacherName;
    private String campusName;
    private String classType;
    private String classStatus;
    private String studentName;
    private Integer studentId;

    private Date createTime;
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
