package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.enums.WeekEnum;
import com.moil.hafen.common.utils.DateUtil;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.ClassScheduleDao;
import com.moil.hafen.web.domain.ClassInfo;
import com.moil.hafen.web.domain.ClassSchedule;
import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.domain.ClassStudent;
import com.moil.hafen.web.service.ClassInfoService;
import com.moil.hafen.web.service.ClassScheduleService;
import com.moil.hafen.web.service.ClassScheduleStudentService;
import com.moil.hafen.web.service.ClassStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ClassScheduleServiceImpl extends ServiceImpl<ClassScheduleDao,ClassSchedule> implements ClassScheduleService {
    @Resource
    private ClassScheduleStudentService classScheduleStudentService;
    @Resource
    private ClassStudentService classStudentService;
    @Resource
    private ClassInfoService classInfoService;

    @Override
    public IPage<ClassSchedule> getPage(QueryRequest request, ClassSchedule classSchedule) {
        Page<ClassSchedule> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<ClassSchedule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ClassSchedule::getClassId,classSchedule.getClassId());
        return this.baseMapper.getPage(page, lambdaQueryWrapper);
    }

    @Override
    public List<Map<String, String>> getClassScheduleDate(String startTime,Integer classId) {
        List<Map<String,String>> list = new ArrayList<>();
        int count = this.count(new LambdaQueryWrapper<ClassSchedule>().eq(ClassSchedule::getClassId, classId).eq(ClassSchedule::getStartTime, startTime));
        if(count>0){
            List<ClassSchedule> classScheduleList = this.list(new LambdaQueryWrapper<ClassSchedule>().eq(ClassSchedule::getClassId, classId).eq(ClassSchedule::getStatus, 0)
                    .orderByAsc(ClassSchedule::getClassDate));
            for (ClassSchedule classSchedule : classScheduleList) {
                Map<String,String> map = new HashMap<>(2);
                map.put("date", DateUtil.format2(classSchedule.getClassDate(),DateUtil.YMD));
                map.put("week", classSchedule.getWeekDay());
                list.add(map);
            }
        }else {
            list = getList(startTime);
        }
        return list;
    }

    @Override
    public void addClassSchedule(ClassSchedule classSchedule) {
        classSchedule.setCreateTime(new Date());
        classSchedule.setModifyTime(new Date());
        int classId = classSchedule.getClassId();
        ClassInfo classInfo = classInfoService.getById(classId);
        for (String date : classSchedule.getClassDateList()) {
            classSchedule.setClassDate(DateUtil.string2Date(date,DateUtil.YMD));
            LocalDate day = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            classSchedule.setWeekDay(WeekEnum.getDesByCode(day.getDayOfWeek().toString()));
            this.save(classSchedule);
            saveScheduleStudent(classSchedule, classInfo);
        }
    }

    @Override
    public void updateClassSchedule(ClassSchedule classSchedule) {
        List<ClassSchedule> list = this.list(new LambdaQueryWrapper<ClassSchedule>().eq(ClassSchedule::getClassId, classSchedule.getClassId())
                .eq(ClassSchedule::getStatus,0).orderByAsc(ClassSchedule::getClassDate));
        this.remove(new LambdaUpdateWrapper<ClassSchedule>().eq(ClassSchedule::getClassId, classSchedule.getClassId())
                .eq(ClassSchedule::getStatus,0));
        List<String> dateList = classSchedule.getClassDateList();
        int classId = classSchedule.getClassId();
        ClassInfo classInfo = classInfoService.getById(classId);
        for (int i = 0; i < dateList.size(); i++) {
            String date = dateList.get(i);
            if(list.size()-1>i){
                classSchedule.setId(list.get(i).getId());
                classSchedule.setCreateTime(new Date());
            }
            classSchedule.setClassDate(DateUtil.string2Date(date,DateUtil.YMD));
            LocalDate day = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            classSchedule.setWeekDay(WeekEnum.getDesByCode(day.getDayOfWeek().toString()));
            classSchedule.setModifyTime(new Date());
            this.saveOrUpdate(classSchedule);
            saveScheduleStudent(classSchedule, classInfo);
        }
    }

    private void saveScheduleStudent(ClassSchedule classSchedule, ClassInfo classInfo){
        List<ClassScheduleStudent> list = new ArrayList<>();
        List<ClassStudent> classStudents = classStudentService.list(new LambdaQueryWrapper<ClassStudent>().eq(ClassStudent::getClassId, classInfo.getId()));
        for (ClassStudent classStudent : classStudents) {
            ClassScheduleStudent classScheduleStudent = new ClassScheduleStudent(classSchedule.getId(), classStudent.getStudentId(), classInfo.getId(), classInfo.getTeachingCount(), new Date());
            list.add(classScheduleStudent);
        }
        classScheduleStudentService.saveBatch(list);
    }

    private List<Map<String,String>> getList(String startTime){
        LocalDate start = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        long days = start.until(end, ChronoUnit.DAYS);
        long week = days / 7;
        List<Map<String,String>> list = new ArrayList<>();
        for(int i=0;i<=week;i++){
            Map<String,String> map = new HashMap<>(2);
            LocalDate localDate = start.plus(i, ChronoUnit.WEEKS);
            Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            map.put("date", DateUtil.format2(date,DateUtil.YMD));
            map.put("week", WeekEnum.getDesByCode(localDate.getDayOfWeek().toString()));
            list.add(map);
        }
        return list;
    }
}
