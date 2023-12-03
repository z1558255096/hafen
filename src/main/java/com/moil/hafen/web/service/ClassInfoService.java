package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.ClassInfo;

import java.util.List;

public interface ClassInfoService extends IService<ClassInfo> {
    IPage<ClassInfo> getPage(QueryRequest request, ClassInfo classInfo);

    List<ClassInfo> getClassInfoList(ClassInfo classInfo);

    void updateClassInfo(ClassInfo classInfo);
}
