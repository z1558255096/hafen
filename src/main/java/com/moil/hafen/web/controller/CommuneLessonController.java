package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.CommuneLesson;
import com.moil.hafen.web.domain.CommuneLessonAdvance;
import com.moil.hafen.web.service.CommuneLessonAdvanceService;
import com.moil.hafen.web.service.CommuneLessonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 管理后台/公社模块/公社课程管理
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"communeLesson"})
@Api(tags = "公社课程管理")
public class CommuneLessonController extends BaseController {

    private String message;

    @Resource
    private CommuneLessonService communeLessonService;
    @Resource
    private CommuneLessonAdvanceService communeLessonAdvanceService;

    /**
     * 获取公社课程列表（分页） - 管理后台
     *
     * @param request       要求
     * @param communeLesson 社区课程
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取公社课程列表（分页）")
    public Result<IPage<CommuneLesson>> page(QueryRequest request, CommuneLesson communeLesson) {
        IPage<CommuneLesson> page = this.communeLessonService.getPage(request, communeLesson);
        return Result.OK(page);
    }

    /**
     * 添加公社课程信息 - 管理后台
     *
     * @param communeLesson 社区课程
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加公社课程信息")
    public Result add(@RequestBody CommuneLesson communeLesson) throws FebsException {
        try {
            communeLesson.setCreateTime(new Date());
            communeLesson.setModifyTime(new Date());
            communeLessonService.save(communeLesson);
            addAdvance(communeLesson);
            return Result.OK();
        } catch (Exception e) {
            message = "添加公社课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除公社课程信息 - 管理后台
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除公社课程信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(communeLessonService.delete(id));
        } catch (Exception e) {
            message = "删除公社课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上下架公社课程 - 管理后台
     *
     * @param id     id
     * @param status 上架状态 0上架 1下架
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架公社课程")
    public Result changeStatus(@PathVariable Integer id, Integer status) throws FebsException {
        try {
            CommuneLesson communeLesson = new CommuneLesson();
            communeLesson.setStatus(status);
            communeLesson.setId(id);
            communeLesson.setModifyTime(new Date());
            return Result.OK(this.communeLessonService.updateById(communeLesson));
        } catch (Exception e) {
            message = "上下架公社课程失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改公社课程信息 - 管理后台
     *
     * @param communeLesson 社区课程
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改公社课程信息")
    public Result update(@RequestBody CommuneLesson communeLesson) throws FebsException {
        try {
            communeLesson.setModifyTime(new Date());
            this.communeLessonService.updateById(communeLesson);
            addAdvance(communeLesson);
            return Result.OK();
        } catch (Exception e) {
            message = "修改公社课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 新增公社课程高级设置
     *
     * @param communeLesson 社区课程
     */
    private void addAdvance(CommuneLesson communeLesson) {
        // 先删除原有的
        communeLessonAdvanceService.update(new LambdaUpdateWrapper<CommuneLessonAdvance>()
                .eq(CommuneLessonAdvance::getLessonId, communeLesson.getId()).set(CommuneLessonAdvance::getDelFlag, 1));
        List<CommuneLessonAdvance> communeLessonAdvanceList = communeLesson.getCommuneLessonAdvanceList();
        for (CommuneLessonAdvance communeLessonAdvance : communeLessonAdvanceList) {
            communeLessonAdvance.setLessonId(communeLesson.getId());
        }
        // 再批量新增
        communeLessonAdvanceService.saveBatch(communeLessonAdvanceList);
    }

    /**
     * 通过ID获取公社课程详情 - 管理后台/小程序
     *
     * @param id id
     *
     * @return {@link Result}<{@link CommuneLesson}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公社课程详情")
    public Result<CommuneLesson> detail(@PathVariable Integer id) {
        return Result.OK(this.communeLessonService.detail(id));
    }

    /**
     * 获取公社课程列表--小程序
     *
     * @return {@link Result}<{@link TreeMap}<{@link String}, {@link List}<{@link CommuneLesson}>>>
     */
    @GetMapping("/miniList")
    @ApiOperation("获取公社课程列表--小程序")
    public Result<TreeMap<String, List<CommuneLesson>>> miniList() {
        TreeMap<String, List<CommuneLesson>> result = this.communeLessonService.miniList();
        return Result.OK(result);
    }
}
