package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StudentDao extends BaseMapper<Student> {
    @Select("select t1.* from t_student t1 left join t_student_lesson t1 on t1.id = t2.student_id " +
            "${ew.customSqlSegment} ")
    IPage<Student> getPage(Page<Student> page, @Param(Constants.WRAPPER)QueryWrapper<Student> queryWrapper);
}
