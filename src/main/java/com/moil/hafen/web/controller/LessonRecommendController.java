package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonRecommend;
import com.moil.hafen.web.service.LessonRecommendService;
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
@RequestMapping({"lessonRecommend"})
@Api(tags = "课程推荐管理")
public class LessonRecommendController extends BaseController {

    private String message;

    @Resource
    private LessonRecommendService lessonRecommendService;

    @GetMapping
    @ApiOperation("获取课程推荐列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonRecommend lessonRecommend) {
        IPage<LessonRecommend> page = this.lessonRecommendService.getPage(request, lessonRecommend);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加课程推荐信息")
    public Result add(LessonRecommend lessonRecommend) throws FebsException {
        try {
            lessonRecommend.setCreateTime(new Date());
            return Result.OK(this.lessonRecommendService.save(lessonRecommend));
        } catch (Exception e) {
            message = "添加课程推荐信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除课程推荐信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.lessonRecommendService.removeById(id));
        } catch (Exception e) {
            message = "删除课程推荐信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改课程推荐信息")
    public Result update(LessonRecommend lessonRecommend) throws FebsException {
        try {
            return Result.OK(this.lessonRecommendService.updateById(lessonRecommend));
        } catch (Exception e) {
            message = "修改课程推荐信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("修改课程推荐上下架信息")
    public Result changeStatus(@PathVariable Integer id, Integer status) throws FebsException {
        try {

            LessonRecommend lessonRecommend = this.lessonRecommendService.getById(id);
            lessonRecommend.setStatus(status);
            return Result.OK(this.lessonRecommendService.updateById(lessonRecommend));
        } catch (Exception e) {
            message = "修改课程推荐上下架信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程推荐详情")
    public Result<LessonRecommend> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonRecommendService.getById(id));
    }

}
