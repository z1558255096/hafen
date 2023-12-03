package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonOfflineDao;
import com.moil.hafen.web.domain.LessonCampus;
import com.moil.hafen.web.domain.LessonCampusPrice;
import com.moil.hafen.web.domain.LessonCategory;
import com.moil.hafen.web.domain.LessonOffline;
import com.moil.hafen.web.service.LessonCampusPriceService;
import com.moil.hafen.web.service.LessonCampusService;
import com.moil.hafen.web.service.LessonCategoryService;
import com.moil.hafen.web.service.LessonOfflineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LessonOfflineServiceImpl extends ServiceImpl<LessonOfflineDao,LessonOffline> implements LessonOfflineService {
    @Resource
    private LessonCampusService lessonCampusService;
    @Resource
    private LessonCampusPriceService lessonCampusPriceService;
    @Resource
    private LessonCategoryService lessonCategoryService;

    @Override
    public IPage<LessonOffline> getPage(QueryRequest request, LessonOffline lessonOffline) {
        Page<LessonOffline> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.baseMapper.getPage(page, getCondition(lessonOffline));
    }

    @Override
    public List<LessonOffline> getLessonOfflineList(LessonOffline lessonOffline) {
        return this.baseMapper.getList(getCondition(lessonOffline));
    }

    @Override
    public void addLesson(LessonOffline lessonOffline) {
        lessonOffline.setCreateTime(new Date());
        lessonOffline.setModifyTime(new Date());
        lessonOffline.setDelFlag(0);
        this.save(lessonOffline);
        addLessonCampus(lessonOffline);
        addLessonCampusPrice(lessonOffline);
    }

    @Override
    public LessonOffline detail(Integer id) {
        LessonOffline lessonOffline = this.getById(id);
        if(lessonOffline.getCampusType()==1) {
            List<LessonCampus> lessonCampusList = lessonCampusService.list(new LambdaQueryWrapper<LessonCampus>().eq(LessonCampus::getLessonId, id));
            List<Integer> list = lessonCampusList.stream().map(LessonCampus::getCampusId).collect(Collectors.toList());
            lessonOffline.setCampusList(list);
        }
        return lessonOffline;
    }

    @Override
    public void updateLesson(LessonOffline lessonOffline) {
        lessonOffline.setModifyTime(new Date());
        this.updateById(lessonOffline);
        addLessonCampus(lessonOffline);
        addLessonCampusPrice(lessonOffline);
    }

    @Override
    public TreeMap<String, List<LessonOffline>> miniList(int type,int campusId) {
        List<LessonOffline> lessonOfflineList = this.list(new LambdaQueryWrapper<LessonOffline>().eq(LessonOffline::getType, type).eq(LessonOffline::getStatus, 0)
                .eq(LessonOffline::getDelFlag, 0).eq(LessonOffline::getWebsiteStatus, 1));

        for (LessonOffline lessonOffline : lessonOfflineList) {
            LessonCampusPrice campusPrice = lessonCampusPriceService.getOne(new LambdaQueryWrapper<LessonCampusPrice>().eq(LessonCampusPrice::getLessonId, lessonOffline.getId())
                    .and(query -> query.eq(LessonCampusPrice::getCampusId, campusId).or().eq(LessonCampusPrice::getCampusId, 0))
                    .orderByAsc(LessonCampusPrice::getActualPrice).last("limit 1"));
            lessonOffline.setPrice(campusPrice.getPrice());
            lessonOffline.setActualPrice(campusPrice.getActualPrice());
        }

        Map<Integer, List<LessonOffline>> listMap = lessonOfflineList.stream().collect(Collectors.groupingBy(LessonOffline::getCategoryId));
        List<Integer> categoryIds = listMap.keySet().stream().collect(Collectors.toList());
        List<LessonCategory> list = lessonCategoryService.list(new LambdaQueryWrapper<LessonCategory>().in(LessonCategory::getId, categoryIds).orderByDesc(LessonCategory::getWeight));
        TreeMap<String, List<LessonOffline>> result = new TreeMap<>();
        for (LessonCategory lessonCategory : list) {
            result.put(lessonCategory.getName(),listMap.get(lessonCategory.getId()));
        }
        return result;

    }

    private void addLessonCampus(LessonOffline lessonOffline){
        lessonCampusService.remove(new LambdaUpdateWrapper<LessonCampus>().eq(LessonCampus::getLessonId,lessonOffline.getId()));
        if(lessonOffline.getCampusType()==1){
            List<LessonCampus> campusList = new ArrayList<>();
            for (Integer campusId : lessonOffline.getCampusList()) {
                LessonCampus lessonCampus = new LessonCampus(lessonOffline.getId(), campusId, lessonOffline.getType());
                campusList.add(lessonCampus);
            }
            lessonCampusService.saveBatch(campusList);
        }
    }
    private void addLessonCampusPrice(LessonOffline lessonOffline){
        lessonCampusPriceService.remove(new LambdaUpdateWrapper<LessonCampusPrice>().eq(LessonCampusPrice::getLessonId,lessonOffline.getId()));
        lessonCampusPriceService.saveOrUpdateBatch(lessonOffline.getLessonCampusPriceList());
    }
    private QueryWrapper<LessonOffline> getCondition(LessonOffline lessonOffline){
        QueryWrapper<LessonOffline> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t1.del_flag",0).eq("t1.type",lessonOffline.getType());
        if(StringUtils.isNotBlank(lessonOffline.getName())){
            queryWrapper.eq("t1.name", lessonOffline.getName());
        }
        if(lessonOffline.getStatus()!=null){
            queryWrapper.eq("t1.status", lessonOffline.getStatus());
        }
        if(lessonOffline.getCategoryId()!=null){
            queryWrapper.eq("t1.category_id", lessonOffline.getCategoryId());
        }
        return queryWrapper;
    }
}
