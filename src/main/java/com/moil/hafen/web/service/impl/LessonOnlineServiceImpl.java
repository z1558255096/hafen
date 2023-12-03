package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonOnlineDao;
import com.moil.hafen.web.domain.LessonCategory;
import com.moil.hafen.web.domain.LessonOnline;
import com.moil.hafen.web.service.LessonCategoryService;
import com.moil.hafen.web.service.LessonOnlineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class LessonOnlineServiceImpl extends ServiceImpl<LessonOnlineDao,LessonOnline> implements LessonOnlineService {
    @Resource
    private LessonCategoryService lessonCategoryService;
    @Override
    public IPage<LessonOnline> getPage(QueryRequest request, LessonOnline lessonOnline) {
        Page<LessonOnline> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.baseMapper.getPage(page, getCondition(lessonOnline));
    }

    @Override
    public List<LessonOnline> getLessonOnlineList(LessonOnline lessonOnline) {
        return this.baseMapper.getList(getCondition(lessonOnline));
    }

    @Override
    public TreeMap<String, List<LessonOnline>> miniList(int type) {
        List<LessonOnline> lessonOnlineList = this.list(new LambdaQueryWrapper<LessonOnline>().eq(LessonOnline::getType, type).eq(LessonOnline::getStatus, 0)
                .eq(LessonOnline::getDelFlag, 0).eq(LessonOnline::getWebsiteStatus, 1));
        Map<Integer, List<LessonOnline>> listMap = lessonOnlineList.stream().collect(Collectors.groupingBy(LessonOnline::getCategoryId));
        List<Integer> categoryIds = listMap.keySet().stream().collect(Collectors.toList());
        List<LessonCategory> list = lessonCategoryService.list(new LambdaQueryWrapper<LessonCategory>().in(LessonCategory::getId, categoryIds).orderByDesc(LessonCategory::getWeight));
        TreeMap<String, List<LessonOnline>> result = new TreeMap<>();
        for (LessonCategory lessonCategory : list) {
            result.put(lessonCategory.getName(),listMap.get(lessonCategory.getId()));
        }
        return result;
    }

    private QueryWrapper<LessonOnline> getCondition(LessonOnline lessonOnline){
        QueryWrapper<LessonOnline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t1.del_flag",0).eq("t1.type",lessonOnline.getType());
        if(StringUtils.isNotBlank(lessonOnline.getName())){
            queryWrapper.eq("t1.name", lessonOnline.getName());
        }
        if(lessonOnline.getStatus()!=null){
            queryWrapper.eq("t1.status", lessonOnline.getStatus());
        }
        if(lessonOnline.getCategoryId()!=null){
            queryWrapper.eq("t1.category_id", lessonOnline.getCategoryId());
        }
        return queryWrapper;
    }
}
