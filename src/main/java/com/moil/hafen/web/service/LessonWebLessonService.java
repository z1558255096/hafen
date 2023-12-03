package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.web.domain.LessonWebLesson;

import java.util.List;

public interface LessonWebLessonService extends IService<LessonWebLesson> {
    List<LessonWebLesson> getList(LessonWebLesson lessonWebLesson);
}
