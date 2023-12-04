package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CustomerAddress;
import com.moil.hafen.web.service.CustomerAddressService;
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
@RequestMapping({"customerAddress"})
@Api(tags = "前端-用户收货管理")
public class CustomerAddressController extends BaseController {

    private String message;

    @Resource
    private CustomerAddressService customerAddressService;

    /**
     * 获取用户收货列表（分页） - 管理后台/小程序
     *
     * @param request         要求
     * @param customerAddress 客户地址
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取用户收货列表（分页）")
    public Map<String, Object> customerAddressList(QueryRequest request, CustomerAddress customerAddress) {
        IPage<CustomerAddress> page = this.customerAddressService.getPage(request, customerAddress);
        return getDataTable(page);
    }

    /**
     * 添加用户收货信息 - 管理后台/小程序
     *
     * @param customerAddress 客户地址
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加用户收货信息")
    public Result addCustomerAddress(CustomerAddress customerAddress) throws FebsException {
        try {
            customerAddress.setCustomerId(JWTUtil.getCurrentCustomerId());
            customerAddress.setCreateTime(new Date());
            customerAddress.setModifyTime(new Date());
            return Result.OK(this.customerAddressService.save(customerAddress));
        } catch (Exception e) {
            message = "添加用户收货信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除用户收货信息 - 管理后台/小程序
     *
     * @param id id
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户收货信息")
    public Result deleteCustomerAddress(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.customerAddressService.removeById(id));
        } catch (Exception e) {
            message = "删除用户收货信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改用户收货信息 - 管理后台/小程序
     *
     * @param customerAddress 客户地址
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改用户收货信息")
    public Result updateCustomerAddress(CustomerAddress customerAddress) throws FebsException {
        try {
            customerAddress.setModifyTime(new Date());
            return Result.OK(this.customerAddressService.updateById(customerAddress));
        } catch (Exception e) {
            message = "修改用户收货信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 设置用户默认收货地址 - 小程序
     *
     * @param id id
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/default")
    @ApiOperation("设置用户默认收货地址")
    public Result defaultCustomerAddress(@PathVariable Integer id) throws FebsException {
        try {
            this.customerAddressService.update(new LambdaUpdateWrapper<CustomerAddress>()
                    .set(CustomerAddress::getStatus,0).set(CustomerAddress::getModifyTime,new Date()));
            CustomerAddress customerAddress = new CustomerAddress();
            customerAddress.setModifyTime(new Date());
            customerAddress.setStatus(1);
            customerAddress.setId(id);
            this.customerAddressService.updateById(customerAddress);
            return Result.OK();
        } catch (Exception e) {
            message = "设置用户默认收货地址失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取用户收货详情 - 管理后台/小程序
     *
     * @param id id
     * @return {@link Result}<{@link CustomerAddress}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取用户收货详情")
    public Result<CustomerAddress> detail(@PathVariable Integer id) {
        return Result.OK(this.customerAddressService.getById(id));
    }

}
