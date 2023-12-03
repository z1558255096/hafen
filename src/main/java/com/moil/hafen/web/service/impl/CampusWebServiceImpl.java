package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import com.moil.hafen.web.vo.CampusWebVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class CampusWebServiceImpl implements CampusWebService {
    @Resource
    private CampusService campusService;
    @Resource
    private LessonWebRecommendService lessonWebRecommendService;
    @Resource
    private LessonWebBannerService lessonWebBannerService;
    @Resource
    private LessonWebTeacherService lessonWebTeacherService;
    @Resource
    private LessonWebEnvironService lessonWebEnvironService;
    @Resource
    private LessonWebLessonService lessonWebLessonService;
    @Resource
    private LessonAuditionService lessonAuditionService;
    @Resource
    private LessonAuditionStudentService lessonAuditionStudentService;
    @Resource
    private LessonOfflineService lessonOfflineService;


    @Override
    public CampusWebVo info(Integer campusId) {
        Campus campus = campusService.getById(campusId);
        List<LessonWebRecommend> lessonWebRecommendList = lessonWebRecommendService.list(new LambdaQueryWrapper<LessonWebRecommend>()
                .eq(LessonWebRecommend::getCampusId,campusId));
        List<LessonWebBanner> lessonWebBannerList = lessonWebBannerService.list(new LambdaQueryWrapper<LessonWebBanner>().eq(LessonWebBanner::getCampusId, campusId));
        List<LessonWebTeacher> lessonWebTeacherList = lessonWebTeacherService.list(new LambdaQueryWrapper<LessonWebTeacher>().eq(LessonWebTeacher::getCampusId, campusId));
        List<LessonWebEnviron> lessonWebEnvironList = lessonWebEnvironService.list(new LambdaQueryWrapper<LessonWebEnviron>().eq(LessonWebEnviron::getCampusId, campusId));
        List<LessonWebLesson> lessonWebLessonList = lessonWebLessonService.list(new LambdaQueryWrapper<LessonWebLesson>().eq(LessonWebLesson::getCampusId, campusId));
        List<LessonAudition> lessonAuditionList = lessonAuditionService.list(new LambdaQueryWrapper<LessonAudition>().eq(LessonAudition::getCampusId,campusId).ge(LessonAudition::getClassDateTime, LocalDate.now()).orderByAsc(LessonAudition::getClassDateTime).last("limit 2"));
        if(CollectionUtils.isNotEmpty(lessonAuditionList)){
            int customerId = JWTUtil.getCurrentCustomerId();
            for (LessonAudition lessonAudition : lessonAuditionList) {
                int count = lessonAuditionStudentService.count(new LambdaQueryWrapper<LessonAuditionStudent>()
                        .eq(LessonAuditionStudent::getAuditionId, lessonAudition.getId())
                        .eq(LessonAuditionStudent::getCustomerId, customerId).eq(LessonAuditionStudent::getStatus, 0));
                if(count>0){
                    lessonAudition.setStudentReservationStatus(1);
                }
                LessonOffline lessonOffline = lessonOfflineService.getById(lessonAudition.getLessonId());
                lessonAudition.setLessonName(lessonOffline.getName());
            }

        }
        CampusWebVo campusWebVo = new CampusWebVo();
        campusWebVo.setCampus(campus);
        campusWebVo.setLessonWebRecommendList(lessonWebRecommendList);
        campusWebVo.setLessonWebBannerList(lessonWebBannerList);
        campusWebVo.setLessonWebTeacherList(lessonWebTeacherList);
        campusWebVo.setLessonWebEnvironList(lessonWebEnvironList);
        campusWebVo.setLessonWebLessonList(lessonWebLessonList);
        campusWebVo.setLessonAuditionList(lessonAuditionList);
        return campusWebVo;
    }
}
