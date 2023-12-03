package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.CommuneWebLessonDao;
import com.moil.hafen.web.domain.CommuneWebLesson;
import com.moil.hafen.web.service.CommuneWebLessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommuneWebLessonServiceImpl extends ServiceImpl<CommuneWebLessonDao,CommuneWebLesson> implements CommuneWebLessonService {
    @Override
    public List<CommuneWebLesson> getList() {
        return this.baseMapper.getList();
    }
}
