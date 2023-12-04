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
 * 课程微官网banner管理
 *
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

    /**
     * 获取课程微官网banner列表
     *
     * @param campusId 校园id
     *
     * @return {@link Result}<{@link List}<{@link LessonWebBanner}>>
     */
    @GetMapping
    @ApiOperation("获取课程微官网banner列表")
    public Result<List<LessonWebBanner>> list(Integer campusId) {

        List<LessonWebBanner> list = this.lessonWebBannerService.list(new LambdaQueryWrapper<LessonWebBanner>().eq(LessonWebBanner::getCampusId, campusId));
        return Result.OK(list);
    }

    /**
     * 添加课程微官网banner信息
     *
     * @param lessonWebBanner 课程网页横幅
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
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

    /**
     * 删除课程微官网banner信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
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

    /**
     * 修改课程微官网banner信息
     *
     * @param lessonWebBanner 课程网页横幅
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
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

    /**
     * 通过ID获取课程微官网banner详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonWebBanner}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程微官网banner详情")
    public Result<LessonWebBanner> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonWebBannerService.getById(id));
    }

}
