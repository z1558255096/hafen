package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.moil.hafen.web.domain.LessonWebLesson;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LessonWebLessonDao extends BaseMapper<LessonWebLesson> {
    @Select("select *,t2.cover,t2.name,t2.price,t2.actualPrice from t_lesson_web_lesson t1 left join t_Lesson_Offline t2 on t1.lesson_id=t2.id " +
            "${ew.customSqlSegment} ")
    List<LessonWebLesson> getOnlineLesson(@Param(Constants.WRAPPER)QueryWrapper<LessonWebLesson> queryWrapper);
    @Select("select t1.*,t2.cover,t2.name,t3.price,t3.actualPrice from t_lesson_web_lesson t1 left join t_Lesson_Offline t2 on t1.lesson_id=t2.id " +
            "left join (select min(price) as price,min(actualPrice) as actualPrice from t_lesson_campus_price where (campus_id = t1.campus_id or campus_id=0) and lesson_id=t2.lesson_id) t3" +
            "${ew.customSqlSegment}")
    List<LessonWebLesson> getOfflineLesson(@Param(Constants.WRAPPER)QueryWrapper<LessonWebLesson> queryWrapper);
}
