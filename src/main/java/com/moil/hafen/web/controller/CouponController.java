package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Coupon;
import com.moil.hafen.web.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"coupon"})
@Api(tags = "优惠券管理")
public class CouponController extends BaseController {

    private String message;

    @Resource
    private CouponService couponService;

    /**
     * 获取优惠券列表（分页） 管理后台/小程序
     *
     * @param request 要求
     * @param coupon  息票
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取优惠券列表（分页）")
    public Map<String, Object> page(QueryRequest request, Coupon coupon) {
        IPage<Coupon> page = this.couponService.getPage(request, coupon);
        return getDataTable(page);
    }

    /**
     * 添加优惠券信息 - 管理后台
     *
     * @param coupon 息票
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加优惠券信息")
    public Result add(Coupon coupon) throws FebsException {
        try {
            this.couponService.saveCoupon(coupon);
            return Result.OK();
        } catch (Exception e) {
            message = "添加优惠券信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除优惠券信息 - 管理后台
     *
     * @param id id
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除优惠券信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            Coupon coupon = this.couponService.getById(id);
            if(coupon.getStatus()==0){
               throw new FebsException("仅下架优惠券可以删除");
            }
            coupon.setDelFlag(1);
            coupon.setModifyTime(new Date());
            return Result.OK(this.couponService.updateById(coupon));
        } catch (Exception e) {
            message = "删除优惠券信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上/下架优惠券信息 - 管理后台
     *
     * @param id     id
     * @param status 地位
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上/下架优惠券信息")
    public Result changeStatus(@PathVariable Integer id, Integer status) throws FebsException {
        try {
            Coupon coupon = this.couponService.getById(id);
            coupon.setStatus(status);
            coupon.setModifyTime(new Date());
            return Result.OK(this.couponService.updateById(coupon));
        } catch (Exception e) {
            message = "上/下架优惠券信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改优惠券信息 - 管理后台
     *
     * @param coupon 息票
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改优惠券信息")
    public Result update(Coupon coupon) throws FebsException {
        try {
            coupon.setModifyTime(new Date());
            return Result.OK(this.couponService.updateById(coupon));
        } catch (Exception e) {
            message = "修改优惠券信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取优惠券详情 - 管理后台/小程序
     *
     * @param id id
     * @return {@link Result}<{@link Coupon}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取优惠券详情")
    public Result<Coupon> detail(@PathVariable Integer id) {
        return Result.OK(this.couponService.getById(id));
    }

    /**
     * 查询用户优惠券列表 - 管理后台/小程序
     *
     * @param request 要求
     * @param status  地位
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/getCouponByPage")
    @ApiOperation("查询用户优惠券列表")
    public Map<String, Object> getCouponByPage(QueryRequest request, int status) {
        IPage<Coupon> page = this.couponService.getCouponByPage(request, status);
        return getDataTable(page);
    }
}
