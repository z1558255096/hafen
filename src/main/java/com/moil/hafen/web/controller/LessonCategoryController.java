package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.Objects;

/**
 * 管理后台-科技营-线下课程管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"t_lesson_category"})
@Api(tags = "课程类别管理")
public class LessonCategoryController extends BaseController {

    private String message;

    @Resource
    private LessonCategoryService lessonCategoryService;

    /**
     * 获取课程类目列表（分页）
     *
     * @param request        要求
     * @param lessonCategory 课程类别
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取课程类目列表（分页）", notes = "获取课程类目列表（分页）")
    public Result<IPage<LessonCategory>> page(@RequestBody QueryRequest request, @RequestBody LessonCategory lessonCategory) {
        IPage<LessonCategory> page = this.lessonCategoryService.getPage(request, lessonCategory);
        return Result.OK(page);
    }

    /**
     * 添加课程类目信息
     *
     * @param lessonCategory 课程类别
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/add")
    @ApiOperation("添加课程类目信息")
    public Result add(@RequestBody LessonCategory lessonCategory) throws FebsException {
        int count = lessonCategoryService.count(new LambdaQueryWrapper<LessonCategory>()
                .eq(LessonCategory::getName, lessonCategory.getName())
                .eq(LessonCategory::getType, lessonCategory.getType())
                .eq(LessonCategory::getDelFlag, 0));
        if (count > 0) {
            return Result.error("课程类目名称已存在");
        }
        lessonCategory.setCreateTime(new Date());
        lessonCategory.setModifyTime(new Date());
        return Result.OK(lessonCategoryService.save(lessonCategory));
    }

    /**
     * 删除课程类目信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除课程类目信息")
    public Result<Boolean> delete(@PathVariable Integer id) {
        LessonCategory lessonCategory = new LessonCategory();
        lessonCategory.setDelFlag(1);
        lessonCategory.setId(id);
        lessonCategory.setModifyTime(new Date());
        return Result.OK(lessonCategoryService.updateById(lessonCategory));
    }

    /**
     * 修改课程类目信息
     *
     * @param lessonCategory 课程类别
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改课程类目信息")
    public Result update(LessonCategory lessonCategory) {
        LessonCategory one = lessonCategoryService.getOne(new LambdaQueryWrapper<LessonCategory>()
                .eq(LessonCategory::getName, lessonCategory.getName())
                .eq(LessonCategory::getType, lessonCategory.getType())
                .eq(LessonCategory::getDelFlag, 0));
        if (one != null && !Objects.equals(one.getId(), lessonCategory.getId())) {
            return Result.error("课程类目名称已存在");
        }
        lessonCategory.setModifyTime(new Date());
        return Result.OK(lessonCategoryService.updateById(lessonCategory));
    }

    /**
     * 通过ID获取课程类目详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonCategory}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取课程类目详情")
    public Result<LessonCategory> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonCategoryService.getById(id));
    }

    /**
     * 获取课程类目列表
     *
     * @return {@link Result}
     */
    @GetMapping("/list")
    @ApiOperation("获取课程类目列表")
    public Result list() {
        List<LessonCategory> list = this.lessonCategoryService.list();
        return Result.OK(list);
    }

}
