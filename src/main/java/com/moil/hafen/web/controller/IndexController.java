package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.service.IndexService;
import com.moil.hafen.web.vo.IndexVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 小程序-首页
 *
 * @author song
 */
@Slf4j
@RestController
@RequestMapping({"index"})
@Api(tags = "小程序-首页")
public class IndexController extends BaseController {

    private String message;

    @Resource
    private IndexService indexService;

    /**
     * 获取首页数据
     *
     * @return {@link Result}<{@link IndexVo}>
     */
    @GetMapping
    @ApiOperation("获取首页数据")
    public Result<IndexVo> detail() {
        return Result.OK(this.indexService.getIndex());
    }

}

