package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.LessonOffline;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LessonOfflineDao extends BaseMapper<LessonOffline> {
    String SQL = "select t1.*,t2.name as category_name,t3.chapter_count " +
            "from t_lesson_online_chapter t1 " +
            "left join t_lesson_category t2 on t1.category_id=t2.id " +
            "left join ( select lesson_id,count(1) as chapter_count t_lesson_online_chapter group by lesson_id ) t3 on t1.id=t3.lesson_id " +
            "${ew.customSqlSegment}  ";
    @Select(SQL)
    IPage<LessonOffline> getPage(Page<LessonOffline> page, @Param(Constants.WRAPPER) QueryWrapper<LessonOffline> queryWrapper);
    @Select(SQL)
    List<LessonOffline> getList(@Param(Constants.WRAPPER) QueryWrapper<LessonOffline> queryWrapper);
}
