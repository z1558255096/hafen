package com.moil.hafen.web.service;

import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.vo.TeacherScheduleVo;

import java.util.List;

public interface TeacherScheduleService {
    List<TeacherScheduleVo> getMySchedule(String classDate);

    List<Student> getMyStudentList(String scheduleId, String classType, int status);

    List<ClassScheduleStudent> getMyAttendScheduleList(String scheduleId);

    List<LessonAuditionStudent> getMyAttendAuditionList(String auditionId);

    void signIn(String scheduleId, String classType, Integer studentId, String attendStatus);
}
