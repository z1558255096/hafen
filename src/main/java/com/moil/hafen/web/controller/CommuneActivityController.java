package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneActivity;
import com.moil.hafen.web.service.CommuneActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 管理后台/公社模块/公社活动管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeActivity"})
@Api(tags = "公社活动管理")
public class CommuneActivityController extends BaseController {

    private String message;

    @Resource
    private CommuneActivityService communeActivityService;

    /**
     * 获取公社活动列表（分页） - 管理后台/小程序
     *
     * @param request         要求
     * @param communeActivity 公社活动
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取公社活动列表（分页）")
    public Result<IPage<CommuneActivity>> page(QueryRequest request, CommuneActivity communeActivity) {
        IPage<CommuneActivity> page = communeActivityService.getPage(request, communeActivity);
        return Result.OK(page);
    }

    /**
     * 添加公社活动信息 - 管理后台
     *
     * @param communeActivity 公社活动
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加公社活动信息")
    public Result add(@RequestBody CommuneActivity communeActivity) throws FebsException {
        try {
            communeActivity.setCreateTime(new Date());
            communeActivity.setModifyTime(new Date());
            return Result.OK(communeActivityService.save(communeActivity));
        } catch (Exception e) {
            message = "添加公社活动信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除公社活动信息 - 管理后台
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/delete")
    @ApiOperation("删除公社活动信息")
    public Result delete(@RequestParam Integer id) throws FebsException {
        try {
            boolean update = communeActivityService.lambdaUpdate().eq(CommuneActivity::getId, id).set(CommuneActivity::getDelFlag, 1).update();
            return Result.OK(update);
        } catch (Exception e) {
            message = "删除公社活动信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上下架公社活动 - 管理后台
     *
     * @param id     id
     * @param status 上架状态 0上架 1下架
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/changeStatus")
    @ApiOperation("上下架公社活动")
    public Result changeStatus(@RequestParam Integer id, @RequestParam int status) throws FebsException {
        try {
            CommuneActivity communeActivity = new CommuneActivity();
            communeActivity.setStatus(status);
            communeActivity.setId(id);
            communeActivity.setModifyTime(new Date());
            return Result.OK(this.communeActivityService.updateById(communeActivity));
        } catch (Exception e) {
            message = "上下架公社活动失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改公社活动信息 - 管理后台
     *
     * @param communeActivity 公社活动
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/update")
    @ApiOperation("修改公社活动信息")
    public Result update(@RequestBody CommuneActivity communeActivity) throws FebsException {
        try {
            communeActivity.setModifyTime(new Date());
            return Result.OK(this.communeActivityService.updateById(communeActivity));
        } catch (Exception e) {
            message = "修改公社活动信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取公社活动详情 - 管理后台/小程序
     *
     * @param id id
     *
     * @return {@link Result}<{@link CommuneActivity}>
     */
    @GetMapping("/detail")
    @ApiOperation("通过ID获取公社活动详情")
    public Result<CommuneActivity> detail(@RequestParam Integer id) {
        return Result.OK(this.communeActivityService.getById(id));
    }
}
