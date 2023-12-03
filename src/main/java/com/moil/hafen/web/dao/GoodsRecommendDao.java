package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.GoodsRecommend;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface GoodsRecommendDao extends BaseMapper<GoodsRecommend> {
    @Select("select t1.*,t2.name as goods_name " +
            "from t_goods_recommend t1 left join t_goods t2 on t1.goods_id=t2.id " +
            "${ew.customSqlSegment}  ")
    IPage<GoodsRecommend> getPage(Page<GoodsRecommend> page, @Param(Constants.WRAPPER) QueryWrapper<GoodsRecommend> lambdaQueryWrapper);
    @Select("select t1.*,t2.name as goods_name,t3.price " +
            "from t_goods_recommend t1 left join t_goods t2 on t1.goods_id=t2.id " +
            "left join (select goods_id,min(price) as price from t_goods_specs group by goods_id ) t3 on t3.goods_id=t2.id" +
            "${ew.customSqlSegment}  ")
    List<GoodsRecommend> getList(@Param(Constants.WRAPPER)QueryWrapper<GoodsRecommend> queryWrapper);
}
