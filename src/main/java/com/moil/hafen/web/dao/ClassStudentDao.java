package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.ClassStudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ClassStudentDao extends BaseMapper<ClassStudent> {
    @Select("select t1.*,t2.sex,t2.mobile,t2.student_name from t_class_student t1 left join t_customer t2 on  t1.customer_id=t2.id " +
            "${ew.customSqlSegment}")
    IPage<ClassStudent> getPage(Page<ClassStudent> page, @Param(Constants.WRAPPER)LambdaQueryWrapper<ClassStudent> lambdaQueryWrapper);
}
