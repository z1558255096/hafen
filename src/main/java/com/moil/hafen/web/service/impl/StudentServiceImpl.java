package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.StudentDao;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {
    @Override
    public IPage<Student> getPage(QueryRequest request, Integer lessonId, String studentName) {

        Page<Student> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        QueryWrapper<Student> queryWrapper = new QueryWrapper();
        queryWrapper.eq("t2.lesson_id",lessonId).eq("t1.student_name",studentName);
        return this.baseMapper.getPage(page, queryWrapper);
    }
}
