package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.ClassSchedule;
import com.moil.hafen.web.service.ClassScheduleService;
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
@RequestMapping({"classSchedule"})
@Api(tags = "班级日程管理")
public class ClassScheduleController extends BaseController {

    private String message;

    @Resource
    private ClassScheduleService classScheduleService;

    @GetMapping
    @ApiOperation("获取班级日程列表（分页）")
    public Map<String, Object> page(QueryRequest request, ClassSchedule classSchedule) {
        IPage<ClassSchedule> page = this.classScheduleService.getPage(request, classSchedule);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加班级日程信息")
    public Result add(ClassSchedule classSchedule) throws FebsException {
        try {
            this.classScheduleService.addClassSchedule(classSchedule);
            return Result.OK();
        } catch (Exception e) {
            message = "添加班级日程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除班级日程信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.classScheduleService.removeById(id));
        } catch (Exception e) {
            message = "删除班级日程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改班级日程信息")
    public Result update(ClassSchedule classSchedule) throws FebsException {
        try {
            this.classScheduleService.updateClassSchedule(classSchedule);
            return Result.OK();
        } catch (Exception e) {
            message = "修改班级日程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架班级日程")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            ClassSchedule classSchedule = classScheduleService.getById(id);
            classSchedule.setModifyTime(new Date());
            classSchedule.setStatus(status);
            return Result.OK(this.classScheduleService.updateById(classSchedule));
        } catch (Exception e) {
            message = "上下架班级日程失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取班级日程详情")
    public Result<ClassSchedule> detail(@PathVariable Integer id) {
        return Result.OK(this.classScheduleService.getById(id));
    }
    @GetMapping("/getClassScheduleDate")
    @ApiOperation("通过ID获取班级日程详情")
    public Result getClassScheduleDate(String startTime,Integer classId) {
        List<Map<String,String>> list = this.classScheduleService.getClassScheduleDate(startTime,classId);
        return Result.OK(list);
    }

}
