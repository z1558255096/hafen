package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.web.dao.ClassScheduleDao;
import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.ClassScheduleStudentService;
import com.moil.hafen.web.service.LessonAuditionStudentService;
import com.moil.hafen.web.service.TeacherScheduleService;
import com.moil.hafen.web.vo.TeacherScheduleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherScheduleServiceImpl implements TeacherScheduleService {
    @Resource
    private ClassScheduleDao classScheduleDao;
    @Resource
    private LessonAuditionStudentService lessonAuditionStudentService;
    @Resource
    private ClassScheduleStudentService classScheduleStudentService;
    @Override
    public List<TeacherScheduleVo> getMySchedule(String classDate) {
        int customerId = JWTUtil.getCurrentCustomerId();
        List<TeacherScheduleVo> mySchedule = classScheduleDao.getMySchedule(classDate, customerId);
        return mySchedule;
    }

    @Override
    public List<Student> getMyStudentList(String scheduleId, String classType, int status) {
        if(classType.equals("试听日程")){
            List<Student> list = lessonAuditionStudentService.getStudentList(scheduleId,status);
            return list;
        }else {
            List<Student> list = classScheduleStudentService.getMyStudentList(scheduleId);
            return list;
        }
    }

    @Override
    public List<ClassScheduleStudent> getMyAttendScheduleList(String scheduleId) {
        return classScheduleStudentService.getMyAttendScheduleList(scheduleId);
    }

    @Override
    public List<LessonAuditionStudent> getMyAttendAuditionList(String auditionId) {
        return lessonAuditionStudentService.getAuditionList(auditionId);
    }

    @Override
    public void signIn(String scheduleId, String classType, Integer studentId, String attendStatus) {
        if(classType.equals("试听日程")){
            lessonAuditionStudentService.update(new LambdaUpdateWrapper<LessonAuditionStudent>().set(LessonAuditionStudent::getAttendStatus, attendStatus)
                    .eq(LessonAuditionStudent::getAuditionId,scheduleId).eq(LessonAuditionStudent::getStudentId,studentId));
        }else {
            classScheduleStudentService.update(new LambdaUpdateWrapper<ClassScheduleStudent>().set(ClassScheduleStudent::getAttendStatus,attendStatus)
                    .eq(ClassScheduleStudent::getScheduleId,scheduleId).eq(ClassScheduleStudent::getStudentId,studentId));
        }
    }
}
