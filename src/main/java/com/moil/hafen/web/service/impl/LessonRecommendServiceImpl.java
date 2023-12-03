package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonRecommendDao;
import com.moil.hafen.web.domain.LessonRecommend;
import com.moil.hafen.web.service.LessonRecommendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonRecommendServiceImpl extends ServiceImpl<LessonRecommendDao,LessonRecommend> implements LessonRecommendService {
    @Override
    public IPage<LessonRecommend> getPage(QueryRequest request, LessonRecommend lessonRecommend) {
        Page<LessonRecommend> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        QueryWrapper<LessonRecommend> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(lessonRecommend.getTitle())){
            queryWrapper.eq("title", lessonRecommend.getTitle());
        }
        if(lessonRecommend.getStatus() != null){
            queryWrapper.eq("status", lessonRecommend.getStatus());
        }
        if(StringUtils.isNotBlank(lessonRecommend.getLessonName())){
            queryWrapper.eq("lesson_name", lessonRecommend.getLessonName());
        }
        if(StringUtils.isNotBlank(lessonRecommend.getCreateTimeFrom())){
            queryWrapper.ge("create_time",lessonRecommend.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(lessonRecommend.getCreateTimeTo())){
            queryWrapper.le("create_time",lessonRecommend.getCreateTimeTo()+" 23:59:59");
        }
        return this.baseMapper.getPage(page, queryWrapper);
    }

    @Override
    public List<LessonRecommend> getList() {
        QueryWrapper<LessonRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0).orderByDesc("weight");
        return this.baseMapper.getList(queryWrapper);
    }
}
