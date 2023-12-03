package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.domain.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClassScheduleStudentDao extends BaseMapper<ClassScheduleStudent> {
    @Select("select t1.create_time as my_create_time,t2.student_name,t2.mobile from  t_class_schedule_student t1 " +
            "left join t_student t2 on t1.student_id=t2.id " +
            "where t1.schedule_id = #{scheduleId}")
    List<Student> getMyStudentList(String scheduleId);

    @Select("select t1.*,t2.student_name,t2.mobile from  t_class_schedule_student t1 " +
            "left join t_student t2 on t1.student_id=t2.id " +
            "where t1.schedule_id = #{scheduleId}")
    List<ClassScheduleStudent> getMyAttendScheduleList(String scheduleId);
}
