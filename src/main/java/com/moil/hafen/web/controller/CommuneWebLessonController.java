package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneWebLesson;
import com.moil.hafen.web.service.CommuneWebLessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理后台/公社模块/微官网课程管理
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeWebLesson"})
@Api(tags = "管理后台/公社模块/微官网课程管理")
public class CommuneWebLessonController extends BaseController {

    private String message;

    @Resource
    private CommuneWebLessonService communeWebLessonService;

    /**
     * 获取公社微官网课程列表 - 管理后台/小程序
     *
     * @return {@link Result}<{@link List}<{@link CommuneWebLesson}>>
     */
    @GetMapping
    @ApiOperation("获取公社微官网课程列表")
    public Result<List<CommuneWebLesson>> list() {
        List<CommuneWebLesson> list = communeWebLessonService.getList();
        return Result.OK(list);
    }

    /**
     * 添加公社微官网课程信息 - 管理后台
     *
     * @param communeWebLesson 社区网络课程
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加公社微官网课程信息")
    public Result add(@RequestBody CommuneWebLesson communeWebLesson) throws FebsException {
        try {
            return Result.OK(communeWebLessonService.save(communeWebLesson));
        } catch (Exception e) {
            message = "添加公社微官网课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除公社微官网课程信息 - 管理后台
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/delete")
    @ApiOperation("删除公社微官网课程信息")
    public Result delete(@RequestParam Integer id) throws FebsException {
        try {
            return Result.OK(communeWebLessonService.update(new LambdaUpdateWrapper<CommuneWebLesson>().eq(CommuneWebLesson::getId, id).set(CommuneWebLesson::getDelFlag, 1)));
        } catch (Exception e) {
            message = "删除公社微官网课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改公社微官网课程信息 - 管理后台
     *
     * @param communeWebLesson 社区网络课程
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/update")
    @ApiOperation("修改公社微官网课程信息")
    public Result update(@RequestBody List<CommuneWebLesson> communeWebLesson) throws FebsException {
        try {
            communeWebLessonService.update(new LambdaUpdateWrapper<CommuneWebLesson>().set(CommuneWebLesson::getDelFlag, 1));
            for (CommuneWebLesson lesson : communeWebLesson) {
                lesson.setModifyTime(new Date());
            }
            return Result.OK(communeWebLessonService.saveBatch(communeWebLesson));
        } catch (Exception e) {
            message = "修改公社微官网课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取公社微官网课程详情 - 管理后台/小程序
     *
     * @param id id
     *
     * @return {@link Result}<{@link CommuneWebLesson}>
     */
    @GetMapping("/detail")
    @ApiOperation("通过ID获取公社微官网课程详情")
    public Result<CommuneWebLesson> detail(@RequestParam Integer id) {
        return Result.OK(communeWebLessonService.getById(id));
    }

}
