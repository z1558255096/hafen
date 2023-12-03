package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.StudentService;
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
@RequestMapping({"student"})
@Api(tags = "学员管理")
public class StudentController extends BaseController {

    private String message;

    @Resource
    private StudentService studentService;

    @GetMapping("/{lessonId}")
    @ApiOperation("查询课程下面的学员列表")
    public Map<String, Object> page(QueryRequest request,@PathVariable Integer lessonId,String studentName) {
        IPage<Student> page = this.studentService.getPage(request,lessonId, studentName);
        return getDataTable(page);
    }

    @GetMapping("/myStudent")
    @ApiOperation("查询我的/指定用户下面的学员列表")
    public Result<List<Student>> myStudent(Integer customerId) {
        if(customerId == null){
            customerId = JWTUtil.getCurrentCustomerId();
        }
        List<Student> list = this.studentService.list(new LambdaQueryWrapper<Student>().eq(Student::getCustomerId, customerId));
        return Result.OK(list);
    }

    @PostMapping
    @ApiOperation("添加学员信息")
    public Result add(Student student) throws FebsException {
        try {
            student.setCreateTime(new Date());
            student.setModifyTime(new Date());
            return Result.OK(this.studentService.save(student));
        } catch (Exception e) {
            message = "添加学员信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除学员信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            Student student = studentService.getById(id);
            student.setDelFlag(1);
            student.setModifyTime(new Date());
            return Result.OK(this.studentService.updateById(student));
        } catch (Exception e) {
            message = "删除学员信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改学员信息")
    public Result update(Student student) throws FebsException {
        try {
            student.setModifyTime(new Date());
            return Result.OK(this.studentService.updateById(student));
        } catch (Exception e) {
            message = "修改学员信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取学员详情")
    public Result<Student> detail(@PathVariable Integer id) {
        return Result.OK(this.studentService.getById(id));
    }

}
