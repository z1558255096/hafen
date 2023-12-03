package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonWebBanner;
import com.moil.hafen.web.service.LessonWebBannerService;
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
@RequestMapping({"lessonWebBanner"})
@Api(tags = "课程微官网banner管理")
public class LessonWebBannerController extends BaseController {

    private String message;

    @Resource
    private LessonWebBannerService lessonWebBannerService;

    @GetMapping
    @ApiOperation("获取课程微官网banner列表")
    public Result<List<LessonWebBanner>> list(Integer campusId) {

        List<LessonWebBanner> list = this.lessonWebBannerService.list(new LambdaQueryWrapper<LessonWebBanner>().eq(LessonWebBanner::getCampusId,campusId));
        return Result.OK(list);
    }

    @PostMapping
    @ApiOperation("添加课程微官网banner信息")
    public Result add(LessonWebBanner lessonWebBanner) throws FebsException {
        try {
            lessonWebBanner.setCreateTime(new Date());
            lessonWebBanner.setModifyTime(new Date());
            return Result.OK(this.lessonWebBannerService.save(lessonWebBanner));
        } catch (Exception e) {
            message = "添加课程微官网banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除课程微官网banner信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.lessonWebBannerService.removeById(id));
        } catch (Exception e) {
            message = "删除课程微官网banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改课程微官网banner信息")
    public Result update(LessonWebBanner lessonWebBanner) throws FebsException {
        try {
            lessonWebBanner.setModifyTime(new Date());
            return Result.OK(this.lessonWebBannerService.updateById(lessonWebBanner));
        } catch (Exception e) {
            message = "修改课程微官网banner信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程微官网banner详情")
    public Result<LessonWebBanner> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonWebBannerService.getById(id));
    }

}
