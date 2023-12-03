package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.LessonRecommend;

import java.util.List;

public interface LessonRecommendService extends IService<LessonRecommend> {
    IPage<LessonRecommend> getPage(QueryRequest request, LessonRecommend lessonRecommend);

    List<LessonRecommend> getList();
}
