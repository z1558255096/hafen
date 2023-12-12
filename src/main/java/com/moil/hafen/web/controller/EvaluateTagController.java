package com.moil.hafen.web.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.enums.Status;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.EvaluateTag;
import com.moil.hafen.web.service.EvaluateTagService;
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
@RequestMapping({"evaluateTag"})
@Api(tags = "管理后台/内部管理/基础规则管理/课堂点评评价体系管理")
public class EvaluateTagController extends BaseController {

    private String message;

    @Resource
    private EvaluateTagService evaluateTagService;

    @GetMapping
    @ApiOperation("获取课堂点评评价体系列表（分页）")
    public Result<IPage<EvaluateTag>> page(QueryRequest request, EvaluateTag evaluateTag) {
        IPage<EvaluateTag> page = this.evaluateTagService.getPage(request, evaluateTag);
        return Result.OK(page);
    }

    @PostMapping("add")
    @ApiOperation("添加课堂点评评价体系信息")
    public Result<Object> add(@RequestBody EvaluateTag evaluateTag) throws FebsException {
        try {
            LambdaQueryWrapper<EvaluateTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EvaluateTag::getType, evaluateTag.getType());
            queryWrapper.eq(EvaluateTag::getName, evaluateTag.getName());
            EvaluateTag tag = evaluateTagService.getOne(queryWrapper);
            if (ObjectUtil.isNotEmpty(tag)) {
                return Result.error("该评分名称已存在,请勿重复");
            }
            evaluateTag.setCreateTime(new Date());
            evaluateTag.setModifyTime(new Date());
            evaluateTag.setState(Status.上架.state);
            return Result.OK(this.evaluateTagService.save(evaluateTag));
        } catch (Exception e) {
            message = "添加课堂点评评价体系信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("delete")
    @ApiOperation("删除课堂点评评价体系信息")
    public Result<Object> delete(@RequestParam("id") Integer id) throws FebsException {
        try {
            return Result.OK(this.evaluateTagService.removeById(id));
        } catch (Exception e) {
            message = "删除课堂点评评价体系信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping("update")
    @ApiOperation("修改课堂点评评价体系信息")
    public Result<Object> update(@RequestBody EvaluateTag evaluateTag) throws FebsException {
        try {
            LambdaQueryWrapper<EvaluateTag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EvaluateTag::getType, evaluateTag.getType());
            queryWrapper.eq(EvaluateTag::getName, evaluateTag.getName());
            EvaluateTag tag = evaluateTagService.getOne(queryWrapper);
            if (ObjectUtil.isNotEmpty(tag)) {
                return Result.error("该评分名称已存在,请勿重复");
            }
            evaluateTag.setModifyTime(new Date());
            return Result.OK(this.evaluateTagService.updateById(evaluateTag));
        } catch (Exception e) {
            message = "修改课堂点评评价体系信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping("detail")
    @ApiOperation("通过ID获取课堂点评评价体系详情")
    public Result<EvaluateTag> detail(@RequestParam("id") Integer id) {
        return Result.OK(this.evaluateTagService.getById(id));
    }

    @GetMapping("/list")
    @ApiOperation("获取课堂点评评价体系列表")
    public Result<List<EvaluateTag>> list() {
        List<EvaluateTag> list = this.evaluateTagService.list();
        return Result.OK(list);
    }
}
