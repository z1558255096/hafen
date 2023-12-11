package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.*;

/**
 * 管理后台/科技营/线下课程管理
 *
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

    /**
     * 获取线下课程列表（分页）
     *
     * @param request       要求
     * @param lessonOffline 课程离线
     *
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取线下课程列表（分页）")
    public Result<IPage<LessonOffline>> page(QueryRequest request, LessonOffline lessonOffline) {
        IPage<LessonOffline> page = lessonOfflineService.getPage(request, lessonOffline);
        return Result.OK(page);
    }

    /**
     * 添加线下课程信息
     *
     * @param lessonOffline 课程离线
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加线下课程信息")
    public Result add(@RequestBody LessonOffline lessonOffline) throws FebsException {
        try {
            int count = lessonOfflineService.count(new LambdaQueryWrapper<LessonOffline>()
                    .eq(LessonOffline::getName, lessonOffline.getName())
                    .eq(LessonOffline::getType, lessonOffline.getType())
                    .eq(LessonOffline::getDelFlag, 0));
            if (count > 0) {
                return Result.error("课程名称已存在");
            }
            lessonOfflineService.addLesson(lessonOffline);
            return Result.OK();
        } catch (Exception e) {
            message = "添加线下课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除线下课程信息
     *
     * @param id id
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/delete")
    @ApiOperation("删除线下课程信息")
    public Result delete(@RequestParam Integer id) throws FebsException {
        try {
            LessonOffline lessonOffline = lessonOfflineService.getById(id);
            lessonOffline.setDelFlag(1);
            lessonOffline.setModifyTime(new Date());
            return Result.OK(lessonOfflineService.updateById(lessonOffline));
        } catch (Exception e) {
            message = "删除线下课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改线下课程信息
     *
     * @param lessonOffline 课程离线
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/update")
    @ApiOperation("修改线下课程信息")
    public Result update(@RequestBody LessonOffline lessonOffline) throws FebsException {
        try {
            LessonOffline one = lessonOfflineService.getOne(new LambdaQueryWrapper<LessonOffline>()
                    .eq(LessonOffline::getName, lessonOffline.getName())
                    .eq(LessonOffline::getType, lessonOffline.getType())
                    .eq(LessonOffline::getDelFlag, 0));
            if (one != null && !Objects.equals(one.getId(), lessonOffline.getId())) {
                return Result.error("课程名称已存在");
            }
            lessonOfflineService.updateLesson(lessonOffline);
            return Result.OK();
        } catch (Exception e) {
            message = "修改线下课程信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 上下架线下课程
     *
     * @param id     id
     * @param status 地位
     *
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping("/changeStatus")
    @ApiOperation("上下架线下课程")
    public Result changeStatus(@RequestParam Integer id, Integer status) throws FebsException {
        try {
            LessonOffline lessonOffline = lessonOfflineService.getById(id);
            lessonOffline.setStatus(status);
            lessonOffline.setModifyTime(new Date());
            return Result.OK(lessonOfflineService.updateById(lessonOffline));
        } catch (Exception e) {
            message = "上下架线下课程失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取线下课程详情
     *
     * @param id id
     *
     * @return {@link Result}<{@link LessonOffline}>
     */
    @GetMapping("/detail")
    @ApiOperation("通过ID获取线下课程详情")
    public Result<LessonOffline> detail(@RequestParam Integer id) {
        return Result.OK(lessonOfflineService.detail(id));
    }

    /**
     * 导出线下课程
     *
     * @param lessonOffline 课程离线
     * @param response      回答
     *
     * @throws FebsException FEBS系统内部异常
     */
    @GetMapping("/export")
    @ApiOperation("导出线下课程")
    public void export(LessonOffline lessonOffline, HttpServletResponse response) throws FebsException {
        try {
            List<LessonOffline> contestantInfoList = lessonOfflineService.getLessonOfflineList(lessonOffline);
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

    /**
     * 获取线下课程列表--小程序
     *
     * @param type     类型
     * @param campusId 校园id
     *
     * @return {@link Result}<{@link TreeMap}<{@link String}, {@link List}<{@link LessonOffline}>>>
     */
    @GetMapping("/miniList")
    @ApiOperation("获取线下课程列表--小程序")
    public Result<TreeMap<String, List<LessonOffline>>> miniList(int type, int campusId) {
        TreeMap<String, List<LessonOffline>> result = lessonOfflineService.miniList(type, campusId);
        return Result.OK(result);
    }

}
