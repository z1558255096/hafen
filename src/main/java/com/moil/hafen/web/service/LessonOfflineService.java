package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.LessonOffline;

import java.util.List;
import java.util.TreeMap;

public interface LessonOfflineService extends IService<LessonOffline> {
    IPage<LessonOffline> getPage(QueryRequest request, LessonOffline lessonOffline);

    List<LessonOffline> getLessonOfflineList(LessonOffline lessonOffline);

    void addLesson(LessonOffline lessonOffline);

    LessonOffline detail(Integer id);

    void updateLesson(LessonOffline lessonOffline);

    TreeMap<String, List<LessonOffline>> miniList(int type, int campusId);
}
