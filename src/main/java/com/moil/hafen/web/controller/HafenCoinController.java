package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.HafenCoin;
import com.moil.hafen.web.service.HafenCoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"hafenCoin"})
@Api(tags = "小程序-哈奋币管理")
public class HafenCoinController extends BaseController {

    private String message;

    @Resource
    private HafenCoinService hafenCoinService;

    /**
     * 获取哈奋币列表（分页）
     *
     * @param request   要求
     * @param hafenCoin 港湾币
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取哈奋币列表（分页）")
    public Map<String, Object> page(QueryRequest request, HafenCoin hafenCoin) {
        IPage<HafenCoin> page = this.hafenCoinService.getPage(request, hafenCoin);
        return getDataTable(page);
    }

    /**
     * 添加哈奋币信息
     *
     * @param hafenCoin 港湾币
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加哈奋币信息")
    public Result add(HafenCoin hafenCoin) throws FebsException {
        try {
            this.hafenCoinService.saveHafenCoin(hafenCoin);
            return Result.OK();
        } catch (Exception e) {
            message = "添加哈奋币信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除哈奋币信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除哈奋币信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.hafenCoinService.removeById(id));
        } catch (Exception e) {
            message = "删除哈奋币信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取哈奋币详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link HafenCoin}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取哈奋币详情")
    public Result<HafenCoin> detail(@PathVariable Integer id) {
        return Result.OK(this.hafenCoinService.getById(id));
    }

    /**
     * 获取我的哈奋币列表（分页）
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/myCoin")
    @ApiOperation("获取我的哈奋币列表（分页）")
    public Map<String, Object> myCoin(QueryRequest request) {
        HafenCoin hafenCoin = new HafenCoin();
        int customerId = JWTUtil.getCurrentCustomerId();
        hafenCoin.setCustomerId(customerId);
        IPage<HafenCoin> page = this.hafenCoinService.getPage(request, hafenCoin);
        HafenCoin coin = hafenCoinService.getOne(new QueryWrapper<HafenCoin>().select("ifnull(sum(coin),0) as totalCoin").eq("customer_id", customerId));
        Map<String, Object> dataTable = getDataTable(page);
        dataTable.put("sumCoin",coin.getSumCoin());
        return dataTable;
    }

}
