package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Evaluate;
import com.moil.hafen.web.domain.LessonOnline;
import com.moil.hafen.web.service.EvaluateService;
import com.moil.hafen.web.service.LessonOnlineService;
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
@RequestMapping({"lessonOnline"})
@Api(tags = "在线课程管理")
public class LessonOnlineController extends BaseController {

    private String message;

    @Resource
    private LessonOnlineService lessonOnlineService;
    @Resource
    private EvaluateService evaluateService;

    @GetMapping
    @ApiOperation("获取在线课程列表（分页）")
    public Map<String, Object> page(QueryRequest request, LessonOnline lessonOnline) {
        IPage<LessonOnline> page = this.lessonOnlineService.getPage(request, lessonOnline);
        return getDataTable(page);
    }

    @PostMapping
    @ApiOperation("添加在线课程信息")
    public Result add(LessonOnline lessonOnline) throws FebsException {
        try {
            lessonOnline.setCreateTime(new Date());
            lessonOnline.setModifyTime(new Date());
            lessonOnline.setDelFlag(0);
            return Result.OK(this.lessonOnlineService.save(lessonOnline));
        } catch (Exception e) {
            message = "添加在线课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除在线课程信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            LessonOnline lessonOnline = lessonOnlineService.getById(id);
            lessonOnline.setDelFlag(1);
            lessonOnline.setModifyTime(new Date());
            return Result.OK(this.lessonOnlineService.updateById(lessonOnline));
        } catch (Exception e) {
            message = "删除在线课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改在线课程信息")
    public Result update(LessonOnline lessonOnline) throws FebsException {
        try {
            lessonOnline.setModifyTime(new Date());
            return Result.OK(this.lessonOnlineService.updateById(lessonOnline));
        } catch (Exception e) {
            message = "修改在线课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @PutMapping("/{id}/changeStatus")
    @ApiOperation("上下架在线课程")
    public Result changeStatus(@PathVariable Integer id,Integer status) throws FebsException {
        try {
            LessonOnline lessonOnline = lessonOnlineService.getById(id);
            lessonOnline.setStatus(status);
            lessonOnline.setModifyTime(new Date());
            return Result.OK(this.lessonOnlineService.updateById(lessonOnline));
        } catch (Exception e) {
            message = "上下架在线课程失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取在线课程详情")
    public Result<LessonOnline> detail(@PathVariable Integer id) {
        LessonOnline lessonOnline = this.lessonOnlineService.getById(id);
        int count = evaluateService.count(new LambdaQueryWrapper<Evaluate>().eq(Evaluate::getLessonId, id).eq(Evaluate::getSource, 1));
        lessonOnline.setEvaluateCount(count);
        return Result.OK(lessonOnline);
    }
    @GetMapping("/export")
    @ApiOperation("导出在线课程")
    public void export(LessonOnline lessonOnline, HttpServletResponse response) throws FebsException {
        try {
            List<LessonOnline> contestantInfoList = this.lessonOnlineService.getLessonOnlineList(lessonOnline);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("在线课程", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), LessonOnline.class).sheet("在线课程").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/miniList")
    @ApiOperation("获取在线课程列表--小程序")
    public Result<TreeMap<String, List<LessonOnline>>> miniList(int type) {
        TreeMap<String, List<LessonOnline>> result = this.lessonOnlineService.miniList(type);
        return Result.OK(result);
    }

}
