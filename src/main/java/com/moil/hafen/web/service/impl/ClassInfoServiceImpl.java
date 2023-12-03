package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.ClassInfoDao;
import com.moil.hafen.web.domain.ClassInfo;
import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.service.ClassInfoService;
import com.moil.hafen.web.service.ClassScheduleStudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoDao,ClassInfo> implements ClassInfoService {
    @Resource
    private ClassScheduleStudentService classScheduleStudentService;

    @Override
    public IPage<ClassInfo> getPage(QueryRequest request, ClassInfo classInfo) {
        Page<ClassInfo> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page,getCondition(classInfo));
    }

    @Override
    public List<ClassInfo> getClassInfoList(ClassInfo classInfo) {
        return this.list(getCondition(classInfo));
    }

    @Override
    public void updateClassInfo(ClassInfo classInfo) {
        ClassInfo classInfo1 = this.getById(classInfo.getId());
        this.updateById(classInfo);
        if(classInfo1.getTeachingCount() != classInfo.getTeachingCount()){
            classScheduleStudentService.update(new LambdaUpdateWrapper<ClassScheduleStudent>()
                    .set(ClassScheduleStudent::getTeachingCount,classInfo.getTeachingCount())
                    .eq(ClassScheduleStudent::getClassInfoId,classInfo.getId())
                    .isNull(ClassScheduleStudent::getAttendStatus));
        }
    }

    private LambdaQueryWrapper<ClassInfo> getCondition(ClassInfo classInfo){
        LambdaQueryWrapper<ClassInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(classInfo.getName())){
            lambdaQueryWrapper.eq(ClassInfo::getName, classInfo.getName());
        }
        if(StringUtils.isNotBlank(classInfo.getClassTeacher())){
            lambdaQueryWrapper.eq(ClassInfo::getClassTeacher, classInfo.getClassTeacher());
        }
        if(classInfo.getLessonId() != null){
            lambdaQueryWrapper.eq(ClassInfo::getLessonId, classInfo.getLessonId());
        }
        if(classInfo.getCampusId() != null){
            lambdaQueryWrapper.eq(ClassInfo::getCampusId, classInfo.getCampusId());
        }
        return lambdaQueryWrapper;
    }
}
