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
 * 教师小程序-学员管理
 *
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

    /**
     * 查询课程下面的学员列表
     *
     * @param request     要求
     * @param lessonId    课程id
     * @param studentName 学生姓名
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping("/{lessonId}")
    @ApiOperation("查询课程下面的学员列表")
    public Map<String, Object> page(QueryRequest request,@PathVariable Integer lessonId,String studentName) {
        IPage<Student> page = this.studentService.getPage(request,lessonId, studentName);
        return getDataTable(page);
    }

    /**
     * 查询我的/指定用户下面的学员列表
     *
     * @param customerId 客户id
     *
     * @return {@link Result}<{@link List}<{@link Student}>>
     */
    @GetMapping("/myStudent")
    @ApiOperation("查询我的/指定用户下面的学员列表")
    public Result<List<Student>> myStudent(Integer customerId) {
        if(customerId == null){
            customerId = JWTUtil.getCurrentCustomerId();
        }
        List<Student> list = this.studentService.list(new LambdaQueryWrapper<Student>().eq(Student::getCustomerId, customerId));
        return Result.OK(list);
    }

    /**
     * 添加学员信息
     *
     * @param student 大学生
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
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

    /**
     * 删除学员信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
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

    /**
     * 修改学员信息
     *
     * @param student 大学生
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
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

    /**
     * 通过ID获取学员详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link Student}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取学员详情")
    public Result<Student> detail(@PathVariable Integer id) {
        return Result.OK(this.studentService.getById(id));
    }

}
