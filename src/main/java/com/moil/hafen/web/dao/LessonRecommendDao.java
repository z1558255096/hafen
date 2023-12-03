package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.LessonRecommend;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LessonRecommendDao extends BaseMapper<LessonRecommend> {
    @Select("select * from (" +
            "select t1.*,t2.name as lesson_name, 2 as mode " +
            "from t_lesson_recommend t1 left join t_Lesson_Offline t2 on t1.lesson_id=t2.id where t2.status=0 and t2.del_flag=0 " +
            " union all  " +
            "select t1.*,t2.name as lesson_name, 1 as mode " +
            "from t_lesson_recommend t1 left join t_Lesson_Online t2 on t1.lesson_id=t2.id where t2.status=0 and t2.del_flag=0 " +
            ")t " +
            "${ew.customSqlSegment} ")
    IPage<LessonRecommend> getPage(Page<LessonRecommend> page, @Param(Constants.WRAPPER)QueryWrapper<LessonRecommend> queryWrapper);

    @Select("select * from (" +
            "select t1.*,t2.name as lesson_name,2 as mode,actual_price,price  " +
            "from t_lesson_recommend t1 left join t_Lesson_Offline t2 on t1.lesson_id=t2.id " +
            "left join (select lesson_id,price as price,min(actual_price) as actual_price from t_lesson_campus_price group by lesson_id )t3 on t2.id=t3.lesson_id " +
            "where t2.status=0 and t2.del_flag=0 " +
            " union all  " +
            "select t1.*,t2.name as lesson_name,1 as mode,actual_price,price  " +
            "from t_lesson_recommend t1 left join t_Lesson_Online t2 on t1.lesson_id=t2.id where t2.status=0 and t2.del_flag=0 " +
            ")t " +
            "${ew.customSqlSegment} ")
    List<LessonRecommend> getList(@Param(Constants.WRAPPER)QueryWrapper<LessonRecommend> queryWrapper);
}
