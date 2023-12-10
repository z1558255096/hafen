package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.GoodsOrder;
import com.moil.hafen.web.domain.GoodsOrderLogistics;
import com.moil.hafen.web.service.GoodsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 管理后台/商城模块/商品订单订单管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"goodsOrderOrder"})
@Api(tags = "管理后台/商城模块/商品订单订单管理")
public class GoodsOrderController extends BaseController {

    private String message;

    @Resource
    private GoodsOrderService goodsOrderService;


    /**
     * 创建订单
     *
     * @param order 商品订单信息
     *
     * @return {@link Result}<{@link GoodsOrder}>
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/createOrder")
    @ApiOperation("创建订单")
    public Result<GoodsOrder> createOrder(GoodsOrder order) throws FebsException {
        try {
            return Result.OK(goodsOrderService.createOrder(order));
        } catch (Exception e) {
            message = "创建订单失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过ID对商品订单售后
     *
     * @param orderId               订单id
     * @param afterSalesType        售后服务类型
     * @param afterSalesReason      售后原因
     * @param afterSalesCertificate 售后证书
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/afterSales/{orderId}")
    @ApiOperation("通过ID对商品订单售后")
    public Result afterSales(@PathVariable Integer orderId, int afterSalesType, String afterSalesReason, String afterSalesCertificate) throws FebsException {
        try {
            goodsOrderService.afterSales(orderId, afterSalesType, afterSalesReason, afterSalesCertificate);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单售后失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过ID对商品订单售后物流订单
     *
     * @param orderId     订单id
     * @param logisticsSn 物流sn
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/updateAfterSalesLogisticsSn/{orderId}")
    @ApiOperation("通过ID对商品订单售后物流订单")
    public Result updateAfterSalesLogisticsSn(@PathVariable Integer orderId, String logisticsSn) throws FebsException {
        try {
            goodsOrderService.updateAfterSalesLogisticsSn(orderId, logisticsSn);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单售后物流订单失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过ID对商品订单确认收货
     *
     * @param orderId 订单id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/confirmReceipt/{orderId}")
    @ApiOperation("通过ID对商品订单确认收货")
    public Result confirmReceipt(@PathVariable Integer orderId) throws FebsException {
        try {
            goodsOrderService.confirmReceipt(orderId);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单确认收货失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 获取商品订单物流轨迹
     *
     * @param orderId 订单id
     *
     * @return {@link Result}
     */
    @GetMapping("/goodsOrderLogistics/{orderId}")
    @ApiOperation("获取商品订单物流轨迹")
    public Result goodsOrderLogistics(@PathVariable Integer orderId) {
        List<GoodsOrderLogistics> list = goodsOrderService.getGoodsOrderLogistics(orderId);
        return Result.OK(list);
    }

    /**
     * 获取商品订单列表（分页）
     *
     * @param request    要求
     * @param goodsOrder 货物订单
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取商品订单列表（分页）")
    public Map<String, Object> page(QueryRequest request, GoodsOrder goodsOrder) {
        IPage<GoodsOrder> page = goodsOrderService.getPage(request, goodsOrder);
        return getDataTable(page);
    }

    /**
     * 通过ID获取商品订单详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link GoodsOrder}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取商品订单详情")
    public Result<GoodsOrder> detail(@PathVariable Integer id) {
        GoodsOrder goodsOrder = goodsOrderService.detail(id);
        return Result.OK(goodsOrder);
    }

    /**
     * 通过ID对商品订单发货
     *
     * @param id           id
     * @param logisticsSn  物流sn
     * @param deliveryCode 交货代码
     *
     * @return {@link Result}<{@link GoodsOrder}>
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/delivery")
    @ApiOperation("通过ID对商品订单发货")
    public Result<GoodsOrder> delivery(@PathVariable Integer id, String logisticsSn, String deliveryCode) throws FebsException {
        try {
            goodsOrderService.delivery(id, logisticsSn, deliveryCode);
            return Result.OK();
        } catch (Exception e) {
            message = "商品订单发货失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 通过ID对商品订单售后审核
     *
     * @param id     id
     * @param status 100待审核 101拒绝 102待寄出 103退货待收货 104换货待收货 200已退款 201以换货
     * @param reason 原因
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/afterSalesApproval")
    @ApiOperation("通过ID对商品订单售后审核")
    public Result afterSalesApproval(@PathVariable Integer id, String status, String reason) throws FebsException {
        try {
            goodsOrderService.afterSalesApproval(id, status, reason);
            return Result.OK();
        } catch (Exception e) {
            message = "商品订单审核失败";
            log.error(message, e);
            throw new FebsException(message);
        }
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
            goodsOrderService.refund(id);
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
     * @param goodsOrder 货物订单
     *
     * @throws FebsException FEBS系统内部异常
     */
    @GetMapping("/export")
    @ApiOperation("导出订单列表")
    public void export(GoodsOrder goodsOrder, HttpServletResponse response) throws FebsException {
        try {
            List<GoodsOrder> contestantInfoList = goodsOrderService.getGoodsOrderList(goodsOrder);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("订单列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), GoodsOrder.class).sheet("订单列表").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}
