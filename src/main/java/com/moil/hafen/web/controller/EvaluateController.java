package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Evaluate;
import com.moil.hafen.web.service.EvaluateService;
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
@RequestMapping({"evaluate"})
@Api(tags = "评论管理")
public class EvaluateController extends BaseController {

    private String message;

    @Resource
    private EvaluateService evaluateService;

    @GetMapping
    @ApiOperation("获取评论列表（分页）")
    public Map<String, Object> page(QueryRequest request, Evaluate evaluate) {
        IPage<Evaluate> page = this.evaluateService.getPage(request, evaluate);
        return getDataTable(page);
    }
    @PostMapping
    @ApiOperation("添加评论信息")
    public Result add(Evaluate evaluate) throws FebsException {
        try {
            evaluate.setCustomerId(JWTUtil.getCurrentCustomerId());
            evaluate.setCreateTime(new Date());
            return Result.OK(this.evaluateService.save(evaluate));
        } catch (Exception e) {
            message = "添加评论信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("通过ID获取评论详情")
    public Result<Evaluate> detail(@PathVariable Integer id) {
        return Result.OK(this.evaluateService.getById(id));
    }

}
