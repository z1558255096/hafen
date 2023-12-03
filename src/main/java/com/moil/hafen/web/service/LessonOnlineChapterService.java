package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.LessonOnlineChapter;

public interface LessonOnlineChapterService extends IService<LessonOnlineChapter> {
    IPage<LessonOnlineChapter> getPage(QueryRequest request, LessonOnlineChapter lessonOnlineChapter);

    void updateSort(Integer id, String sortStr);
}
