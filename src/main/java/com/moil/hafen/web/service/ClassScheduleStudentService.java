package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.domain.Student;

import java.util.List;

public interface ClassScheduleStudentService extends IService<ClassScheduleStudent> {
    List<Student> getMyStudentList(String scheduleId);

    List<ClassScheduleStudent> getMyAttendScheduleList(String scheduleId);
}
