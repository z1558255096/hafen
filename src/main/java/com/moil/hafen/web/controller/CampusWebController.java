package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.service.CampusWebService;
import com.moil.hafen.web.vo.CampusWebVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping({"campusWeb"})
@Api(tags = "校区微官网")
public class CampusWebController extends BaseController {

    private String message;

    @Resource
    private CampusWebService campusWebService;

    /**
     * 获取微官网信息 - 管理后台/小程序
     *
     * @param campusId 校园id
     * @return {@link Result}
     */
    @GetMapping
    @ApiOperation("获取微官网信息")
    public Result info(Integer campusId) {

        CampusWebVo campusWebVo = this.campusWebService.info(campusId);
        return Result.OK(campusWebVo);
    }


}
