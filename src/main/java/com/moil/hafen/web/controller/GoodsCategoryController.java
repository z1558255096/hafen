package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.GoodsCategory;
import com.moil.hafen.web.service.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"goodsCategory"})
@Api(tags = "商品类目管理")
public class GoodsCategoryController extends BaseController {

    private String message;

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @GetMapping
    @ApiOperation("获取商品类目列表（分页）")
    public Map<String, Object> page(QueryRequest request, GoodsCategory goodsCategory) {
        IPage<GoodsCategory> page = this.goodsCategoryService.getPage(request, goodsCategory);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加商品类目信息")
    public Result add(GoodsCategory goodsCategory) throws FebsException {
        try {
            goodsCategory.setCreateTime(new Date());
            goodsCategory.setModifyTime(new Date());
            goodsCategory.setDelFlag(0);
            return Result.OK(this.goodsCategoryService.save(goodsCategory));
        } catch (Exception e) {
            message = "添加商品类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除商品类目信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            GoodsCategory goodsCategory = new GoodsCategory();
            goodsCategory.setDelFlag(1);
            goodsCategory.setId(id);
            goodsCategory.setModifyTime(new Date());
            return Result.OK(this.goodsCategoryService.updateById(goodsCategory));
        } catch (Exception e) {
            message = "删除商品类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改商品类目信息")
    public Result update(GoodsCategory goodsCategory) throws FebsException {
        try {
            goodsCategory.setModifyTime(new Date());
            return Result.OK(this.goodsCategoryService.updateById(goodsCategory));
        } catch (Exception e) {
            message = "修改商品类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取商品类目详情")
    public Result<GoodsCategory> detail(@PathVariable Integer id) {
        return Result.OK(this.goodsCategoryService.getById(id));
    }
    @GetMapping("/list")
    @ApiOperation("获取商品类目列表")
    public Result list() {
        List<GoodsCategory> list = this.goodsCategoryService.list(new LambdaQueryWrapper<GoodsCategory>().eq(GoodsCategory::getDelFlag,0));
        return Result.OK(list);
    }

}
