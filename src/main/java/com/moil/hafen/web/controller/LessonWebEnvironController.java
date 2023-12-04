package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonWebEnviron;
import com.moil.hafen.web.service.LessonWebEnvironService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理后台-内部管理-校区管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonWebEnviron"})
@Api(tags = "课程微官网校区环境信息管理")
public class LessonWebEnvironController extends BaseController {

    private String message;

    @Resource
    private LessonWebEnvironService lessonWebEnvironService;

    /**
     * 获取课程微官网校区环境信息列表
     *
     * @param campusId 校园id
     *
     * @return {@link Result}<{@link List}<{@link LessonWebEnviron}>>
     */
    @GetMapping
    @ApiOperation("获取课程微官网校区环境信息列表")
    public Result<List<LessonWebEnviron>> list(Integer campusId) {

        List<LessonWebEnviron> list = this.lessonWebEnvironService.list(new LambdaQueryWrapper<LessonWebEnviron>().eq(LessonWebEnviron::getCampusId,campusId));
        return Result.OK(list);
    }

    /**
     * 添加课程微官网校区环境信息信息
     *
     * @param lessonWebEnviron 课程网络环境
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加课程微官网校区环境信息信息")
    public Result add(LessonWebEnviron lessonWebEnviron) throws FebsException {
        try {
            lessonWebEnviron.setCreateTime(new Date());
            lessonWebEnviron.setModifyTime(new Date());
            return Result.OK(this.lessonWebEnvironService.save(lessonWebEnviron));
        } catch (Exception e) {
            message = "添加课程微官网校区环境信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除课程微官网校区环境信息信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除课程微官网校区环境信息信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.lessonWebEnvironService.removeById(id));
        } catch (Exception e) {
            message = "删除课程微官网校区环境信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改课程微官网校区环境信息信息
     *
     * @param lessonWebEnviron 课程网络环境
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改课程微官网校区环境信息信息")
    public Result update(LessonWebEnviron lessonWebEnviron) throws FebsException {
        try {
            lessonWebEnviron.setModifyTime(new Date());
            return Result.OK(this.lessonWebEnvironService.updateById(lessonWebEnviron));
        } catch (Exception e) {
            message = "修改课程微官网校区环境信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取课程微官网校区环境信息详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonWebEnviron}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程微官网校区环境信息详情")
    public Result<LessonWebEnviron> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonWebEnvironService.getById(id));
    }

}
