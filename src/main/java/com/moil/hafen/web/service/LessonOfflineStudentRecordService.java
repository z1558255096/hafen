package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.web.domain.LessonOfflineStudentRecord;

import java.util.List;

public interface LessonOfflineStudentRecordService extends IService<LessonOfflineStudentRecord> {
    List<LessonOfflineStudentRecord> getList(int lessonId);

}
