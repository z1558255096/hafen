package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.ClassSchedule;

import java.util.List;
import java.util.Map;

public interface ClassScheduleService extends IService<ClassSchedule> {
    IPage<ClassSchedule> getPage(QueryRequest request, ClassSchedule classSchedule);

    List<Map<String, String>> getClassScheduleDate(String startTime,Integer classId);

    void addClassSchedule(ClassSchedule classSchedule);

    void updateClassSchedule(ClassSchedule classSchedule);
}
