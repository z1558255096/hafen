package com.moil.hafen.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.utils.DateUtil;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.LessonAuditionDao;
import com.moil.hafen.web.domain.LessonAudition;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.LessonAuditionService;
import com.moil.hafen.web.service.LessonAuditionStudentService;
import com.moil.hafen.web.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LessonAuditionServiceImpl extends ServiceImpl<LessonAuditionDao,LessonAudition> implements LessonAuditionService {
    @Resource
    private LessonAuditionStudentService lessonAuditionStudentService;
    @Resource
    private StudentService studentService;
    @Override
    public List<LessonAudition> getLessonAuditionList(LessonAudition lessonAudition) {
        return this.baseMapper.getList(getCondition(lessonAudition));
    }

    @Override
    public IPage<LessonAudition> getPage(QueryRequest request, LessonAudition lessonAudition) {
        Page<LessonAudition> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.baseMapper.getPage(page, getCondition(lessonAudition));
    }

    private QueryWrapper<LessonAudition> getCondition(LessonAudition lessonAudition){
        QueryWrapper<LessonAudition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t1.type", lessonAudition.getType());
        if(StringUtils.isNotBlank(lessonAudition.getLessonName())){
            queryWrapper.eq("t2.name", lessonAudition.getLessonName());
        }
        if(lessonAudition.getCampusId()!=null){
            queryWrapper.eq("t1.campus_Id", lessonAudition.getCampusId());
        }
        if(lessonAudition.getStatus()!=null){
            queryWrapper.eq("t1.status", lessonAudition.getStatus());
        }
        if(lessonAudition.getReservationStatus()!=null){
            if(lessonAudition.getReservationStatus().equals("预约中")){
                queryWrapper.ge("t1.class_date_time", new Date());
            }else {
                queryWrapper.lt("t1.class_date_time", new Date());
            }
        }
        if(lessonAudition.getStaffId()!=null){
            queryWrapper.eq("t1.staff_id", lessonAudition.getStaffId());
        }
        return queryWrapper;
    }

    @Override
    public void addLessonAudition(LessonAudition lessonAudition) throws FebsException {
        if(CollectionUtil.isEmpty(lessonAudition.getClassDateList())){
            throw new FebsException("上课日期不能为空");
        }
        for (String classDate : lessonAudition.getClassDateList()) {
            lessonAudition.setClassDate(classDate);
            String classDateTime = classDate + " " + lessonAudition.getClassStartTime();
            lessonAudition.setClassDateTime(DateUtil.string2Date(classDateTime, DateUtil.FULL_TIME_SPLIT_PATTERN));
            this.save(lessonAudition);
        }
    }

    @Override
    public Map<String,List<LessonAudition>> getAuditionList(int type, String date,int campusId) {
        QueryWrapper<LessonAudition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t2.type",type).eq("t1.classDate",date).eq("t2.type",0).eq("t1.campus_id",campusId);
        List<LessonAudition> lessonAuditionList = this.baseMapper.getAuditionList(queryWrapper);
        for (LessonAudition lessonAudition : lessonAuditionList) {
            int count = lessonAuditionStudentService.count(new LambdaQueryWrapper<LessonAuditionStudent>()
                    .eq(LessonAuditionStudent::getAuditionId, lessonAudition.getId())
                    .eq(LessonAuditionStudent::getCustomerId, JWTUtil.getCurrentCustomerId()).eq(LessonAuditionStudent::getStatus, 0));
            if(count>0){
                lessonAudition.setStudentReservationStatus(1);
            }
        }
        return lessonAuditionList.stream().collect(Collectors.groupingBy(LessonAudition::getCategoryName));
    }

    @Override
    public IPage<LessonAudition> getMyAudition(QueryRequest request) {
        int customerId = JWTUtil.getCurrentCustomerId();
        Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getCustomerId, customerId)
                .orderByDesc(Student::getActiveStatus).orderByAsc(Student::getCreateTime).last("limit 1"));
        Page<LessonAudition> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        QueryWrapper<LessonAudition> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t3.student_id", student.getId()).eq("t1.status",0);
        return this.baseMapper.getMyAudition(page, queryWrapper);
    }


}
