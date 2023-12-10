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
import java.util.Objects;

/**
 * 管理后台/商城模块/商品类目管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"goodsCategory"})
@Api(tags = "管理后台/商品管理/商品类目管理")
public class GoodsCategoryController extends BaseController {

    private String message;

    @Resource
    private GoodsCategoryService goodsCategoryService;

    @GetMapping
    @ApiOperation("获取商品类目列表（分页）")
    public Map<String, Object> page(QueryRequest request, GoodsCategory goodsCategory) {
        IPage<GoodsCategory> page = goodsCategoryService.getPage(request, goodsCategory);
        return getDataTable(page);
    }

    /**
     * 添加商品类目信息
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     * @see GoodsCategoryController#add(GoodsCategory)
     */
    @PostMapping
    @ApiOperation("添加商品类目信息")
    public Result add(GoodsCategory goodsCategory) throws FebsException {
        try {
            int count = goodsCategoryService.count(new LambdaQueryWrapper<GoodsCategory>()
                    .eq(GoodsCategory::getName, goodsCategory.getName())
                    .eq(GoodsCategory::getDelFlag, 0));
            if (count > 0) {
                return Result.error("商品类目名称已存在");
            }
            return Result.OK(goodsCategoryService.save(goodsCategory));
        } catch (Exception e) {
            message = "添加商品类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除商品类目信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除商品类目信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            GoodsCategory goodsCategory = new GoodsCategory();
            goodsCategory.setDelFlag(1);
            goodsCategory.setId(id);
            goodsCategory.setModifyTime(new Date());
            return Result.OK(goodsCategoryService.updateById(goodsCategory));
        } catch (Exception e) {
            message = "删除商品类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改商品类目信息
     *
     * @param goodsCategory 商品类别
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改商品类目信息")
    public Result update(GoodsCategory goodsCategory) throws FebsException {
        try {
            GoodsCategory one = goodsCategoryService.getOne(new LambdaQueryWrapper<GoodsCategory>()
                    .eq(GoodsCategory::getName, goodsCategory.getName())
                    .eq(GoodsCategory::getDelFlag, 0));
            if (one != null && !Objects.equals(one.getId(), goodsCategory.getId())) {
                return Result.error("课程类目名称已存在");
            }
            goodsCategory.setModifyTime(new Date());
            return Result.OK(goodsCategoryService.updateById(goodsCategory));
        } catch (Exception e) {
            message = "修改商品类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取商品类目详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link GoodsCategory}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取商品类目详情")
    public Result<GoodsCategory> detail(@PathVariable Integer id) {
        return Result.OK(goodsCategoryService.getById(id));
    }

    /**
     * 获取商品类目列表
     *
     * @return {@link Result}
     */
    @GetMapping("/list")
    @ApiOperation("获取商品类目列表")
    public Result list() {
        List<GoodsCategory> list = goodsCategoryService.list(new LambdaQueryWrapper<GoodsCategory>().eq(GoodsCategory::getDelFlag, 0));
        return Result.OK(list);
    }

}
