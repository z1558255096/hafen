package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.GoodsRecommend;
import com.moil.hafen.web.service.GoodsRecommendService;
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
@RequestMapping({"goodsRecommend"})
@Api(tags = "商品推荐管理")
public class GoodsRecommendController extends BaseController {

    private String message;

    @Resource
    private GoodsRecommendService goodsRecommendService;

    @GetMapping
    @ApiOperation("获取商品推荐列表（分页）")
    public Map<String, Object> page(QueryRequest request, GoodsRecommend goodsRecommend) {
        IPage<GoodsRecommend> page = this.goodsRecommendService.getPage(request, goodsRecommend);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加商品推荐信息")
    public Result add(GoodsRecommend goodsRecommend) throws FebsException {
        try {
            goodsRecommend.setCreateTime(new Date());
            return Result.OK(this.goodsRecommendService.save(goodsRecommend));
        } catch (Exception e) {
            message = "添加商品推荐信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除商品推荐信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.goodsRecommendService.removeById(id));
        } catch (Exception e) {
            message = "删除商品推荐信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改商品推荐信息")
    public Result update(GoodsRecommend goodsRecommend) throws FebsException {
        try {
            return Result.OK(this.goodsRecommendService.updateById(goodsRecommend));
        } catch (Exception e) {
            message = "修改商品推荐信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("修改商品推荐上下架信息")
    public Result changeStatus(@PathVariable Integer id, Integer status) throws FebsException {
        try {

            GoodsRecommend goodsRecommend = this.goodsRecommendService.getById(id);
            goodsRecommend.setStatus(status);
            return Result.OK(this.goodsRecommendService.updateById(goodsRecommend));
        } catch (Exception e) {
            message = "修改商品推荐上下架信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取商品推荐详情")
    public Result<GoodsRecommend> detail(@PathVariable Integer id) {
        return Result.OK(this.goodsRecommendService.getById(id));
    }

}
