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
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取公社活动列表（分页）")
    public Map<String, Object> page(QueryRequest request, CommuneActivity communeActivity) {
        IPage<CommuneActivity> page = this.communeActivityService.getPage(request, communeActivity);
        return getDataTable(page);
    }

    /**
     * 添加公社活动信息 - 管理后台
     *
     * @param communeActivity 公社活动
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加公社活动信息")
    public Result add(CommuneActivity communeActivity) throws FebsException {
        try {
            communeActivity.setCreateTime(new Date());
            communeActivity.setModifyTime(new Date());
            return Result.OK(this.communeActivityService.save(communeActivity));
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
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除公社活动信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.communeActivityService.removeById(id));
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
     * @param status 地位
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架公社活动")
    public Result changeStatus(@PathVariable Integer id, int status) throws FebsException {
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
     *  修改公社活动信息 - 管理后台
     *
     * @param communeActivity 公社活动
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改公社活动信息")
    public Result update(CommuneActivity communeActivity) throws FebsException {
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
     * @return {@link Result}<{@link CommuneActivity}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公社活动详情")
    public Result<CommuneActivity> detail(@PathVariable Integer id) {
        return Result.OK(this.communeActivityService.getById(id));
    }
}
