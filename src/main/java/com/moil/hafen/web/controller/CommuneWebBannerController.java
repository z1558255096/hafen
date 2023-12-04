package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneWebBanner;
import com.moil.hafen.web.service.CommuneWebBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeWebBanner"})
@Api(tags = "公社微官网banner管理")
public class CommuneWebBannerController extends BaseController {

    private String message;

    @Resource
    private CommuneWebBannerService communeWebBannerService;

    /**
     * 获取公社微官网banner列表 - 管理后台/小程序
     *
     * @return {@link Result}<{@link List}<{@link CommuneWebBanner}>>
     */
    @GetMapping
    @ApiOperation("获取公社微官网banner列表")
    public Result<List<CommuneWebBanner>> list() {

        List<CommuneWebBanner> list = this.communeWebBannerService.list();
        return Result.OK(list);
    }

    /**
     * 添加公社微官网banner信息 - 管理后台
     *
     * @param communeWebBanner 社区网络横幅
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加公社微官网banner信息")
    public Result add(CommuneWebBanner communeWebBanner) throws FebsException {
        try {
            communeWebBanner.setCreateTime(new Date());
            communeWebBanner.setModifyTime(new Date());
            return Result.OK(this.communeWebBannerService.save(communeWebBanner));
        } catch (Exception e) {
            message = "添加公社微官网banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除公社微官网banner信息 - 管理后台
     *
     * @param id id
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除公社微官网banner信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.communeWebBannerService.removeById(id));
        } catch (Exception e) {
            message = "删除公社微官网banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改公社微官网banner信息 - 管理后台
     *
     * @param communeWebBanner 社区网络横幅
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改公社微官网banner信息")
    public Result update(CommuneWebBanner communeWebBanner) throws FebsException {
        try {
            communeWebBanner.setModifyTime(new Date());
            return Result.OK(this.communeWebBannerService.updateById(communeWebBanner));
        } catch (Exception e) {
            message = "修改公社微官网banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取公社微官网banner详情 - 管理后台/小程序
     *
     * @param id id
     * @return {@link Result}<{@link CommuneWebBanner}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公社微官网banner详情")
    public Result<CommuneWebBanner> detail(@PathVariable Integer id) {
        return Result.OK(this.communeWebBannerService.getById(id));
    }

}
