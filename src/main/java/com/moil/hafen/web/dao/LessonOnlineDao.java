package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.LessonOnline;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LessonOnlineDao extends BaseMapper<LessonOnline> {
    String SQL = "select t1.*,t2.name as category_name,t3.chapter_count " +
            "from t_lesson_online_chapter t1 " +
            "left join t_lesson_category t2 on t1.category_id=t2.id " +
            "left join ( select lesson_id,count(1) as chapter_count t_lesson_online_chapter group by lesson_id ) t3 on t1.id=t3.lesson_id " +
            "${ew.customSqlSegment}  ";
    @Select(SQL)
    IPage<LessonOnline> getPage(Page<LessonOnline> page, @Param(Constants.WRAPPER) QueryWrapper<LessonOnline> queryWrapper);
    @Select(SQL)
    List<LessonOnline> getList(@Param(Constants.WRAPPER) QueryWrapper<LessonOnline> queryWrapper);
}
