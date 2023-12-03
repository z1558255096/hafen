package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.web.domain.Coupon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CouponDao extends BaseMapper<Coupon> {

    @Select("select t1.*,t2.use_time  from t_coupon t1 left join t_coupon_customer t2 on t1.id =t2.coupon_id " +
            "${ew.customSqlSegment} ")
    IPage<Coupon> getPage(Page<Coupon> page, @Param(Constants.WRAPPER)QueryWrapper<Coupon> queryWrapper);
}
