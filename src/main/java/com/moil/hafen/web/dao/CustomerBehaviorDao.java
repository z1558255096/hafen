package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.CustomerBehavior;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CustomerBehaviorDao extends BaseMapper<CustomerBehavior> {
    @Select("select t1.*,t2.cover,t2.title from t_customer_behavior t1 left join t_article t2 on t1.option_id = t2.id " +
            "${ew.customSqlSegment} ")
    IPage<CustomerBehavior> getArticlePage(Page<CustomerBehavior> page, @Param(Constants.WRAPPER)QueryWrapper<CustomerBehavior> queryWrapper);

    @Select("select * from (" +
            "select t1.*,t2.cover,t2.name as title from t_customer_behavior t1 left join t_Lesson_Offline t2 on t1.option_id = t2.id where t2.del_flag=0 and t2.status=0 " +
            "union all " +
            "select t1.*,t2.cover,t2.name as title from t_customer_behavior t1 left join t_Lesson_Offline t2 on t1.option_id = t2.id where t2.del_flag=0 and t2.status=0 )t " +
            "${ew.customSqlSegment} ")
    IPage<CustomerBehavior> getLessonPage(Page<CustomerBehavior> page, @Param(Constants.WRAPPER)QueryWrapper<CustomerBehavior> queryWrapper);

    @Select("select t1.*,t2.cover,t2.name as title from t_customer_behavior t1 left join t_commune_ticket t2 on t1.option_id = t2.id " +
            "${ew.customSqlSegment} ")
    IPage<CustomerBehavior> getTicketPage(Page<CustomerBehavior> page, QueryWrapper<CustomerBehavior> queryWrapper);

    @Select("select t1.*,t2.mainImg as cover,t2.name as title from t_customer_behavior t1 left join t_goods t2 on t1.option_id = t2.id " +
            "${ew.customSqlSegment} ")
    IPage<CustomerBehavior> getGoodsPage(Page<CustomerBehavior> page, QueryWrapper<CustomerBehavior> queryWrapper);
}
