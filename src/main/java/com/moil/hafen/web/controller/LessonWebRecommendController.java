package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonWebRecommend;
import com.moil.hafen.web.service.LessonWebRecommendService;
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
@RequestMapping({"lessonWebRecommend"})
@Api(tags = "课程微官网推荐信息管理")
public class LessonWebRecommendController extends BaseController {

    private String message;

    @Resource
    private LessonWebRecommendService lessonWebRecommendService;

    @GetMapping
    @ApiOperation("获取课程微官网推荐信息列表")
    public Result<List<LessonWebRecommend>> list(Integer campusId) {

        List<LessonWebRecommend> list = this.lessonWebRecommendService.list(new LambdaQueryWrapper<LessonWebRecommend>().eq(LessonWebRecommend::getCampusId,campusId));
        return Result.OK(list);
    }

    @PostMapping
    @ApiOperation("添加课程微官网推荐信息信息")
    public Result add(LessonWebRecommend lessonWebRecommend) throws FebsException {
        try {
            lessonWebRecommend.setCreateTime(new Date());
            lessonWebRecommend.setModifyTime(new Date());
            return Result.OK(this.lessonWebRecommendService.save(lessonWebRecommend));
        } catch (Exception e) {
            message = "添加课程微官网推荐信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除课程微官网推荐信息信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.lessonWebRecommendService.removeById(id));
        } catch (Exception e) {
            message = "删除课程微官网推荐信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改课程微官网推荐信息信息")
    public Result update(LessonWebRecommend lessonWebRecommend) throws FebsException {
        try {
            lessonWebRecommend.setModifyTime(new Date());
            return Result.OK(this.lessonWebRecommendService.updateById(lessonWebRecommend));
        } catch (Exception e) {
            message = "修改课程微官网推荐信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程微官网推荐信息详情")
    public Result<LessonWebRecommend> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonWebRecommendService.getById(id));
    }

}
