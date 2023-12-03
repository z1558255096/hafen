package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonCommentDao;
import com.moil.hafen.web.domain.LessonComment;
import com.moil.hafen.web.service.LessonCommentService;
import org.springframework.stereotype.Service;

@Service
public class LessonCommentServiceImpl extends ServiceImpl<LessonCommentDao, LessonComment> implements LessonCommentService {
    @Override
    public IPage<LessonComment> getPage(QueryRequest request, LessonComment lessonComment) {
        Page<LessonComment> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<LessonComment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LessonComment::getLessonId, lessonComment.getLessonId())
                .eq(LessonComment::getType,lessonComment.getType())
                .eq(LessonComment::getItem,lessonComment.getItem())
                .orderByDesc(LessonComment::getCreateTime);
        return this.page(page, lambdaQueryWrapper);
    }
}
