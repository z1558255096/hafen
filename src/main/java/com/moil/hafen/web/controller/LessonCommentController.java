package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonComment;
import com.moil.hafen.web.service.LessonCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * 小程序-课堂点评
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonComment"})
@Api(tags = "在线课程评论管理")
public class LessonCommentController extends BaseController {

    private String message;

    @Resource
    private LessonCommentService lessonCommentService;

    /**
     * 获取在线课程评论列表（分页）
     *
     * @param request       要求
     * @param lessonComment 课程评论
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取在线课程评论列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonComment lessonComment) {
        IPage<LessonComment> page = this.lessonCommentService.getPage(request, lessonComment);
        return getDataTable(page);
    }

    /**
     * 添加在线课程评论信息
     *
     * @param lessonComment 课程评论
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加在线课程评论信息")
    public Result add(LessonComment lessonComment) throws FebsException {
        try {
            lessonComment.setCreateTime(new Date());
            lessonComment.setModifyTime(new Date());
            return Result.OK(this.lessonCommentService.save(lessonComment));
        } catch (Exception e) {
            message = "添加在线课程评论信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }


    /**
     * 修改在线课程评论信息
     *
     * @param lessonComment 课程评论
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改在线课程评论信息")
    public Result update(LessonComment lessonComment) throws FebsException {
        try {
            lessonComment.setModifyTime(new Date());
            return Result.OK(this.lessonCommentService.updateById(lessonComment));
        } catch (Exception e) {
            message = "修改在线课程评论信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上下架在线课程评论
     *
     * @param id     id
     * @param status 地位
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架在线课程评论")
    public Result changeStatus(@PathVariable Integer id, Integer status) throws FebsException {
        try {
            LessonComment lessonComment = lessonCommentService.getById(id);
            lessonComment.setStatus(status);
            lessonComment.setModifyTime(new Date());
            return Result.OK(this.lessonCommentService.updateById(lessonComment));
        } catch (Exception e) {
            message = "上下架在线课程评论失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取在线课程评论详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonComment}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取在线课程评论详情")
    public Result<LessonComment> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonCommentService.getById(id));
    }

}
