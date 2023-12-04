package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.domain.LessonOfflineStudentRecord;
import com.moil.hafen.web.service.LessonOfflineStudentRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理后台-科技营-线下课程管理
 *
 * @author song
 */
@Slf4j
@RestController
@RequestMapping({"lessonOfflineStudentRecord"})
@Api(tags = "课时变动明细")
public class LessonOfflineStudentRecordController  extends BaseController {

    private String message;

    @Resource
    private LessonOfflineStudentRecordService lessonOfflineStudentRecordService;

    @GetMapping("list")
    @ApiOperation("获取课时变动明细列表")
    public Result<List<LessonOfflineStudentRecord>> list(int lessonId) {
        List<LessonOfflineStudentRecord> list = this.lessonOfflineStudentRecordService.getList(lessonId);
        return Result.OK(list);
    }
}
