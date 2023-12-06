package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.CommuneLesson;

import java.util.List;
import java.util.TreeMap;

public interface CommuneLessonService extends IService<CommuneLesson> {
    IPage<CommuneLesson> getPage(QueryRequest request, CommuneLesson communeLesson);

    TreeMap<String, List<CommuneLesson>> miniList();

    CommuneLesson detail(Integer id);

    Boolean delete(Integer id);
}
