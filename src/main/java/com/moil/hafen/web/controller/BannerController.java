package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Banner;
import com.moil.hafen.web.service.BannerService;
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
@RequestMapping({"banner"})
@Api(tags = "banner管理")
public class BannerController extends BaseController {

    private String message;

    @Resource
    private BannerService bannerService;

    @GetMapping
    @ApiOperation("获取banner列表（分页）")
    public Map<String, Object> page(QueryRequest request, Banner banner) {
        IPage<Banner> page = this.bannerService.getPage(request, banner);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加banner信息")
    public Result add(Banner banner) throws FebsException {
        try {
            banner.setCreateTime(new Date());
            banner.setModifyTime(new Date());
            banner.setDelFlag(0);
            return Result.OK(this.bannerService.save(banner));
        } catch (Exception e) {
            message = "添加banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除banner信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            Banner banner = bannerService.getById(id);
            banner.setDelFlag(1);
            banner.setModifyTime(new Date());
            return Result.OK(this.bannerService.updateById(banner));
        } catch (Exception e) {
            message = "删除banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改banner信息")
    public Result update(Banner banner) throws FebsException {
        try {
            banner.setModifyTime(new Date());
            return Result.OK(this.bannerService.updateById(banner));
        } catch (Exception e) {
            message = "修改banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架banner")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            Banner banner = bannerService.getById(id);
            banner.setStatus(status);
            banner.setModifyTime(new Date());
            return Result.OK(this.bannerService.updateById(banner));
        } catch (Exception e) {
            message = "上下架banner失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取banner详情")
    public Result<Banner> detail(@PathVariable Integer id) {
        return Result.OK(this.bannerService.getById(id));
    }

}
