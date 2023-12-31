package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonWebTeacher;
import com.moil.hafen.web.service.LessonWebTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理后台-课程微官网管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonWebTeacher"})
@Api(tags = "课程微官网师资介绍信息管理")
public class LessonWebTeacherController extends BaseController {

    private String message;

    @Resource
    private LessonWebTeacherService lessonWebTeacherService;

    /**
     * 获取课程微官网师资介绍信息列表
     *
     * @param campusId 校园id
     *
     * @return {@link Result}<{@link List}<{@link LessonWebTeacher}>>
     */
    @GetMapping
    @ApiOperation("获取课程微官网师资介绍信息列表")
    public Result<List<LessonWebTeacher>> list(Integer campusId) {

        List<LessonWebTeacher> list = this.lessonWebTeacherService.list(new LambdaQueryWrapper<LessonWebTeacher>().eq(LessonWebTeacher::getCampusId,campusId));
        return Result.OK(list);
    }

    /**
     * 添加课程微官网师资介绍信息信息
     *
     * @param lessonWebTeacher 课程网络教师
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加课程微官网师资介绍信息信息")
    public Result add(LessonWebTeacher lessonWebTeacher) throws FebsException {
        try {
            lessonWebTeacher.setCreateTime(new Date());
            lessonWebTeacher.setModifyTime(new Date());
            return Result.OK(this.lessonWebTeacherService.save(lessonWebTeacher));
        } catch (Exception e) {
            message = "添加课程微官网师资介绍信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除课程微官网师资介绍信息信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除课程微官网师资介绍信息信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.lessonWebTeacherService.removeById(id));
        } catch (Exception e) {
            message = "删除课程微官网师资介绍信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改课程微官网师资介绍信息信息
     *
     * @param lessonWebTeacher 课程网络教师
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改课程微官网师资介绍信息信息")
    public Result update(LessonWebTeacher lessonWebTeacher) throws FebsException {
        try {
            lessonWebTeacher.setModifyTime(new Date());
            return Result.OK(this.lessonWebTeacherService.updateById(lessonWebTeacher));
        } catch (Exception e) {
            message = "修改课程微官网师资介绍信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取课程微官网师资介绍信息详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonWebTeacher}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程微官网师资介绍信息详情")
    public Result<LessonWebTeacher> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonWebTeacherService.getById(id));
    }

}
