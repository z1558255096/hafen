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
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"goodsOrderOrder"})
@Api(tags = "商品订单订单管理")
public class GoodsOrderController extends BaseController {

    private String message;

    @Resource
    private GoodsOrderService goodsOrderService;


    @PostMapping("/createOrder")
    @ApiOperation("创建订单")
    public Result<GoodsOrder> createOrder(GoodsOrder order) throws FebsException {
        try {
            return Result.OK(this.goodsOrderService.createOrder(order));
        } catch (Exception e) {
            message = "创建订单失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PutMapping("/afterSales/{orderId}")
    @ApiOperation("通过ID对商品订单售后")
    public Result afterSales(@PathVariable Integer orderId,int afterSalesType, String afterSalesReason, String afterSalesCertificate) throws FebsException {
        try {
            this.goodsOrderService.afterSales(orderId,afterSalesType,afterSalesReason,afterSalesCertificate);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单售后失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PutMapping("/updateAfterSalesLogisticsSn/{orderId}")
    @ApiOperation("通过ID对商品订单售后物流订单")
    public Result updateAfterSalesLogisticsSn(@PathVariable Integer orderId,String logisticsSn) throws FebsException {
        try {
            this.goodsOrderService.updateAfterSalesLogisticsSn(orderId,logisticsSn);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单售后物流订单失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PutMapping("/confirmReceipt/{orderId}")
    @ApiOperation("通过ID对商品订单确认收货")
    public Result confirmReceipt(@PathVariable Integer orderId) throws FebsException {
        try {
            this.goodsOrderService.confirmReceipt(orderId);
            return Result.OK();
        } catch (Exception e) {
            message = "通过ID对商品订单确认收货失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @GetMapping("/goodsOrderLogistics/{orderId}")
    @ApiOperation("获取商品订单物流轨迹")
    public Result goodsOrderLogistics(@PathVariable Integer orderId) {
        List<GoodsOrderLogistics> list = this.goodsOrderService.getGoodsOrderLogistics(orderId);
        return Result.OK(list);
    }
    @GetMapping
    @ApiOperation("获取商品订单列表（分页）")
    public Map<String, Object> page(QueryRequest request, GoodsOrder goodsOrder) {
        IPage<GoodsOrder> page = this.goodsOrderService.getPage(request, goodsOrder);
        return getDataTable(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("通过ID获取商品订单详情")
    public Result<GoodsOrder> detail(@PathVariable Integer id) {
        GoodsOrder goodsOrder = this.goodsOrderService.detail(id);
        return Result.OK(goodsOrder);
    }

    @PutMapping("/{id}/delivery")
    @ApiOperation("通过ID对商品订单发货")
    public Result<GoodsOrder> delivery(@PathVariable Integer id,String logisticsSn, String deliveryCode) throws FebsException {
        try {
            this.goodsOrderService.delivery(id,logisticsSn,deliveryCode);
            return Result.OK();
        } catch (Exception e) {
            message = "商品订单发货失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PutMapping("/{id}/afterSalesApproval")
    @ApiOperation("通过ID对商品订单售后审核")
    public Result afterSalesApproval(@PathVariable Integer id,String status, String reason) throws FebsException {
        try {
            this.goodsOrderService.afterSalesApproval(id,status,reason);
            return Result.OK();
        } catch (Exception e) {
            message = "商品订单审核失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @PutMapping("/{id}/refund")
    @ApiOperation("通过ID对商品订单退款")
    public Result refund(@PathVariable Integer id) throws FebsException {
        try {
            this.goodsOrderService.refund(id);
            return Result.OK();
        } catch (Exception e) {
            message = "商品订单退款失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @GetMapping("/export")
    @ApiOperation("导出订单列表")
    public void export(GoodsOrder goodsOrder, HttpServletResponse response) throws FebsException {
        try {
            List<GoodsOrder> contestantInfoList = this.goodsOrderService.getGoodsOrderList(goodsOrder);
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
