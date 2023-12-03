package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.web.domain.CommuneWebLesson;

import java.util.List;

public interface CommuneWebLessonService extends IService<CommuneWebLesson> {
    List<CommuneWebLesson> getList();

}
