package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.AboutUs;
import com.moil.hafen.web.service.AboutUsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"aboutUs"})
@Api(tags = "关于我们管理")
public class AboutUsController extends BaseController {

    private String message;

    @Resource
    private AboutUsService aboutUsService;



    @PutMapping
    @ApiOperation("修改关于我们信息")
    public Result update(AboutUs aboutUs) throws FebsException {
        try {
            return Result.OK(this.aboutUsService.updateById(aboutUs));
        } catch (Exception e) {
            message = "修改关于我们信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping
    @ApiOperation("获取关于我们详情")
    public Result<AboutUs> detail() {
        return Result.OK(this.aboutUsService.getOne(new LambdaQueryWrapper<AboutUs>().last("limit 1")));
    }

}
