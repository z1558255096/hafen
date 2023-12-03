package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CouponCustomer;
import com.moil.hafen.web.service.CouponCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"couponCustomer"})
@Api(tags = "优惠券管理")
public class CouponCustomerController extends BaseController {

    private String message;

    @Resource
    private CouponCustomerService couponCustomerService;


    @PostMapping
    @ApiOperation("手动发放优惠券")
    public Result add(Integer couponId, List<Integer> customerIds) throws FebsException {
        try {
            this.couponCustomerService.saveCoupon(couponId, customerIds);
            return Result.OK();
        } catch (Exception e) {
            message = "手动发放优惠券失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping("/{couponId}/getUnUseCount")
    @ApiOperation("查询是否有未使用的优惠券")
    public Result getUnUseCount(@PathVariable Integer couponId) {
        return Result.OK(this.couponCustomerService.count(new LambdaQueryWrapper<CouponCustomer>()
                .eq(CouponCustomer::getCouponId,couponId).eq(CouponCustomer::getStatus,0)));
    }

}
