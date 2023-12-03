package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.domain.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LessonAuditionStudentDao extends BaseMapper<LessonAuditionStudent> {
    @Select("select t1.*,t2.student_name,t2.mobile from  t_lesson_audition_student t1 " +
            "left join t_student t2 on t1.student_id=t2.id " +
            "${ew.customSqlSegment} ")
    IPage<LessonAuditionStudent> getPage(Page<LessonAuditionStudent> page, @Param(Constants.WRAPPER)QueryWrapper<LessonAuditionStudent> queryWrapper);

    @Select("select t1.create_time as my_create_time, t1.cancel_time,t2.student_name,t2.mobile from  t_lesson_audition_student t1 " +
            "left join t_student t2 on t1.student_id=t2.id " +
            "where t1.status=#{status} and t1.audition_id = #{auditionId}")
    List<Student> getStudentList(@Param("auditionId") String auditionId,@Param("auditionId")int status);

    @Select("select t1.*,t2.student_name,t2.mobile from  t_lesson_audition_student t1 " +
            "left join t_student t2 on t1.student_id=t2.id " +
            "where t1.status=#{status} and t1.audition_id = #{auditionId}")
    List<LessonAuditionStudent> getAuditionList(@Param("auditionId") String auditionId,@Param("auditionId")int status);
}
