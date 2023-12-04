package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CustomerBehavior;
import com.moil.hafen.web.service.CustomerBehaviorService;
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
@RequestMapping({"customerBehavior"})
@Api(tags = "我的收藏管理 - 小程序")
public class CustomerBehaviorController extends BaseController {

    private String message;

    @Resource
    private CustomerBehaviorService customerBehaviorService;

    /**
     * 获取我的收藏列表（分页）
     *
     * @param request          要求
     * @param customerBehavior 客户行为
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取我的收藏列表（分页）")
    public Map<String, Object> page(QueryRequest request, CustomerBehavior customerBehavior) {
        IPage<CustomerBehavior> page = this.customerBehaviorService.getPage(request, customerBehavior);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加我的收藏/点赞---(behavior:like/collect)")
    public Result add(CustomerBehavior customerBehavior) throws FebsException {
        try {
            customerBehaviorService.addCustomerBehavior(customerBehavior);
            return Result.OK();
        } catch (Exception e) {
            message = "添加我的收藏失败";
            log.error(message, e);
            return Result.error(message);
        }
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除我的收藏")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            this.customerBehaviorService.delete(id);
            return Result.OK();
        } catch (Exception e) {
            message = "删除我的收藏失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/likeAndCollect")
    @ApiOperation("获取用户已是否点赞收藏 {source;//数据来源1文章 2课程 3门票 4商品, type;//类型 1线上课程 2线下课程}")
    public Result likeAndCollect(Integer id, Integer source, Integer type) {
        Map<String,Boolean> result = this.customerBehaviorService.likeAndCollect(id, source, type);
        return Result.OK(result);
    }

}
