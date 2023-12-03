package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Coupon;

public interface CouponService extends IService<Coupon> {
    IPage<Coupon> getPage(QueryRequest request, Coupon coupon);

    void saveCoupon(Coupon coupon);

    IPage<Coupon> getCouponByPage(QueryRequest request, int status);
}
