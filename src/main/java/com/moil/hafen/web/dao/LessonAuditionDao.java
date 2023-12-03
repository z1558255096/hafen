package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.LessonAudition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LessonAuditionDao extends BaseMapper<LessonAudition> {
    String SQL = "select t1.*,t2.name as lesson_name,t3.name as campus_name,t4.student_count  from t_lesson_audition t1 " +
            "left join t_Lesson_Offline t2 on t1.lesson_id = t2.id " +
            "left join t_campus t3 on t1.campus_id = t3.id " +
            "left join (select audition_id,count(1) as student_count from t_lesson_audition_student where status=0 ) t4 on t1.id=t4.audition_id " +
            "${ew.customSqlSegment} ";

    @Select(SQL)
    List<LessonAudition> getList(@Param(Constants.WRAPPER)QueryWrapper<LessonAudition> condition);
    @Select(SQL)
    IPage<LessonAudition> getPage(Page<LessonAudition> page, @Param(Constants.WRAPPER)QueryWrapper<LessonAudition> condition);

    @Select("select t1.*,t2.name as lesson_name,t3.name as category_name  from t_lesson_audition t1 " +
            "left join t_Lesson_Offline t2 on t1.lesson_id = t2.id " +
            "left join t_lesson_category t3 on t2.category_id = t3.id ")
    List<LessonAudition> getAuditionList(@Param(Constants.WRAPPER)QueryWrapper<LessonAudition> condition);

    @Select("select t1.*,t2.name as lesson_name,t3.status as student_reservation_status, t3.class_status  from t_lesson_audition t1 " +
            "left join t_Lesson_Offline t2 on t1.lesson_id = t2.id " +
            "left join t_lesson_audition_student t3 on t1.id=t3.audition_id " +
            "${ew.customSqlSegment} ")
    IPage<LessonAudition> getMyAudition(Page<LessonAudition> page, QueryWrapper<LessonAudition> queryWrapper);
}
