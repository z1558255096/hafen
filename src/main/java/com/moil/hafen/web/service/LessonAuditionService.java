package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonAudition;

import java.util.List;
import java.util.Map;

public interface LessonAuditionService extends IService<LessonAudition> {
    List<LessonAudition> getLessonAuditionList(LessonAudition lessonAudition);

    IPage<LessonAudition> getPage(QueryRequest request, LessonAudition lessonAudition);

    void addLessonAudition(LessonAudition lessonAudition) throws FebsException;

    Map<String,List<LessonAudition>> getAuditionList(int type, String date,int campusId);

    IPage<LessonAudition> getMyAudition(QueryRequest request);

}
