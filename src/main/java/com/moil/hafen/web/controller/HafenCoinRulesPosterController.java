package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.HafenCoinRulesPoster;
import com.moil.hafen.web.service.HafenCoinRulesPosterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"hafenCoinRulesPoster"})
@Api(tags = "哈奋币规则-海报")
public class HafenCoinRulesPosterController extends BaseController {

    private String message;

    @Resource
    private HafenCoinRulesPosterService hafenCoinRulesPosterService;

    @PutMapping
    @ApiOperation("修改哈奋币规则信息")
    public Result update(HafenCoinRulesPoster hafenCoinRulesPoster) throws FebsException {
        try {
            hafenCoinRulesPoster.setModifyTime(new Date());
            return Result.OK(this.hafenCoinRulesPosterService.updateById(hafenCoinRulesPoster));
        } catch (Exception e) {
            message = "修改哈奋币规则信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping
    @ApiOperation("获取规则海报")
    public Result<HafenCoinRulesPoster> detail() {
        return Result.OK(this.hafenCoinRulesPosterService.getOne(new LambdaQueryWrapper<HafenCoinRulesPoster>().last("limit 1")));
    }

}
