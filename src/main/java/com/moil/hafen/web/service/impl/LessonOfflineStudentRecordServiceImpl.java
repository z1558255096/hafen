package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.web.dao.LessonOfflineStudentRecordDao;
import com.moil.hafen.web.domain.LessonOfflineStudentRecord;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.LessonOfflineStudentRecordService;
import com.moil.hafen.web.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LessonOfflineStudentRecordServiceImpl extends ServiceImpl<LessonOfflineStudentRecordDao, LessonOfflineStudentRecord> implements LessonOfflineStudentRecordService {
    @Resource
    private StudentService studentService;
    @Override
    public List<LessonOfflineStudentRecord> getList(int lessonId) {
        int customerId = JWTUtil.getCurrentCustomerId();
        Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getCustomerId, customerId)
                .orderByDesc(Student::getActiveStatus).orderByAsc(Student::getCreateTime).last("limit 1"));
        return this.list(new LambdaQueryWrapper<LessonOfflineStudentRecord>().eq(LessonOfflineStudentRecord::getStudentId,student.getId())
                .eq(LessonOfflineStudentRecord::getLessonId,lessonId).orderByDesc(LessonOfflineStudentRecord::getCreateTime));
    }
}
