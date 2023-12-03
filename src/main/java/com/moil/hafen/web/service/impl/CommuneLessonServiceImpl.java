package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CommuneLessonDao;
import com.moil.hafen.web.domain.CommuneLesson;
import com.moil.hafen.web.domain.CommuneLessonAdvance;
import com.moil.hafen.web.domain.LessonCategory;
import com.moil.hafen.web.service.CommuneLessonAdvanceService;
import com.moil.hafen.web.service.CommuneLessonService;
import com.moil.hafen.web.service.LessonCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class CommuneLessonServiceImpl extends ServiceImpl<CommuneLessonDao, CommuneLesson> implements CommuneLessonService {
    @Resource
    private LessonCategoryService lessonCategoryService;
    @Resource
    private CommuneLessonAdvanceService communeLessonAdvanceService;

    @Override
    public IPage<CommuneLesson> getPage(QueryRequest request, CommuneLesson communeLesson) {
        Page<CommuneLesson> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<CommuneLesson> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommuneLesson::getDelFlag,0);
        if(StringUtils.isNotBlank(communeLesson.getName())){
            lambdaQueryWrapper.eq(CommuneLesson::getName, communeLesson.getName());
        }
        if(communeLesson.getStatus() != null){
            lambdaQueryWrapper.eq(CommuneLesson::getStatus, communeLesson.getStatus());
        }
        if(communeLesson.getCategoryId() != null){
            lambdaQueryWrapper.eq(CommuneLesson::getCategoryId, communeLesson.getCategoryId());
        }
        IPage<CommuneLesson> communeLessonIPage = this.page(page, lambdaQueryWrapper);
        List<Integer> categoryIds = communeLessonIPage.getRecords().stream().map(CommuneLesson::getCategoryId).collect(Collectors.toList());
        List<LessonCategory> communeLessonCategoryList = lessonCategoryService.list(new LambdaQueryWrapper<LessonCategory>().in(LessonCategory::getId, categoryIds));
        Map<Integer, String> communeLessonCategoryMap = communeLessonCategoryList.stream().collect(Collectors.toMap(LessonCategory::getId, LessonCategory::getName));
        for (CommuneLesson record : communeLessonIPage.getRecords()) {
            record.setCategoryName(communeLessonCategoryMap.get(record.getCategoryId()));
        }
        return communeLessonIPage;
    }

    @Override
    public TreeMap<String, List<CommuneLesson>> miniList() {
        List<CommuneLesson> communeLessonList = this.list(new LambdaQueryWrapper<CommuneLesson>().eq(CommuneLesson::getStatus, 0)
                .eq(CommuneLesson::getDelFlag, 0));
        Map<Integer, List<CommuneLesson>> listMap = communeLessonList.stream().collect(Collectors.groupingBy(CommuneLesson::getCategoryId));
        List<Integer> categoryIds = listMap.keySet().stream().collect(Collectors.toList());
        List<LessonCategory> list = lessonCategoryService.list(new LambdaQueryWrapper<LessonCategory>().in(LessonCategory::getId, categoryIds).orderByDesc(LessonCategory::getWeight));
        TreeMap<String, List<CommuneLesson>> result = new TreeMap<>();
        for (LessonCategory lessonCategory : list) {
            result.put(lessonCategory.getName(),listMap.get(lessonCategory.getId()));
        }
        return result;
    }

    @Override
    public CommuneLesson detail(Integer id) {
        CommuneLesson communeLesson = this.getById(id);
        List<CommuneLessonAdvance> communeLessonAdvanceList = communeLessonAdvanceService.list(new LambdaQueryWrapper<CommuneLessonAdvance>()
                .eq(CommuneLessonAdvance::getLessonId, id).orderByAsc(CommuneLessonAdvance::getIndex));
        communeLesson.setCommuneLessonAdvanceList(communeLessonAdvanceList);
        return communeLesson;
    }
}
