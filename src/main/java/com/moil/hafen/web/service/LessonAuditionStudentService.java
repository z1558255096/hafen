package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.domain.Student;

import java.util.List;

public interface LessonAuditionStudentService extends IService<LessonAuditionStudent> {
    IPage<LessonAuditionStudent> getPage(QueryRequest request, LessonAuditionStudent lessonAuditionStudent);

    void reservation(Integer auditionId, List<Integer> studentIds);

    void cancel(Integer auditionId, List<Integer> studentIds);

    List<Student> getStudentList(String auditionId, int status);

    List<LessonAuditionStudent> getAuditionList(String auditionId);
}
