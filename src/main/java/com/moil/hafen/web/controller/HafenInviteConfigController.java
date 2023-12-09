package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.domain.InviteConfig;
import com.moil.hafen.web.service.InviteConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 管理后台/内部管理/基础规则管理/哈奋币规则/分销
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"inviteConfig"})
@Api(tags = "管理后台/内部管理/基础规则管理/哈奋币规则/分销")
public class HafenInviteConfigController extends BaseController {

    private String message;

    @Resource
    private InviteConfigService inviteConfigService;

    /**
     * 获取分销列表（分页）
     *
     * @param request      要求
     * @param inviteConfig 邀请配置
     * @return {@link Result}<{@link IPage}<{@link InviteConfig}>>
     */
    @GetMapping
    @ApiOperation("获取分销列表（分页）")
    public Result<IPage<InviteConfig>> page(QueryRequest request, InviteConfig inviteConfig) {
        IPage<InviteConfig> page = this.inviteConfigService.getPage(request, inviteConfig);
        return Result.OK(page);
    }
}
