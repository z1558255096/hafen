package com.moil.hafen.web.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TeacherScheduleVo {
    private Integer scheduleId;
    private Date classDate;
    private String teacherName;
    private String classTime;
    private String classType;
    private Integer studentCount;
    private Integer attendStudentCount;
}
