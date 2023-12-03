package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.web.dao.CouponCustomerDao;
import com.moil.hafen.web.domain.CouponCustomer;
import com.moil.hafen.web.service.CouponCustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CouponCustomerServiceImpl extends ServiceImpl<CouponCustomerDao, CouponCustomer> implements CouponCustomerService {
    @Override
    public void saveCoupon(Integer couponId, List<Integer> customerIds) {
        List<CouponCustomer> couponCustomerList = new ArrayList<>();
        for (Integer customerId : customerIds) {
            int count = this.count(new LambdaQueryWrapper<CouponCustomer>().eq(CouponCustomer::getCouponId,couponId)
                    .eq(CouponCustomer::getCustomerId,customerId));
            if(count>0){
                continue;
            }
            CouponCustomer couponCustomer = new CouponCustomer(couponId, customerId, 0, JWTUtil.getCurrentUserName(), new Date());
            couponCustomerList.add(couponCustomer);
        }
        this.saveBatch(couponCustomerList);
    }
}
