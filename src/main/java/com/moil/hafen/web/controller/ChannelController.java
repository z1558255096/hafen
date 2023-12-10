package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Channel;
import com.moil.hafen.web.service.ChannelService;
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
@RequestMapping({"channel"})
@Api(tags = "管理后台/内部管理/基础规则管理/渠道管理")
public class ChannelController extends BaseController {

    private String message;

    @Resource
    private ChannelService channelService;

    /**
     * 获取渠道列表（分页） - 管理后台
     *
     * @param request 要求
     * @param channel 频道
     * @return {@link Result}<{@link IPage}<{@link Channel}>>
     */
    @GetMapping
    @ApiOperation("获取渠道列表（分页）")
    public Result<IPage<Channel>> page(QueryRequest request, Channel channel) {
        IPage<Channel> page = this.channelService.getPage(request, channel);
        return Result.OK(page);
    }

    /**
     * 添加渠道信息 - 管理后台/小程序
     *
     * @param channel 频道
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加渠道信息")
    public Result add(@RequestBody Channel channel) throws FebsException {
        try {
            channel.setCreateTime(new Date());
            channel.setModifyTime(new Date());
            return Result.OK(this.channelService.save(channel));
        } catch (Exception e) {
            message = "添加渠道信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除渠道信息 - 管理后台
     *
     * @param id id
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping
    @ApiOperation("删除渠道信息")
    public Result delete(@RequestParam("id") Integer id) throws FebsException {
        try {
            return Result.OK(this.channelService.removeById(id));
        } catch (Exception e) {
            message = "删除渠道信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改渠道信息 - 管理后台
     *
     * @param channel 频道
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改渠道信息")
    public Result update(@RequestBody Channel channel) throws FebsException {
        try {
            channel.setModifyTime(new Date());
            return Result.OK(this.channelService.updateById(channel));
        } catch (Exception e) {
            message = "修改渠道信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取渠道详情 （管理后台/小程序）
     *
     * @param id id
     * @return {@link Result}<{@link Channel}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取渠道详情")
    public Result<Channel> detail(@PathVariable Integer id) {
        return Result.OK(this.channelService.getById(id));
    }

    /**
     * 获取渠道列表（不分页） - 小程序
     *
     * @return {@link Result}
     */
    @GetMapping("/list")
    @ApiOperation("获取渠道列表")
    public Result list() {
        List<Channel> list = this.channelService.list();
        return Result.OK(list);
    }

}
