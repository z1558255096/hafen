package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.LessonWebLessonDao;
import com.moil.hafen.web.domain.LessonWebLesson;
import com.moil.hafen.web.service.LessonWebLessonService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LessonWebLessonServiceImpl extends ServiceImpl<LessonWebLessonDao,LessonWebLesson> implements LessonWebLessonService {
    @Override
    public List<LessonWebLesson> getList(LessonWebLesson lessonWebLesson) {
        QueryWrapper<LessonWebLesson> queryWrapper = new QueryWrapper<LessonWebLesson>();
        queryWrapper.eq("campus_id",lessonWebLesson.getCampusId()).eq("t2.status",0).eq("t2.website_status",1);
        if(lessonWebLesson.getMode()==1){//上课形式 1线上 2线下
            queryWrapper.eq("t1.mode",1);
            return this.baseMapper.getOnlineLesson(queryWrapper);
        }else {
            queryWrapper.eq("t1.mode",2);
            return this.baseMapper.getOfflineLesson(queryWrapper);
        }

    }
}
