package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.LessonOnline;

import java.util.List;
import java.util.TreeMap;

public interface LessonOnlineService extends IService<LessonOnline> {
    IPage<LessonOnline> getPage(QueryRequest request, LessonOnline lessonOnline);

    List<LessonOnline> getLessonOnlineList(LessonOnline lessonOnline);

    TreeMap<String, List<LessonOnline>> miniList(int type);
}
