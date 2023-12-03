package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.ClassStudentDao;
import com.moil.hafen.web.domain.ClassStudent;
import com.moil.hafen.web.service.ClassStudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ClassStudentServiceImpl extends ServiceImpl<ClassStudentDao,ClassStudent> implements ClassStudentService {
    @Override
    public IPage<ClassStudent> getPage(QueryRequest request, ClassStudent classStudent) {
        Page<ClassStudent> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<ClassStudent> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(classStudent.getStudentName())){
            lambdaQueryWrapper.eq(ClassStudent::getStudentName, classStudent.getStudentName());
        }
        lambdaQueryWrapper.eq(ClassStudent::getClassId,classStudent.getClassId());
        return this.baseMapper.getPage(page, lambdaQueryWrapper);
    }
}
