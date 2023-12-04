package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonWebLesson;
import com.moil.hafen.web.service.LessonWebLessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理后台-营销管理-首页-课程
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonWebLesson"})
@Api(tags = "课程微官网课程信息管理")
public class LessonWebLessonController extends BaseController {

    private String message;

    @Resource
    private LessonWebLessonService lessonWebLessonService;

    /**
     * 获取课程微官网课程信息列表
     *
     * @param lessonWebLesson 课程网络课程
     *
     * @return {@link Result}<{@link List}<{@link LessonWebLesson}>>
     */
    @GetMapping
    @ApiOperation("获取课程微官网课程信息列表")
    public Result<List<LessonWebLesson>> list(LessonWebLesson lessonWebLesson) {

        List<LessonWebLesson> list = this.lessonWebLessonService.getList(lessonWebLesson);
        return Result.OK(list);
    }

    /**
     * 添加课程微官网课程信息信息
     *
     * @param lessonWebLesson 课程网络课程
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加课程微官网课程信息信息")
    public Result add(LessonWebLesson lessonWebLesson) throws FebsException {
        try {
            lessonWebLesson.setCreateTime(new Date());
            lessonWebLesson.setModifyTime(new Date());
            return Result.OK(this.lessonWebLessonService.save(lessonWebLesson));
        } catch (Exception e) {
            message = "添加课程微官网课程信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除课程微官网课程信息信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除课程微官网课程信息信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.lessonWebLessonService.removeById(id));
        } catch (Exception e) {
            message = "删除课程微官网课程信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改课程微官网课程信息信息
     *
     * @param lessonWebLesson 课程网络课程
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改课程微官网课程信息信息")
    public Result update(LessonWebLesson lessonWebLesson) throws FebsException {
        try {
            lessonWebLesson.setModifyTime(new Date());
            return Result.OK(this.lessonWebLessonService.updateById(lessonWebLesson));
        } catch (Exception e) {
            message = "修改课程微官网课程信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取课程微官网课程信息详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonWebLesson}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程微官网课程信息详情")
    public Result<LessonWebLesson> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonWebLessonService.getById(id));
    }

}
