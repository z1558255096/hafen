package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Student;

public interface StudentService extends IService<Student> {
    IPage<Student> getPage(QueryRequest request, Integer lessonId, String studentName);
}
