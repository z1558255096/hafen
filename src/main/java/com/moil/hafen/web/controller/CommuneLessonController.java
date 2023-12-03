package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @GetMapping
    @ApiOperation("获取公社课程列表（分页）")
    public Map<String, Object> page(QueryRequest request, CommuneLesson communeLesson) {
        IPage<CommuneLesson> page = this.communeLessonService.getPage(request, communeLesson);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加公社课程信息")
    public Result add(CommuneLesson communeLesson) throws FebsException {
        try {
            communeLesson.setCreateTime(new Date());
            communeLesson.setModifyTime(new Date());
            this.communeLessonService.save(communeLesson);
            addAdvance(communeLesson);
            return Result.OK();
        } catch (Exception e) {
            message = "添加公社课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除公社课程信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.communeLessonService.removeById(id));
        } catch (Exception e) {
            message = "删除公社课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架公社课程")
    public Result changeStatus(@PathVariable Integer id, int status) throws FebsException {
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
    @PutMapping
    @ApiOperation("修改公社课程信息")
    public Result update(CommuneLesson communeLesson) throws FebsException {
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
    private void addAdvance(CommuneLesson communeLesson){
        communeLessonAdvanceService.remove(new LambdaQueryWrapper<CommuneLessonAdvance>()
                .eq(CommuneLessonAdvance::getLessonId,communeLesson.getId()));
        List<CommuneLessonAdvance> communeLessonAdvanceList = communeLesson.getCommuneLessonAdvanceList();
        for (CommuneLessonAdvance communeLessonAdvance : communeLessonAdvanceList) {
            communeLessonAdvance.setLessonId(communeLesson.getId());
        }
        communeLessonAdvanceService.saveBatch(communeLessonAdvanceList);
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取公社课程详情")
    public Result<CommuneLesson> detail(@PathVariable Integer id) {
        return Result.OK(this.communeLessonService.detail(id));
    }

    @GetMapping("/miniList")
    @ApiOperation("获取公社课程列表--小程序")
    public Result<TreeMap<String, List<CommuneLesson>>> miniList() {
        TreeMap<String, List<CommuneLesson>> result = this.communeLessonService.miniList();
        return Result.OK(result);
    }
}
