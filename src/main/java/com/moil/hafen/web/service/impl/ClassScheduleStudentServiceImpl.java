package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.ClassScheduleStudentDao;
import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.ClassScheduleStudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassScheduleStudentServiceImpl extends ServiceImpl<ClassScheduleStudentDao, ClassScheduleStudent> implements ClassScheduleStudentService {
    @Override
    public List<Student> getMyStudentList(String scheduleId) {
        return this.baseMapper.getMyStudentList(scheduleId);
    }

    @Override
    public List<ClassScheduleStudent> getMyAttendScheduleList(String scheduleId) {
        return this.baseMapper.getMyAttendScheduleList(scheduleId);
    }
}
