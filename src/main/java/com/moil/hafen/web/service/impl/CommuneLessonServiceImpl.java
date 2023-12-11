package com.moil.hafen.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        if (StringUtils.isNotBlank(communeLesson.getName())) {
            lambdaQueryWrapper.eq(CommuneLesson::getName, communeLesson.getName());
        }
        if (communeLesson.getStatus() != null) {
            lambdaQueryWrapper.eq(CommuneLesson::getStatus, communeLesson.getStatus());
        }
        if (communeLesson.getCategoryId() != null) {
            lambdaQueryWrapper.eq(CommuneLesson::getCategoryId, communeLesson.getCategoryId());
        }
        lambdaQueryWrapper.eq(CommuneLesson::getDelFlag,0).orderByDesc(CommuneLesson::getCreateTime);
        IPage<CommuneLesson> communeLessonIPage = this.page(page, lambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(communeLessonIPage.getRecords())){
            List<Integer> categoryIds = communeLessonIPage.getRecords().stream().map(CommuneLesson::getCategoryId).collect(Collectors.toList());
            List<LessonCategory> communeLessonCategoryList = lessonCategoryService.list(new LambdaQueryWrapper<LessonCategory>().in(LessonCategory::getId, categoryIds));
            Map<Integer, String> communeLessonCategoryMap = communeLessonCategoryList.stream().collect(Collectors.toMap(LessonCategory::getId, LessonCategory::getName));
            for (CommuneLesson record : communeLessonIPage.getRecords()) {
                record.setCategoryName(communeLessonCategoryMap.get(record.getCategoryId()));
            }
        }
        return communeLessonIPage;
    }

    @Override
    public TreeMap<String, List<CommuneLesson>> miniList() {
        //获取公社课程列表
        List<CommuneLesson> communeLessonList = this.list(new LambdaQueryWrapper<CommuneLesson>().eq(CommuneLesson::getStatus, 0)
                .eq(CommuneLesson::getDelFlag, 0));
        //按照类别分组
        Map<Integer, List<CommuneLesson>> listMap = communeLessonList.stream().collect(Collectors.groupingBy(CommuneLesson::getCategoryId));
        List<Integer> categoryIds = new ArrayList<>(listMap.keySet());
        //获取类别列表
        List<LessonCategory> list = lessonCategoryService.list(new LambdaQueryWrapper<LessonCategory>().in(LessonCategory::getId, categoryIds).orderByDesc(LessonCategory::getWeight));
        TreeMap<String, List<CommuneLesson>> result = new TreeMap<>();
        for (LessonCategory lessonCategory : list) {
            result.put(lessonCategory.getName(), listMap.get(lessonCategory.getId()));
        }
        return result;
    }

    @Override
    public CommuneLesson detail(Integer id) {
        CommuneLesson communeLesson = this.getById(id);
        //获取公社课程高级设置
        List<CommuneLessonAdvance> communeLessonAdvanceList = communeLessonAdvanceService.list(new LambdaQueryWrapper<CommuneLessonAdvance>()
                .eq(CommuneLessonAdvance::getLessonId, id)
                .eq(CommuneLessonAdvance::getDelFlag, 0)
                .orderByAsc(CommuneLessonAdvance::getSort));
        communeLesson.setCommuneLessonAdvanceList(communeLessonAdvanceList);
        return communeLesson;
    }

    /**
     * 删除公社课程信息和公社课程高级设置
     *
     * @param id id
     *
     * @return {@link Boolean}
     */
    @Override
    @Transactional
    public Boolean delete(Integer id) {
        //删除公社课程信息
        this.lambdaUpdate().eq(CommuneLesson::getId, id).set(CommuneLesson::getDelFlag, 1).update();
        //删除公社课程高级设置
        return communeLessonAdvanceService.lambdaUpdate().eq(CommuneLessonAdvance::getLessonId, id).set(CommuneLessonAdvance::getDelFlag, 1).update();
    }
}
