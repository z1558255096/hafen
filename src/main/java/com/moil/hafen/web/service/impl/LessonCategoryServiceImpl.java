package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonCategoryDao;
import com.moil.hafen.web.domain.LessonCategory;
import com.moil.hafen.web.service.LessonCategoryService;
import org.springframework.stereotype.Service;

@Service
public class LessonCategoryServiceImpl extends ServiceImpl<LessonCategoryDao,LessonCategory> implements LessonCategoryService {
    @Override
    public IPage<LessonCategory> getPage(QueryRequest request, LessonCategory lessonCategory) {
        Page<LessonCategory> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<LessonCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LessonCategory::getDelFlag,0)
                .eq(LessonCategory::getType,lessonCategory.getType())
                .orderByDesc(LessonCategory::getWeight);
        return this.page(page, lambdaQueryWrapper);
    }
}
