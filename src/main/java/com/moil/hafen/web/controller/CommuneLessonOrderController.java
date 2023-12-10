package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneLessonOrder;
import com.moil.hafen.web.service.CommuneLessonOrderService;
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
 * 管理后台/公社模块/课程订单
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeLessonOrder"})
@Api(tags = "公社课程订单订单管理")
public class CommuneLessonOrderController extends BaseController {

    private String message;

    @Resource
    private CommuneLessonOrderService communeLessonOrderService;


    /**
     * 创建订单
     *
     * @param order 顺序
     *
     * @return {@link Result}<{@link CommuneLessonOrder}>
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/createOrder")
    @ApiOperation("创建订单")
    public Result<CommuneLessonOrder> createOrder(@RequestBody CommuneLessonOrder order) throws FebsException {
        try {
            return Result.OK(communeLessonOrderService.createOrder(order));
        } catch (Exception e) {
            message = "创建订单失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改订单信息 - 小程序
     *
     * @param communeLessonOrder 社区课程顺序
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/update")
    @ApiOperation("修改订单信息")
    public Result update(@RequestBody CommuneLessonOrder communeLessonOrder) throws FebsException {
        try {
            communeLessonOrder.setModifyTime(new Date());
            return Result.OK(this.communeLessonOrderService.updateById(communeLessonOrder));
        } catch (Exception e) {
            message = "修改订单信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID对商品订单售后  管理后台/小程序
     *
     * @param orderId          订单id
     * @param afterSalesReason 售后原因
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/afterSales")
    @ApiOperation("通过ID对商品订单售后")
    public Result afterSales(@RequestParam Integer orderId, @RequestParam String afterSalesReason) throws FebsException {
        try {
            this.communeLessonOrderService.afterSales(orderId, afterSalesReason);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单售后失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 获取商品订单列表（分页） - 管理后台/小程序
     *
     * @param request            要求
     * @param communeLessonOrder 社区课程顺序
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取商品订单列表（分页）")
    public Map<String, Object> page(QueryRequest request, CommuneLessonOrder communeLessonOrder) {
        IPage<CommuneLessonOrder> page = this.communeLessonOrderService.getPage(request, communeLessonOrder);
        return getDataTable(page);
    }

    /**
     * 通过ID获取订单详情 管理后台/小程序
     *
     * @param id id
     *
     * @return {@link Result}<{@link CommuneLessonOrder}>
     */
    @GetMapping("/detail")
    @ApiOperation("通过ID获取订单详情")
    public Result<CommuneLessonOrder> detail(@RequestParam Integer id) {
        CommuneLessonOrder communeLessonOrder = this.communeLessonOrderService.getById(id);
        return Result.OK(communeLessonOrder);
    }

    /**
     * 通过ID对订单退款 管理后台/小程序
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/refund")
    @ApiOperation("通过ID对订单退款")
    public Result refund(@RequestParam Integer id) throws FebsException {
        try {
            this.communeLessonOrderService.refund(id);
            return Result.OK();
        } catch (Exception e) {
            message = "订单退款失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过ID对订单核销 管理后台
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/use")
    @ApiOperation("通过ID对订单核销")
    public Result use(@RequestParam Integer id) throws FebsException {
        try {
            this.communeLessonOrderService.use(id);
            return Result.OK();
        } catch (Exception e) {
            message = "订单核销失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    /**
     * 导出订单列表 - 管理后台
     *
     * @param communeLessonOrder 社区课程顺序
     * @param response           回答
     *
     * @throws FebsException FEBS系统内部异常
     */
    @GetMapping("/export")
    @ApiOperation("导出订单列表")
    public void export(CommuneLessonOrder communeLessonOrder, HttpServletResponse response) throws FebsException {
        try {
            List<CommuneLessonOrder> contestantInfoList = this.communeLessonOrderService.getCommuneLessonOrderList(communeLessonOrder);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("订单列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), CommuneLessonOrder.class).sheet("订单列表").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}
