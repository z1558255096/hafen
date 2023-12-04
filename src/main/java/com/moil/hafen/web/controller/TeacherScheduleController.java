package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.domain.ClassScheduleStudent;
import com.moil.hafen.web.domain.LessonAuditionStudent;
import com.moil.hafen.web.domain.Student;
import com.moil.hafen.web.service.TeacherScheduleService;
import com.moil.hafen.web.vo.TeacherScheduleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 教师小程序-我的日程
 *
 * @author song
 */
@Slf4j
@RestController
@RequestMapping({"teacherSchedule"})
@Api(tags = "教师端日程管理")
public class TeacherScheduleController extends BaseController {

    private String message;

    @Resource
    private TeacherScheduleService teacherScheduleService;


    /**
     * 获取我的日程
     *
     * @param classDate 上课日期
     *
     * @return {@link Result}
     */
    @GetMapping("/getMySchedule")
    @ApiOperation("获取我的日程")
    public Result getMySchedule(String classDate) {
        List<TeacherScheduleVo> list = this.teacherScheduleService.getMySchedule(classDate);
        return Result.OK(list);
    }

    /**
     * 获取日程详情学生列表 status:0上课 1取消预约
     *
     * @param scheduleId 计划id
     * @param classType  类类型
     * @param status     地位
     *
     * @return {@link Result}
     */
    @GetMapping("/getMyStudentList")
    @ApiOperation("获取日程详情学生列表 status:0上课 1取消预约")
    public Result getMyStudentList(String scheduleId, String classType,int status) {
        List<Student> list = this.teacherScheduleService.getMyStudentList(scheduleId, classType,status);
        return Result.OK(list);
    }

    /**
     * 获取我的点名列表
     *
     * @param scheduleId 计划id
     * @param classType  类类型
     *
     * @return {@link Result}
     */
    @GetMapping("/getMyAttendList")
    @ApiOperation("获取我的点名列表")
    public Result getMyAttendList(String scheduleId, String classType) {
        if(classType.equals("班级日程")) {
            List<ClassScheduleStudent> list = this.teacherScheduleService.getMyAttendScheduleList(scheduleId);
            return Result.OK(list);
        }else {
            List<LessonAuditionStudent> list = this.teacherScheduleService.getMyAttendAuditionList(scheduleId);
            return Result.OK(list);
        }

    }

    /**
     * 进行点名
     *
     * @param scheduleId   计划id
     * @param classType    类类型
     * @param studentId    学生id
     * @param attendStatus 出席状态
     *
     * @return {@link Result}
     */
    @PutMapping("/signIn")
    @ApiOperation("进行点名")
    public Result signIn(String scheduleId, String classType,Integer studentId,String attendStatus) {
        this.teacherScheduleService.signIn(scheduleId, classType, studentId, attendStatus);
        return Result.OK();

    }


}
