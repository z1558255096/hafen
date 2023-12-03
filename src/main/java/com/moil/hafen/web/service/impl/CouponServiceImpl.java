package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CouponDao;
import com.moil.hafen.web.domain.Coupon;
import com.moil.hafen.web.domain.CouponCustomer;
import com.moil.hafen.web.domain.Customer;
import com.moil.hafen.web.service.CouponCustomerService;
import com.moil.hafen.web.service.CouponService;
import com.moil.hafen.web.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl extends ServiceImpl<CouponDao,Coupon> implements CouponService {
    @Resource
    private CouponCustomerService couponCustomerService;
    @Resource
    private CustomerService customerService;
    @Override
    public IPage<Coupon> getPage(QueryRequest request, Coupon coupon) {
        Page<Coupon> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        IPage<Coupon> couponIPage = this.page(page, getCondition(coupon));
        for (Coupon record : couponIPage.getRecords()) {
            LambdaQueryWrapper<CouponCustomer> queryWrapper = new LambdaQueryWrapper<CouponCustomer>().eq(CouponCustomer::getCouponId, record.getId());
            int issueCount = couponCustomerService.count(queryWrapper);
            int useCount = couponCustomerService.count(queryWrapper.eq(CouponCustomer::getStatus,1));
            record.setUseCount(useCount);
            record.setIssueCount(issueCount);
        }
        return couponIPage;

    }

    @Override
    public void saveCoupon(Coupon coupon) {
        coupon.setCreateTime(new Date());
        coupon.setModifyTime(new Date());
        this.save(coupon);
        List<Integer> customerIds;
        if(coupon.getTarget()==0){//全部用户
            customerIds = customerService.list().stream().map(Customer::getId).collect(Collectors.toList());
        }else {
            customerIds = coupon.getCustomerIds();
        }
        couponCustomerService.saveCoupon(coupon.getId(),customerIds);
    }

    @Override
    public IPage<Coupon> getCouponByPage(QueryRequest request, int status) {
        Page<Coupon> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t1.del_flag",0).eq("t1.status",0).eq("t2.customer_id", JWTUtil.getCurrentCustomerId());
        if(status==0){
            queryWrapper.eq("t2.status", 0).ge("t1.end_time", new Date());
        }else if(status==1){
            queryWrapper.eq("t2.status", 1);
        }else{
            queryWrapper.eq("t2.status", 0).lt("t1.end_time", new Date());
        }
        return this.baseMapper.getPage(page, queryWrapper);
    }

    private LambdaQueryWrapper<Coupon> getCondition(Coupon coupon){
        LambdaQueryWrapper<Coupon> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(coupon.getName())){
            lambdaQueryWrapper.eq(Coupon::getName, coupon.getName());
        }
        if(coupon.getStatus() != null){
            lambdaQueryWrapper.eq(Coupon::getStatus, coupon.getStatus());
        }
        if(StringUtils.isNotBlank(coupon.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(Coupon::getCreateTime,coupon.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(coupon.getCreateTimeTo())){
            lambdaQueryWrapper.le(Coupon::getCreateTime,coupon.getCreateTimeTo()+" 23:59:59");
        }
        return lambdaQueryWrapper;
    }
}
