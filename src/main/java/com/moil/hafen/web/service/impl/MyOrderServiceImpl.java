package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MyOrderServiceImpl implements MyOrderService {
    @Resource
    private GoodsOrderService goodsOrderService;
    @Resource
    private LessonOnlineOrderService lessonOnlineOrderService;
    @Resource
    private LessonOfflineOrderService lessonOfflineOrderService;
    @Resource
    private CommuneLessonOrderService communeLessonOrderService;
    @Resource
    private CommuneTicketOrderService communeTicketOrderService;

    @Override
    public IPage<GoodsOrder> getGoodsOrderPage(QueryRequest request,Integer status) {//status 0:全部 1待付款 2待发货 3待收货 4退款/售后
        int customerId = JWTUtil.getCurrentCustomerId();
        Page<GoodsOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<GoodsOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GoodsOrder::getCustomerId,customerId).orderByDesc(GoodsOrder::getCreateTime);
        if(status==1){
            lambdaQueryWrapper.eq(GoodsOrder::getOrderStatus,400);
        }else if(status==2){
            lambdaQueryWrapper.eq(GoodsOrder::getOrderStatus,600);
        }else if(status==3){
            lambdaQueryWrapper.eq(GoodsOrder::getOrderStatus,700);
        }else if(status==4){
            lambdaQueryWrapper.ge(GoodsOrder::getAfterSalesType,1);
        }
        return goodsOrderService.page(page,lambdaQueryWrapper);
    }

    @Override
    public IPage<LessonOnlineOrder> getLessonOnlineOrderPage(QueryRequest request) {
        int customerId = JWTUtil.getCurrentCustomerId();
        Page<LessonOnlineOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<LessonOnlineOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LessonOnlineOrder::getCustomerId,customerId).orderByDesc(LessonOnlineOrder::getCreateTime);
        return lessonOnlineOrderService.page(page,lambdaQueryWrapper);
    }

    @Override
    public IPage<LessonOfflineOrder> getLessonOfflineOrderPage(QueryRequest request) {
        int customerId = JWTUtil.getCurrentCustomerId();
        Page<LessonOfflineOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<LessonOfflineOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LessonOfflineOrder::getCustomerId,customerId).orderByDesc(LessonOfflineOrder::getCreateTime);
        return lessonOfflineOrderService.page(page,lambdaQueryWrapper);
    }

    @Override
    public IPage<CommuneLessonOrder> getCommuneLessonOrderPage(QueryRequest request) {
        int customerId = JWTUtil.getCurrentCustomerId();
        Page<CommuneLessonOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<CommuneLessonOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommuneLessonOrder::getCustomerId,customerId).orderByDesc(CommuneLessonOrder::getCreateTime);
        return communeLessonOrderService.page(page,lambdaQueryWrapper);
    }

    @Override
    public IPage<CommuneTicketOrder> getCommuneTicketOrderPage(QueryRequest request) {
        int customerId = JWTUtil.getCurrentCustomerId();
        Page<CommuneTicketOrder> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<CommuneTicketOrder> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CommuneTicketOrder::getCustomerId,customerId).orderByDesc(CommuneTicketOrder::getCreateTime);
        return communeTicketOrderService.page(page,lambdaQueryWrapper);
    }
}
