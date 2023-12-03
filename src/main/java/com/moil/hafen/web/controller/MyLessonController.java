package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.service.MyLessonService;
import com.moil.hafen.web.vo.MyLessonOfflineVo;
import com.moil.hafen.web.vo.MyLessonOnlineVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping({"myLesson"})
@Api(tags = "我的课程")
public class MyLessonController  extends BaseController {

    private String message;

    @Resource
    private MyLessonService myLessonService;

    @GetMapping("/myLessonOnline")
    @ApiOperation("获取我的在线课程列表")
    public Result<List<MyLessonOnlineVo>> myLessonOnline() {
        List<MyLessonOnlineVo> list = this.myLessonService.getMyLessonOnlineList();
        return Result.OK(list);
    }
    @GetMapping("/myLessonOffline")
    @ApiOperation("获取我的线下课程列表")
    public Result<List<MyLessonOfflineVo>> myLessonOffline() {
        List<MyLessonOfflineVo> list = this.myLessonService.getMyLessonOfflineList();
        return Result.OK(list);
    }
}
