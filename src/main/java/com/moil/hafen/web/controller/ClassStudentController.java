package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"classStudent"})
@Api(tags = "班级学员管理")
public class ClassStudentController extends BaseController {

    private String message;

    @Resource
    private ClassStudentService classStudentService;
    @Resource
    private StudentService studentService;
    @Resource
    private ClassScheduleStudentService classScheduleStudentService;
    @Resource
    private ClassScheduleService classScheduleService;
    @Resource
    private ClassInfoService classInfoService;

    @GetMapping
    @ApiOperation("获取班级学员列表（分页）")
    public Map<String, Object> page(QueryRequest request, ClassStudent classStudent) {
        IPage<ClassStudent> page = this.classStudentService.getPage(request, classStudent);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加班级学员信息")
    public Result add(ClassStudent classStudent) throws FebsException {
        try {
            classStudent.setCreateTime(new Date());
            classStudent.setModifyTime(new Date());
            Student student = studentService.getById(classStudent.getStudentId());
            classStudent.setCustomerId(student.getCustomerId());
            this.classStudentService.save(classStudent);
            saveScheduleStudent(classStudent);
            return Result.OK();
        } catch (Exception e) {
            message = "添加班级学员信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    private void saveScheduleStudent(ClassStudent classStudent){
        int classInfoId = classStudent.getClassId();
        ClassInfo classInfo = classInfoService.getById(classInfoId);
        List<ClassSchedule> scheduleList = classScheduleService.list(new LambdaQueryWrapper<ClassSchedule>().eq(ClassSchedule::getClassId, classInfoId)
                .eq(ClassSchedule::getStatus, 0).ge(ClassSchedule::getClassDate, LocalDate.now()));
        List<ClassScheduleStudent> list = new ArrayList<>();
        for (ClassSchedule classSchedule : scheduleList) {
            ClassScheduleStudent classScheduleStudent = new ClassScheduleStudent(classSchedule.getId(), classStudent.getStudentId(), classInfoId, classInfo.getTeachingCount(), new Date());
            list.add(classScheduleStudent);
        }
        classScheduleStudentService.saveBatch(list);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("移出班级学员信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            ClassStudent classStudent = this.classStudentService.getById(id);
            classScheduleStudentService.remove(new LambdaQueryWrapper<ClassScheduleStudent>().eq(ClassScheduleStudent::getStudentId,classStudent.getStudentId())
                    .eq(ClassScheduleStudent::getClassInfoId,classStudent.getClassId()).isNull(ClassScheduleStudent::getAttendStatus));
            return Result.OK(this.classStudentService.removeById(id));
        } catch (Exception e) {
            message = "删除班级学员信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改班级学员信息")
    public Result update(ClassStudent classStudent) throws FebsException {
        try {
            classStudent.setModifyTime(new Date());
            ClassStudent classStudent1 = this.classStudentService.getById(classStudent.getId());
            classStudent.setStudentId(classStudent1.getStudentId());
            classScheduleStudentService.remove(new LambdaQueryWrapper<ClassScheduleStudent>().eq(ClassScheduleStudent::getStudentId,classStudent1.getStudentId())
                    .eq(ClassScheduleStudent::getClassInfoId,classStudent1.getClassId()).isNull(ClassScheduleStudent::getAttendStatus));
            saveScheduleStudent(classStudent);
            return Result.OK(this.classStudentService.updateById(classStudent));
        } catch (Exception e) {
            message = "修改班级学员信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取班级学员详情")
    public Result<ClassStudent> detail(@PathVariable Integer id) {
        return Result.OK(this.classStudentService.getById(id));
    }

}
