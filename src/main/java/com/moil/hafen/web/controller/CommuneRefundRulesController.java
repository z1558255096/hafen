package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneRefundRules;
import com.moil.hafen.web.service.CommuneRefundRulesService;
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
@RequestMapping({"communeRefundRules"})
@Api(tags = "公社退款规则管理")
public class CommuneRefundRulesController extends BaseController {

    private String message;

    @Resource
    private CommuneRefundRulesService communeRefundRulesService;

    @GetMapping
    @ApiOperation("获取公社退款规则列表")
    public Result list() {
        List<CommuneRefundRules> list = this.communeRefundRulesService.list();
        return Result.OK(list);
    }

    @PostMapping
    @ApiOperation("添加公社退款规则信息")
    public Result add(CommuneRefundRules communeRefundRules) throws FebsException {
        try {
            communeRefundRules.setCreateTime(new Date());
            communeRefundRules.setModifyTime(new Date());
            return Result.OK(this.communeRefundRulesService.save(communeRefundRules));
        } catch (Exception e) {
            message = "添加公社退款规则信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除公社退款规则信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.communeRefundRulesService.removeById(id));
        } catch (Exception e) {
            message = "删除公社退款规则信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改公社退款规则信息")
    public Result update(CommuneRefundRules communeRefundRules) throws FebsException {
        try {
            communeRefundRules.setModifyTime(new Date());
            return Result.OK(this.communeRefundRulesService.updateById(communeRefundRules));
        } catch (Exception e) {
            message = "修改公社退款规则信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公社退款规则详情")
    public Result<CommuneRefundRules> detail(@PathVariable Integer id) {
        return Result.OK(this.communeRefundRulesService.getById(id));
    }

}
