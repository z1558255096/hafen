package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonOnlineChapterDao;
import com.moil.hafen.web.domain.LessonOnlineChapter;
import com.moil.hafen.web.service.LessonOnlineChapterService;
import org.springframework.stereotype.Service;

@Service
public class LessonOnlineChapterServiceImpl extends ServiceImpl<LessonOnlineChapterDao, LessonOnlineChapter> implements LessonOnlineChapterService {
    @Override
    public IPage<LessonOnlineChapter> getPage(QueryRequest request, LessonOnlineChapter lessonOnlineChapter) {
        Page<LessonOnlineChapter> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<LessonOnlineChapter> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LessonOnlineChapter::getDelFlag,0)
                .eq(LessonOnlineChapter::getLessonId,lessonOnlineChapter.getLessonId())
                .orderByAsc(LessonOnlineChapter::getSort);
        return this.page(page, lambdaQueryWrapper);

    }

    @Override
    public void updateSort(Integer id, String sortStr) {
        LessonOnlineChapter chapter = this.getById(id);
        int sort = chapter.getSort();
        LessonOnlineChapter chapter1;
        if (sortStr.equals("down")) {
            chapter1 = this.getOne(new LambdaQueryWrapper<LessonOnlineChapter>().eq(LessonOnlineChapter::getLessonId, chapter.getLessonId())
                    .eq(LessonOnlineChapter::getDelFlag, 0).lt(LessonOnlineChapter::getSort, sort)
                    .orderByDesc(LessonOnlineChapter::getSort).last("limit 0,1"));
        } else {
            chapter1 = this.getOne(new LambdaQueryWrapper<LessonOnlineChapter>().eq(LessonOnlineChapter::getLessonId, chapter.getLessonId())
                    .eq(LessonOnlineChapter::getDelFlag, 0).gt(LessonOnlineChapter::getSort, sort)
                    .orderByAsc(LessonOnlineChapter::getSort).last("limit 0,1"));
        }
        chapter.setSort(chapter1.getSort());
        chapter1.setSort(sort);
        this.updateById(chapter);
        this.updateById(chapter1);
    }
}
