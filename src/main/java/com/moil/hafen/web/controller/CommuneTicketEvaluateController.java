package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneTicketEvaluate;
import com.moil.hafen.web.service.CommuneTicketEvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 管理后台/公社模块/公社门票评价管理
 *
 * @author SongZichen
 * @since 2023年12月06日 11:51:20
 **/
@Slf4j
@RestController
@RequestMapping({"communeTicketEvaluate"})
@Api(tags = "公社门票评价管理")
public class CommuneTicketEvaluateController {
    @Resource
    private CommuneTicketEvaluateService communeTicketEvaluateService;

    /**
     * 获取公社门票评价列表（分页） - 管理后台/小程序
     *
     * @param request               要求
     * @param communeTicketEvaluate 评价
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取公社门票评价列表（分页）")
    public Result page(QueryRequest request, CommuneTicketEvaluate communeTicketEvaluate) {
        IPage<CommuneTicketEvaluate> page = communeTicketEvaluateService.getPage(request, communeTicketEvaluate);
        return Result.OK(page);
    }

    /**
     * 添加公社门票信息 - 管理后台
     *
     * @param communeTicketEvaluate 评价
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加公社门票评价信息")
    public Result add(@RequestBody CommuneTicketEvaluate communeTicketEvaluate) throws FebsException {
        try {
            communeTicketEvaluate.setCreateTime(new Date());
            communeTicketEvaluate.setModifyTime(new Date());
            communeTicketEvaluateService.save(communeTicketEvaluate);
            return Result.OK();
        } catch (Exception e) {
            return Result.error("添加公社门票评价信息失败");
        }
    }


    /**
     * 上下架公社门票评价 - 管理后台
     *
     * @param id     id
     * @param status 地位
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架公社门票评价")
    public Result changeStatus(@PathVariable Integer id, int status) throws FebsException {
        try {
            CommuneTicketEvaluate communeTicketEvaluate = new CommuneTicketEvaluate();
            communeTicketEvaluate.setStatus(status);
            communeTicketEvaluate.setId(id);
            communeTicketEvaluate.setModifyTime(new Date());
            return Result.OK(communeTicketEvaluateService.updateById(communeTicketEvaluate));
        } catch (Exception e) {
            return Result.error("上下架公社门票失败");
        }
    }


    /**
     * 通过ID获取公社门票评价详情 - 管理后台/小程序
     *
     * @param id id
     *
     * @return {@link Result}<{@link CommuneTicketEvaluate}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公社门票评价详情")
    public Result<CommuneTicketEvaluate> detail(@PathVariable Integer id) {
        CommuneTicketEvaluate byId = communeTicketEvaluateService.getById(id);
        return Result.OK(byId);
    }

}
