package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneRefundRulesConfig;
import com.moil.hafen.web.service.CommuneRefundRulesConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeRefundRulesConfig"})
@Api(tags = "公社退款规则管理")
public class CommuneRefundRulesConfigController extends BaseController {

    private String message;

    @Resource
    private CommuneRefundRulesConfigService communeRefundRulesConfigService;

    @GetMapping
    @ApiOperation("获取公社退款规则配置列表")
    public Result list() {
        List<CommuneRefundRulesConfig> list = this.communeRefundRulesConfigService.list();
        return Result.OK(list);
    }

    @PutMapping
    @ApiOperation("修改公社退款规则配置状态")
    public Result update(CommuneRefundRulesConfig communeRefundRulesConfig) throws FebsException {
        try {
            communeRefundRulesConfig.setModifyTime(new Date());
            return Result.OK(this.communeRefundRulesConfigService.updateById(communeRefundRulesConfig));
        } catch (Exception e) {
            message = "修改公社退款规则配置状态失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
}
