package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.web.domain.CouponCustomer;

import java.util.List;

public interface CouponCustomerService extends IService<CouponCustomer> {
    void saveCoupon(Integer couponId, List<Integer> customerIds);
}
