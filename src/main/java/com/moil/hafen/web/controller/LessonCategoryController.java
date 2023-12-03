package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonCategory;
import com.moil.hafen.web.service.LessonCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"t_lesson_category"})
@Api(tags = "课程类目管理")
public class LessonCategoryController extends BaseController {

    private String message;

    @Resource
    private LessonCategoryService lessonCategoryService;

    @GetMapping
    @ApiOperation("获取课程类目列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonCategory lessonCategory) {
        IPage<LessonCategory> page = this.lessonCategoryService.getPage(request, lessonCategory);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加课程类目信息")
    public Result add(LessonCategory lessonCategory) throws FebsException {
        try {
            lessonCategory.setCreateTime(new Date());
            lessonCategory.setModifyTime(new Date());
            lessonCategory.setDelFlag(0);
            return Result.OK(this.lessonCategoryService.save(lessonCategory));
        } catch (Exception e) {
            message = "添加课程类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除课程类目信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            LessonCategory lessonCategory = new LessonCategory();
            lessonCategory.setDelFlag(1);
            lessonCategory.setId(id);
            lessonCategory.setModifyTime(new Date());
            return Result.OK(this.lessonCategoryService.updateById(lessonCategory));
        } catch (Exception e) {
            message = "删除课程类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改课程类目信息")
    public Result update(LessonCategory lessonCategory) throws FebsException {
        try {
            lessonCategory.setModifyTime(new Date());
            return Result.OK(this.lessonCategoryService.updateById(lessonCategory));
        } catch (Exception e) {
            message = "修改课程类目信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程类目详情")
    public Result<LessonCategory> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonCategoryService.getById(id));
    }
    @GetMapping("/list")
    @ApiOperation("获取课程类目列表")
    public Result list() {
        List<LessonCategory> list = this.lessonCategoryService.list();
        return Result.OK(list);
    }

}
