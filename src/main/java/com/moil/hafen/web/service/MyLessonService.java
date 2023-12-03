package com.moil.hafen.web.service;

import com.moil.hafen.web.vo.MyLessonOfflineVo;
import com.moil.hafen.web.vo.MyLessonOnlineVo;

import java.util.List;

public interface MyLessonService {
    List<MyLessonOnlineVo> getMyLessonOnlineList();

    List<MyLessonOfflineVo> getMyLessonOfflineList();

}
