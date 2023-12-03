package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.service.LessonAuditionStudentService;
import com.moil.hafen.web.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonAuditionStudent"})
@Api(tags = "试听学员管理")
public class LessonAuditionStudentController extends BaseController {

    private String message;

    @Resource
    private LessonAuditionStudentService lessonAuditionStudentService;
    @Resource
    private StudentService studentService;

    @GetMapping
    @ApiOperation("获取试听学员列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonAuditionStudent lessonAuditionStudent) {
        IPage<LessonAuditionStudent> page = this.lessonAuditionStudentService.getPage(request, lessonAuditionStudent);
        return getDataTable(page);
    }

    @PostMapping("/{auditionId}/reservation")
    @ApiOperation("预约试听")
    public Result reservation(@PathVariable Integer auditionId, List<Integer> studentIds) throws FebsException {
        try {
            this.lessonAuditionStudentService.reservation(auditionId,studentIds);
            return Result.OK();
        } catch (Exception e) {
            message = "预约试听失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping("/{auditionId}/cancel")
    @ApiOperation("取消试听")
    public Result cancel(@PathVariable Integer auditionId, List<Integer> studentIds) throws FebsException {
        try {
            this.lessonAuditionStudentService.cancel(auditionId, studentIds);
            return Result.OK();
        } catch (Exception e) {
            message = "取消试听失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

}
