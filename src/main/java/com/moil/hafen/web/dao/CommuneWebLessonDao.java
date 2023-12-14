package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moil.hafen.web.domain.CommuneWebLesson;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommuneWebLessonDao extends BaseMapper<CommuneWebLesson> {
    @Select("select t1.*,t2.name,t2.cover,t2.price from t_commune_web_lesson t1 " +
            "left join t_commune_lesson t2 on t1.lesson_id = t2.id " +
            "where t1.del_flag = 0 and t2.del_flag = 0 order by t1.create_time desc")
    List<CommuneWebLesson> getList();
}
