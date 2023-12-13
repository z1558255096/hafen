package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneRefundRulesConfig;
import com.moil.hafen.web.service.CommuneRefundRulesConfigService;
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
@RequestMapping({"communeRefundRulesConfig"})
@Api(tags = "管理后台/内部管理/基础规则管理/公社退款规则配置")
public class CommuneRefundRulesConfigController extends BaseController {

    private String message;

    @Resource
    private CommuneRefundRulesConfigService communeRefundRulesConfigService;

    /**
     * 获取公社退款规则配置列表 - 管理后台/小程序
     *
     * @return {@link Result}
     */
    @GetMapping
    @ApiOperation("获取公社退款规则配置列表")
    public Result<List<CommuneRefundRulesConfig>> list() {
        List<CommuneRefundRulesConfig> list = this.communeRefundRulesConfigService.list();
        return Result.OK(list);
    }

    /**
     * 保存公社退款规则配置
     *
     * @param communeRefundRulesConfigList 社区退款规则配置
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("add")
    @ApiOperation("保存公社退款规则配置")
    public Result add(@RequestBody List<CommuneRefundRulesConfig> communeRefundRulesConfigList, @RequestParam("type") Integer type) throws FebsException {
        try {
            LambdaQueryWrapper<CommuneRefundRulesConfig> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CommuneRefundRulesConfig::getType, type);
            communeRefundRulesConfigService.remove(queryWrapper);
            for (CommuneRefundRulesConfig config : communeRefundRulesConfigList) {
                config.setCreateTime(new Date());
                config.setModifyTime(new Date());
            }
            this.communeRefundRulesConfigService.saveBatch(communeRefundRulesConfigList);
            return Result.OK();
        } catch (Exception e) {
            message = "保存公社退款规则配置状态失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改公社退款规则配置 - 管理后台
     *
     * @param communeRefundRulesConfig 社区退款规则配置
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("update")
    @ApiOperation("修改公社退款规则配置")
    public Result update(@RequestBody CommuneRefundRulesConfig communeRefundRulesConfig) throws FebsException {
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
