package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.utils.DateUtil;
import com.moil.hafen.web.domain.LessonAudition;
import com.moil.hafen.web.domain.LessonOffline;
import com.moil.hafen.web.service.LessonAuditionService;
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

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"lessonAudition"})
@Api(tags = "试听日程管理")
public class LessonAuditionController extends BaseController {

    private String message;

    @Resource
    private LessonAuditionService lessonAuditionService;
    @Resource
    private LessonOfflineService lessonOfflineService;

    @GetMapping
    @ApiOperation("获取试听日程列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonAudition lessonAudition) {
        IPage<LessonAudition> page = this.lessonAuditionService.getPage(request, lessonAudition);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加试听日程信息")
    public Result add(LessonAudition lessonAudition) throws FebsException {
        try {
            lessonAudition.setCreateTime(new Date());
            lessonAudition.setModifyTime(new Date());
            String classDateTime = lessonAudition.getClassDate() + " " + lessonAudition.getClassStartTime();
            lessonAudition.setClassDateTime(DateUtil.string2Date(classDateTime, DateUtil.FULL_TIME_SPLIT_PATTERN));
            LessonOffline lessonOffline = lessonOfflineService.getById(lessonAudition.getLessonId());
            lessonAudition.setType(lessonOffline.getType());
            lessonAudition.setClassTime(lessonAudition.getClassStartTime()+" ~ " + lessonAudition.getClassEndTime());
            this.lessonAuditionService.addLessonAudition(lessonAudition);
            return Result.OK();
        } catch (Exception e) {
            message = "添加试听日程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除试听日程信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.lessonAuditionService.removeById(id));
        } catch (Exception e) {
            message = "删除试听日程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改试听日程信息")
    public Result update(LessonAudition lessonAudition) throws FebsException {
        try {
            lessonAudition.setModifyTime(new Date());
            String classDateTime = lessonAudition.getClassDate() + " " + lessonAudition.getClassStartTime();
            lessonAudition.setClassDateTime(DateUtil.string2Date(classDateTime, DateUtil.FULL_TIME_SPLIT_PATTERN));
            LessonOffline lessonOffline = lessonOfflineService.getById(lessonAudition.getLessonId());
            lessonAudition.setType(lessonOffline.getType());
            lessonAudition.setClassTime(lessonAudition.getClassStartTime()+" ~ " + lessonAudition.getClassEndTime());
            return Result.OK(this.lessonAuditionService.updateById(lessonAudition));
        } catch (Exception e) {
            message = "修改试听日程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架试听日程")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            LessonAudition lessonAudition = lessonAuditionService.getById(id);
            lessonAudition.setStatus(status);
            lessonAudition.setModifyTime(new Date());
            return Result.OK(this.lessonAuditionService.updateById(lessonAudition));
        } catch (Exception e) {
            message = "上下架试听日程失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取试听日程详情")
    public Result<LessonAudition> detail(@PathVariable Integer id) {
        return Result.OK(this.lessonAuditionService.getById(id));
    }
    @GetMapping("/export")
    @ApiOperation("导出试听日程列表")
    public void export(LessonAudition lessonAudition, HttpServletResponse response) throws FebsException {
        try {
            List<LessonAudition> contestantInfoList = this.lessonAuditionService.getLessonAuditionList(lessonAudition);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("试听日程列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), LessonAudition.class).sheet("试听日程列表").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @GetMapping("/getAudition")
    @ApiOperation("获取试听日程列表（分页）")
    public Result<Map<String,List<LessonAudition>>> getAuditionList(int type,String date,int campusId) {
        Map<String,List<LessonAudition>> result = this.lessonAuditionService.getAuditionList(type, date, campusId);
        return Result.OK(result);
    }

    @GetMapping("/getMyAudition")
    @ApiOperation("获取我的试听日程列表")
    public Map<String, Object> getMyAudition(QueryRequest request) {
        IPage<LessonAudition> page = this.lessonAuditionService.getMyAudition(request);
        return getDataTable(page);
    }
}