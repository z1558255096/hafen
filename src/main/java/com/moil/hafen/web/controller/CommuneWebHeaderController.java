package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneWebHeader;
import com.moil.hafen.web.service.CommuneWebHeaderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeWebHeader"})
@Api(tags = "公社微官网头部信息管理")
public class CommuneWebHeaderController extends BaseController {

    private String message;

    @Resource
    private CommuneWebHeaderService communeWebHeaderService;


    /**
     * 获取公社微官网头部信息列表 - 管理后台/小程序
     *
     * @param communeWebHeader 社区web标头
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改公社微官网头部信息信息")
    public Result update(CommuneWebHeader communeWebHeader) throws FebsException {
        try {
            communeWebHeader.setModifyTime(new Date());
            return Result.OK(this.communeWebHeaderService.update(communeWebHeader,new LambdaUpdateWrapper<CommuneWebHeader>().gt(CommuneWebHeader::getId,0)));
        } catch (Exception e) {
            message = "修改公社微官网头部信息信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取公社微官网头部信息详情 - 管理后台/小程序
     *
     * @param id id
     * @return {@link Result}<{@link CommuneWebHeader}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公社微官网头部信息详情")
    public Result<CommuneWebHeader> detail(@PathVariable Integer id) {
        return Result.OK(this.communeWebHeaderService.getById(id));
    }

}
