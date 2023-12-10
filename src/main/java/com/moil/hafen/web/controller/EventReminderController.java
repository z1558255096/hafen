package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.EventReminder;
import com.moil.hafen.web.service.EventReminderService;
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
@RequestMapping({"eventReminder"})
@Api(tags = "管理后台/内部管理/基础规则管理/家校设置")
public class EventReminderController extends BaseController {

    private String message;

    @Resource
    private EventReminderService eventReminderService;

    @GetMapping
    @ApiOperation("获取家校设置列表")
    public Result<List<EventReminder>> list() {
        List<EventReminder> list = this.eventReminderService.list();
        return Result.OK(list);
    }

    @PostMapping("add")
    @ApiOperation("添加家校设置信息")
    public Result add(@RequestBody EventReminder eventReminder){
        try {
            eventReminder.setCreateTime(new Date());
            eventReminder.setModifyTime(new Date());
            return Result.OK(this.eventReminderService.save(eventReminder));
        } catch (Exception e) {
            message = "添加家校设置信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改家校设置信息")
    public Result update(@RequestBody EventReminder eventReminder) throws FebsException {
        try {
            eventReminder.setModifyTime(new Date());
            return Result.OK(this.eventReminderService.updateById(eventReminder));
        } catch (Exception e) {
            message = "修改家校设置信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
}
