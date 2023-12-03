package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.ClassSchedule;
import com.moil.hafen.web.vo.TeacherScheduleVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClassScheduleDao extends BaseMapper<ClassSchedule> {
    @Select("select t1.*,t2.class_Date,t2.id as detail_id from t_class_schedule t1 left join t_class_schedule_detail t2 on t1.id=t2.class_schedule_id " +
            "${ew.customSqlSegment}")
    IPage<ClassSchedule> getPage(Page<ClassSchedule> page, @Param(Constants.WRAPPER)LambdaQueryWrapper<ClassSchedule> lambdaQueryWrapper);

    @Select(" select * from (select t1.id as schedule_id,t1.class_time,t1.class_date,t2.name as teacher_name,t1.student_count,t1.attend_student_count, '班级日程' as class_type from t_class_schedule t1 " +
            "left join t_staff_management t2 on t1.staff_id = t2.id where t1.staffId=#{customerId} and t1.class_date = #{classDate}" +
            "union all " +
            "select t1.id as schedule_id,t1.class_time,t1.class_date,t2.name as teacher_name,t1.student_count,t1.attend_student_count, '试听日程' as class_type from t_lesson_audition t1 " +
            "left join t_staff_management t2 on t1.staff_id = t2.id where t1.staffId=#{customerId} and t1.class_date = #{classDate}) t1 ")
    List<TeacherScheduleVo> getMySchedule(@Param("classDate") String classDate,@Param("customerId") int customerId);
}
