package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.service.CommuneWebService;
import com.moil.hafen.web.vo.CommuneWebVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeWeb"})
@Api(tags = "公社微官网")
public class CommuneWebController extends BaseController {

    private String message;

    @Resource
    private CommuneWebService communeWebService;

    /**
     * 获取公社微官网 - 管理后台/小程序
     *
     * @return {@link Result}<{@link CommuneWebVo}>
     */
    @GetMapping
    @ApiOperation("获取公社微官网")
    public Result<CommuneWebVo> index() {

        CommuneWebVo index = this.communeWebService.index();
        return Result.OK(index);
    }

}
