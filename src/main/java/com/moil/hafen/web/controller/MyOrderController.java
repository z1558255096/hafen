package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.MyOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户小程序-个人中心-订单列表
 *
 * @author song
 */
@Slf4j
@RestController
@RequestMapping({"myOrder"})
@Api(tags = "我的订单管理")
public class MyOrderController extends BaseController {

    private String message;

    @Resource
    private MyOrderService myOrderService;

    /**
     * 获取商城订单列表（分页） status 0:全部 1待付款 2待发货 3待收货 4退款/售后
     *
     * @param request 要求
     * @param status  地位
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/goodsOrder")
    @ApiOperation("获取商城订单列表（分页） status 0:全部 1待付款 2待发货 3待收货 4退款/售后")
    public Map<String, Object> goodsOrder(QueryRequest request,int status) {
        IPage<GoodsOrder> page = this.myOrderService.getGoodsOrderPage(request, status);
        return getDataTable(page);
    }

    /**
     * 获取线上订单列表（分页）
     *
     * @param request 要求
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/lessonOnlineOrder")
    @ApiOperation("获取线上订单列表（分页）")
    public Map<String, Object> lessonOnlineOrder(QueryRequest request) {
        IPage<LessonOnlineOrder> page = this.myOrderService.getLessonOnlineOrderPage(request);
        return getDataTable(page);
    }

    /**
     * 获取线下订单列表（分页）
     *
     * @param request 要求
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/lessonOfflineOrder")
    @ApiOperation("获取线下订单列表（分页）")
    public Map<String, Object> lessonOfflineOrder(QueryRequest request) {
        IPage<LessonOfflineOrder> page = this.myOrderService.getLessonOfflineOrderPage(request);
        return getDataTable(page);
    }

    /**
     * 获取公社课程订单列表（分页）
     *
     * @param request 要求
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/communeLessonOrder")
    @ApiOperation("获取公社课程订单列表（分页）")
    public Map<String, Object> communeLessonOrder(QueryRequest request) {
        IPage<CommuneLessonOrder> page = this.myOrderService.getCommuneLessonOrderPage(request);
        return getDataTable(page);
    }

    /**
     * 获取公社门票订单列表（分页）
     *
     * @param request 要求
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/communeTicketOrder")
    @ApiOperation("获取公社门票订单列表（分页）")
    public Map<String, Object> communeTicketOrder(QueryRequest request) {
        IPage<CommuneTicketOrder> page = this.myOrderService.getCommuneTicketOrderPage(request);
        return getDataTable(page);
    }

}
