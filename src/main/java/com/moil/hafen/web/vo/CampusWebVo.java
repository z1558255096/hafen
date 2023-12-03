package com.moil.hafen.web.vo;

import com.moil.hafen.web.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class CampusWebVo {
    private Campus campus;
    private List<LessonWebRecommend> lessonWebRecommendList;
    private List<LessonWebBanner> lessonWebBannerList;
    private List<LessonWebTeacher> lessonWebTeacherList;
    private List<LessonWebEnviron> lessonWebEnvironList;
    private List<LessonWebLesson> lessonWebLessonList;
    private List<LessonAudition> lessonAuditionList;
}
