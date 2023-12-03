package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.InviteConfig;
import com.moil.hafen.web.service.InviteConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"inviteConfig"})
@Api(tags = "哈奋币规则-分销")
public class InviteConfigController extends BaseController {

    private String message;

    @Resource
    private InviteConfigService inviteConfigService;

    @GetMapping
    @ApiOperation("获取分销列表（分页）")
    public Map<String, Object> page(QueryRequest request, InviteConfig inviteConfig) {
        IPage<InviteConfig> page = this.inviteConfigService.getPage(request, inviteConfig);
        return getDataTable(page);
    }

}
