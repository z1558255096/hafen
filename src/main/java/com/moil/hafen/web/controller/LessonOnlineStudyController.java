package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonOnlineStudy;
import com.moil.hafen.web.service.LessonOnlineStudyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户小程序-在线课程列表管理
 *
 * @author song
 */
@Slf4j
@RestController
@RequestMapping({"lessonOnlineStudy"})
@Api(tags = "在线课程学习管理")
public class LessonOnlineStudyController extends BaseController {

    private String message;

    @Resource
    private LessonOnlineStudyService lessonOnlineStudyService;

    /**
     * 添加在线学习记录列表
     *
     * @param lessonOnlineStudy 课程在线学习
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加在线学习记录列表")
    public Result add(LessonOnlineStudy lessonOnlineStudy) throws FebsException {
        try {
            int customerId = JWTUtil.getCurrentCustomerId();
            lessonOnlineStudyService.update(new LambdaUpdateWrapper<LessonOnlineStudy>().set(LessonOnlineStudy::getCurrentChapter,0)
                    .eq(LessonOnlineStudy::getLessonId,lessonOnlineStudy.getLessonId()).eq(LessonOnlineStudy::getCustomerId,customerId));
            LessonOnlineStudy one = lessonOnlineStudyService.getOne(new LambdaQueryWrapper<>());
            if(one != null){
                one.setModifyTime(new Date());
                one.setCurrentDuration(lessonOnlineStudy.getCurrentDuration());
                one.setCurrentChapter(1);
                return Result.OK(this.lessonOnlineStudyService.updateById(one));
            }else{
                lessonOnlineStudy.setCurrentChapter(1);
                lessonOnlineStudy.setCreateTime(new Date());
                lessonOnlineStudy.setModifyTime(new Date());
                return Result.OK(this.lessonOnlineStudyService.save(lessonOnlineStudy));
            }
        } catch (Exception e) {
            message = "添加在线学习记录列表失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

}
