package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonOfflineOrder;
import com.moil.hafen.web.service.LessonOfflineOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理后台-科技营-线下课程订单订单管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonOfflineOrder"})
@Api(tags = "线下课程订单订单管理")
public class LessonOfflineOrderController extends BaseController {

    private String message;

    @Resource
    private LessonOfflineOrderService lessonOfflineOrderService;


    /**
     * 创建订单
     *
     * @param order 顺序
     *
     * @return {@link Result}<{@link LessonOfflineOrder}>
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/createOrder")
    @ApiOperation("创建订单")
    public Result<LessonOfflineOrder> createOrder(LessonOfflineOrder order) throws FebsException {
        try {
            return Result.OK(this.lessonOfflineOrderService.createOrder(order));
        } catch (Exception e) {
            message = "创建订单失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改订单信息
     *
     * @param lessonOfflineOrder 课程离线顺序
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改订单信息")
    public Result update(LessonOfflineOrder lessonOfflineOrder) throws FebsException {
        try {
            lessonOfflineOrder.setModifyTime(new Date());
            return Result.OK(this.lessonOfflineOrderService.updateById(lessonOfflineOrder));
        } catch (Exception e) {
            message = "修改订单信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID对商品订单售后
     *
     * @param orderId          订单id
     * @param afterSalesReason 售后原因
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/afterSales/{orderId}")
    @ApiOperation("通过ID对商品订单售后")
    public Result afterSales(@PathVariable Integer orderId, String afterSalesReason) throws FebsException {
        try {
            this.lessonOfflineOrderService.afterSales(orderId,afterSalesReason);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单售后失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 获取商品订单列表（分页）
     *
     * @param request            要求
     * @param lessonOfflineOrder 课程离线顺序
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取商品订单列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonOfflineOrder lessonOfflineOrder) {
        IPage<LessonOfflineOrder> page = this.lessonOfflineOrderService.getPage(request, lessonOfflineOrder);
        return getDataTable(page);
    }

    /**
     * 通过ID获取商品订单详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonOfflineOrder}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取商品订单详情")
    public Result<LessonOfflineOrder> detail(@PathVariable Integer id) {
        LessonOfflineOrder lessonOfflineOrder = this.lessonOfflineOrderService.getById(id);
        return Result.OK(lessonOfflineOrder);
    }

    /**
     * 通过ID对商品订单退款
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/refund")
    @ApiOperation("通过ID对商品订单退款")
    public Result refund(@PathVariable Integer id) throws FebsException {
        try {
            this.lessonOfflineOrderService.refund(id);
            return Result.OK();
        } catch (Exception e) {
            message = "商品订单退款失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    /**
     * 导出订单列表
     *
     * @param lessonOfflineOrder 课程离线顺序
     * @param response           回答
     *
     * @throws FebsException FEBS系统内部异常
     */
    @GetMapping("/export")
    @ApiOperation("导出订单列表")
    public void export(LessonOfflineOrder lessonOfflineOrder, HttpServletResponse response) throws FebsException {
        try {
            List<LessonOfflineOrder> contestantInfoList = this.lessonOfflineOrderService.getlessonOfflineOrderList(lessonOfflineOrder);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("订单列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), LessonOfflineOrder.class).sheet("订单列表").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}
