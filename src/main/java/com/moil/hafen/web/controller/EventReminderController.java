package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.EventReminder;
import com.moil.hafen.web.service.EventReminderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"eventReminder"})
@Api(tags = "家校设置-提醒规则")
public class EventReminderController extends BaseController {

    private String message;

    @Resource
    private EventReminderService eventReminderService;

    @GetMapping
    @ApiOperation("获取家校设置列表")
    public Result list() {
        List<EventReminder> list = this.eventReminderService.list();
        return Result.OK(list);
    }
    @PutMapping
    @ApiOperation("修改家校设置信息")
    public Result update(EventReminder eventReminder) throws FebsException {
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
