package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonOnlineChapter;
import com.moil.hafen.web.domain.LessonOnlineStudy;
import com.moil.hafen.web.service.LessonOnlineChapterService;
import com.moil.hafen.web.service.LessonOnlineStudyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 管理后台-科技营-线上课程列表-新增章节
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonOnlineChapter"})
@Api(tags = "在线课程章节管理")
public class LessonOnlineChapterController extends BaseController {

    private String message;

    @Resource
    private LessonOnlineChapterService lessonOnlineChapterService;
    @Resource
    private LessonOnlineStudyService lessonOnlineStudyService;

    /**
     * 获取在线课程章节列表（分页）
     *
     * @param request             要求
     * @param lessonOnlineChapter 在线课程章节
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取在线课程章节列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonOnlineChapter lessonOnlineChapter) {
        IPage<LessonOnlineChapter> page = this.lessonOnlineChapterService.getPage(request, lessonOnlineChapter);
        return getDataTable(page);
    }

    /**
     * 添加在线课程章节信息
     *
     * @param lessonOnlineChapter 在线课程章节
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加在线课程章节信息")
    public Result add(LessonOnlineChapter lessonOnlineChapter) throws FebsException {
        try {
            lessonOnlineChapter.setCreateTime(new Date());
            lessonOnlineChapter.setModifyTime(new Date());
            LessonOnlineChapter chapter = lessonOnlineChapterService.getOne(new LambdaQueryWrapper<LessonOnlineChapter>()
                    .eq(LessonOnlineChapter::getLessonId, lessonOnlineChapter.getLessonId())
                    .orderByDesc(LessonOnlineChapter::getSort));
            int sort = 1;
            if(chapter != null){
                sort = chapter.getSort()+1;
            }
            lessonOnlineChapter.setSort(sort);
            lessonOnlineChapter.setDelFlag(0);
            return Result.OK(this.lessonOnlineChapterService.save(lessonOnlineChapter));
        } catch (Exception e) {
            message = "添加在线课程章节信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除在线课程章节信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除在线课程章节信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            LessonOnlineChapter lessonOnlineChapter = lessonOnlineChapterService.getById(id);
            lessonOnlineChapter.setDelFlag(1);
            lessonOnlineChapter.setModifyTime(new Date());
            return Result.OK(this.lessonOnlineChapterService.updateById(lessonOnlineChapter));
        } catch (Exception e) {
            message = "删除在线课程章节信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改在线课程章节信息
     *
     * @param lessonOnlineChapter 在线课程章节
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改在线课程章节信息")
    public Result update(LessonOnlineChapter lessonOnlineChapter) throws FebsException {
        try {
            lessonOnlineChapter.setModifyTime(new Date());
            return Result.OK(this.lessonOnlineChapterService.updateById(lessonOnlineChapter));
        } catch (Exception e) {
            message = "修改在线课程章节信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上下架在线课程章节
     *
     * @param id     id
     * @param status 地位
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架在线课程章节")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            LessonOnlineChapter lessonOnlineChapter = lessonOnlineChapterService.getById(id);
            lessonOnlineChapter.setStatus(status);
            lessonOnlineChapter.setModifyTime(new Date());
            return Result.OK(this.lessonOnlineChapterService.updateById(lessonOnlineChapter));
        } catch (Exception e) {
            message = "上下架在线课程章节失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 更新排序--sortStr 操作字符（down---下移，up---上移）
     *
     * @param id      id
     * @param sortStr 排序str
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/updateSort")
    @ApiOperation("更新排序--sortStr 操作字符（down---下移，up---上移）")
    public Result updateSort(@PathVariable Integer id,String sortStr) throws FebsException {
        try {
            lessonOnlineChapterService.updateSort(id, sortStr);
            return Result.OK();
        } catch (Exception e) {
            message = "上下架在线课程章节失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取在线课程章节详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonOnlineChapter}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取在线课程章节详情")
    public Result<LessonOnlineChapter> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonOnlineChapterService.getById(id));
    }

    /**
     * 小程序通过课程ID获取在线课程章节列表
     *
     * @param lessonId 课程id
     *
     * @return {@link Result}<{@link List}<{@link LessonOnlineChapter}>>
     */
    @GetMapping("/{lessonId}/getMiniChapter")
    @ApiOperation("小程序通过课程ID获取在线课程章节列表")
    public Result<List<LessonOnlineChapter>> getMiniChapter(@PathVariable Integer lessonId) {
        LessonOnlineStudy lessonOnlineStudy = lessonOnlineStudyService.getOne(new LambdaQueryWrapper<LessonOnlineStudy>().eq(LessonOnlineStudy::getLessonId, lessonId).eq(LessonOnlineStudy::getCurrentChapter, 1));
        List<LessonOnlineChapter> list = this.lessonOnlineChapterService.list(new LambdaQueryWrapper<LessonOnlineChapter>().eq(LessonOnlineChapter::getLessonId, lessonId)
                .eq(LessonOnlineChapter::getStatus, 0).eq(LessonOnlineChapter::getDelFlag, 0).orderByAsc(LessonOnlineChapter::getSort));
        if(lessonOnlineStudy != null){
            for (LessonOnlineChapter lessonOnlineChapter : list) {
                if(lessonOnlineChapter.getId()==lessonOnlineStudy.getChapterId()){
                    lessonOnlineChapter.setStudyStatus(1);
                }
            }
        }
        return Result.OK();
    }

}
