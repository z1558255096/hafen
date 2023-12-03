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
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"channel"})
@Api(tags = "渠道管理")
public class ChannelController extends BaseController {

    private String message;

    @Resource
    private ChannelService channelService;

    @GetMapping
    @ApiOperation("获取渠道列表（分页）")
    public Map<String, Object> page(QueryRequest request, Channel channel) {
        IPage<Channel> page = this.channelService.getPage(request, channel);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加渠道信息")
    public Result add(Channel channel) throws FebsException {
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

    @DeleteMapping("/{id}")
    @ApiOperation("删除渠道信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.channelService.removeById(id));
        } catch (Exception e) {
            message = "删除渠道信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改渠道信息")
    public Result update(Channel channel) throws FebsException {
        try {
            channel.setModifyTime(new Date());
            return Result.OK(this.channelService.updateById(channel));
        } catch (Exception e) {
            message = "修改渠道信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取渠道详情")
    public Result<Channel> detail(@PathVariable Integer id) {
        return Result.OK(this.channelService.getById(id));
    }
    @GetMapping("/list")
    @ApiOperation("获取渠道列表")
    public Result list() {
        List<Channel> list = this.channelService.list();
        return Result.OK(list);
    }

}
