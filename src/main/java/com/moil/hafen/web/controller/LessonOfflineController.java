package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.LessonOffline;
import com.moil.hafen.web.service.LessonOfflineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonOffline"})
@Api(tags = "线下课程管理")
public class LessonOfflineController extends BaseController {

    private String message;

    @Resource
    private LessonOfflineService lessonOfflineService;

    @GetMapping
    @ApiOperation("获取线下课程列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonOffline lessonOffline) {
        IPage<LessonOffline> page = this.lessonOfflineService.getPage(request, lessonOffline);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加线下课程信息")
    public Result add(LessonOffline lessonOffline) throws FebsException {
        try {
            lessonOfflineService.addLesson(lessonOffline);
            return Result.OK();
        } catch (Exception e) {
            message = "添加线下课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除线下课程信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            LessonOffline lessonOffline = lessonOfflineService.getById(id);
            lessonOffline.setDelFlag(1);
            lessonOffline.setModifyTime(new Date());
            return Result.OK(this.lessonOfflineService.updateById(lessonOffline));
        } catch (Exception e) {
            message = "删除线下课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改线下课程信息")
    public Result update(LessonOffline lessonOffline) throws FebsException {
        try {
            this.lessonOfflineService.updateLesson(lessonOffline);
            return Result.OK();
        } catch (Exception e) {
            message = "修改线下课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架线下课程")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            LessonOffline lessonOffline = lessonOfflineService.getById(id);
            lessonOffline.setStatus(status);
            lessonOffline.setModifyTime(new Date());
            return Result.OK(this.lessonOfflineService.updateById(lessonOffline));
        } catch (Exception e) {
            message = "上下架线下课程失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取线下课程详情")
    public Result<LessonOffline> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonOfflineService.detail(id));
    }
    @GetMapping("/export")
    @ApiOperation("导出线下课程")
    public void export(LessonOffline lessonOffline, HttpServletResponse response) throws FebsException {
        try {
            List<LessonOffline> contestantInfoList = this.lessonOfflineService.getLessonOfflineList(lessonOffline);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("线下课程", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), LessonOffline.class).sheet("线下课程").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/miniList")
    @ApiOperation("获取线下课程列表--小程序")
    public Result<TreeMap<String, List<LessonOffline>>> miniList(int type, int campusId) {
        TreeMap<String, List<LessonOffline>> result = this.lessonOfflineService.miniList(type, campusId);
        return Result.OK(result);
    }

}
