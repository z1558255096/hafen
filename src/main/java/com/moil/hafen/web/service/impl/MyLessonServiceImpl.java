package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import com.moil.hafen.web.vo.MyLessonOfflineVo;
import com.moil.hafen.web.vo.MyLessonOnlineVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyLessonServiceImpl implements MyLessonService {
    @Resource
    private LessonOnlineOrderService lessonOnlineOrderService;
    @Resource
    private LessonOfflineOrderService lessonOfflineOrderService;
    @Resource
    private LessonOnlineChapterService lessonOnlineChapterService;
    @Resource
    private LessonOnlineService lessonOnlineService;
    @Resource
    private LessonOfflineService lessonOfflineService;
    @Resource
    private LessonOnlineStudyService lessonOnlineStudyService;
    @Resource
    private StudentService studentService;
    @Resource
    private LessonOfflineStudentRecordService lessonOfflineStudentRecordService;

    @Override
    public List<MyLessonOnlineVo> getMyLessonOnlineList() {
        int customerId = JWTUtil.getCurrentCustomerId();
        List<LessonOnlineOrder> orderList = lessonOnlineOrderService.list(new LambdaQueryWrapper<LessonOnlineOrder>().eq(LessonOnlineOrder::getCustomerId, customerId)
                .and(query->query.eq(LessonOnlineOrder::getStatus,1).or().eq(LessonOnlineOrder::getStatus,3)));
        List<MyLessonOnlineVo> list = new ArrayList<>();
        for (LessonOnlineOrder lessonOnlineOrder : orderList) {
            Integer lessonId = lessonOnlineOrder.getLessonId();
            LessonOnline lessonOnline = lessonOnlineService.getById(lessonId);
            int count = lessonOnlineChapterService.count(new LambdaQueryWrapper<LessonOnlineChapter>().eq(LessonOnlineChapter::getLessonId, lessonId)
                    .eq(LessonOnlineChapter::getStatus, 0).eq(LessonOnlineChapter::getDelFlag, 0));
            int count1 = this.lessonOnlineStudyService.count(new LambdaQueryWrapper<LessonOnlineStudy>().eq(LessonOnlineStudy::getLessonId, lessonId));
            MyLessonOnlineVo myLessonOnlineVo = new MyLessonOnlineVo(lessonId, lessonOnline.getName(), lessonOnline.getCover(), count, count1);
            list.add(myLessonOnlineVo);
        }
        return list;
    }

    @Override
    public List<MyLessonOfflineVo> getMyLessonOfflineList() {
        int customerId = JWTUtil.getCurrentCustomerId();
        Student student = studentService.getOne(new LambdaQueryWrapper<Student>().eq(Student::getCustomerId, customerId)
                .orderByDesc(Student::getActiveStatus).orderByAsc(Student::getCreateTime).last("limit 1"));
        List<LessonOfflineOrder> orderList = lessonOfflineOrderService.list(new QueryWrapper<LessonOfflineOrder>()
                .select("lesson_id,ifnull(sum(lesson_count),0) as lesson_count,ifnull(sum(give_lesson_count),0) as give_lesson_count")
                        .eq("student_id", student.getId()).and(query->query.eq("status",1).or().eq("status",3))
                .groupBy("lesson_id"));
        List<MyLessonOfflineVo> list = new ArrayList<>();
        for (LessonOfflineOrder lessonOfflineOrder : orderList) {
            Integer lessonId = lessonOfflineOrder.getLessonId();
            LessonOffline lessonOffline = lessonOfflineService.getById(lessonId);
            LessonOfflineStudentRecord serviceOne = lessonOfflineStudentRecordService.getOne(new QueryWrapper<LessonOfflineStudentRecord>()
                    .select("ifnull(sum(count)) as count ")
                    .eq("student_id", student.getId()).eq("lesson_id", lessonId));
            MyLessonOfflineVo myLessonOfflineVo = new MyLessonOfflineVo(lessonId, lessonOffline.getName(), lessonOffline.getCover(), serviceOne.getCount());
            list.add(myLessonOfflineVo);
        }
        return list;
    }
}
