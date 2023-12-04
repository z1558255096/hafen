package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Goods;
import com.moil.hafen.web.domain.GoodsSpecs;
import com.moil.hafen.web.service.GoodsService;
import com.moil.hafen.web.service.GoodsSpecsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理后台——商城——商品管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"goods"})
@Api(tags = "管理后台—商城—商品管理")
public class GoodsController extends BaseController {

    private String message;

    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsSpecsService goodsSpecsService;

    /**
     * 获取商品列表（分页）
     *
     * @param request 要求
     * @param goods   商品
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取商品列表（分页）")
    public Map<String, Object> page(QueryRequest request, Goods goods) {
        IPage<Goods> page = this.goodsService.getPage(request, goods);
        return getDataTable(page);
    }

    /**
     * 添加商品信息
     *
     * @param goods 商品
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加商品信息")
    public Result add(Goods goods) throws FebsException {
        try {
            goods.setCreateTime(new Date());
            goods.setModifyTime(new Date());
            goods.setDelFlag(0);
            this.goodsService.save(goods);
            List<GoodsSpecs> goodsSpecsList = goods.getGoodsSpecsList();
            for (GoodsSpecs goodsSpecs : goodsSpecsList) {
                goodsSpecs.setGoodsId(goods.getId());
            }
            goodsSpecsService.saveBatch(goodsSpecsList);
            return Result.OK();
        } catch (Exception e) {
            message = "添加商品信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除商品信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除商品信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            Goods goods = new Goods();
            goods.setDelFlag(1);
            goods.setId(id);
            goods.setModifyTime(new Date());
            return Result.OK(this.goodsService.updateById(goods));
        } catch (Exception e) {
            message = "删除商品信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上下架商品信息
     *
     * @param id     id
     * @param status 0上架 1下架
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架商品信息")
    public Result changeStatus(@PathVariable Integer id, int status) throws FebsException {
        try {
            Goods goods = new Goods();
            goods.setStatus(status);
            goods.setId(id);
            goods.setModifyTime(new Date());
            return Result.OK(this.goodsService.updateById(goods));
        } catch (Exception e) {
            message = "删除商品信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改商品信息
     *
     * @param goods 商品
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改商品信息")
    public Result update(Goods goods) throws FebsException {
        try {
            goods.setModifyTime(new Date());
            this.goodsService.updateById(goods);
            goodsSpecsService.remove(new LambdaUpdateWrapper<GoodsSpecs>().eq(GoodsSpecs::getGoodsId, goods.getId()));
            List<GoodsSpecs> goodsSpecsList = goods.getGoodsSpecsList();
            for (GoodsSpecs goodsSpecs : goodsSpecsList) {
                goodsSpecs.setGoodsId(goods.getId());
            }
            goodsSpecsService.saveBatch(goodsSpecsList);
            return Result.OK();
        } catch (Exception e) {
            message = "修改商品信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取商品详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link Goods}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取商品详情")
    public Result<Goods> detail(@PathVariable Integer id) {
        Goods goods = this.goodsService.detail(id);
        return Result.OK(goods);
    }

    /**
     * 获取商品列表
     *
     * @return {@link Result}
     */
    @GetMapping("/list")
    @ApiOperation("获取商品列表")
    public Result list() {
        List<Goods> list = this.goodsService.list();
        return Result.OK(list);
    }

}
