package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.ClassInfo;
import com.moil.hafen.web.service.ClassInfoService;
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
@RequestMapping({"classInfo"})
@Api(tags = "科技营/体适能 班级管理")
public class ClassInfoController extends BaseController {

    private String message;

    @Resource
    private ClassInfoService classInfoService;

    /**
     *  获取班级列表（分页） - 管理后台
     *
     * @param request   要求
     * @param classInfo 类信息
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取班级列表（分页）")
    public Map<String, Object> page(QueryRequest request, ClassInfo classInfo) {
        IPage<ClassInfo> page = this.classInfoService.getPage(request, classInfo);
        return getDataTable(page);
    }

    /**
     *  添加班级信息 - 管理后台
     *
     * @param classInfo 类信息
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加班级信息")
    public Result add(ClassInfo classInfo) throws FebsException {
        try {
            classInfo.setCreateTime(new Date());
            classInfo.setModifyTime(new Date());
            return Result.OK(this.classInfoService.save(classInfo));
        } catch (Exception e) {
            message = "添加班级信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除班级信息 - 管理后台
     *
     * @param id id
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除班级信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.classInfoService.removeById(id));
        } catch (Exception e) {
            message = "删除班级信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改班级信息 - 管理后台
     *
     * @param classInfo 类信息
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改班级信息")
    public Result update(ClassInfo classInfo) throws FebsException {
        try {
            classInfo.setModifyTime(new Date());
            this.classInfoService.updateClassInfo(classInfo);
            return Result.OK();
        } catch (Exception e) {
            message = "修改班级信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取班级详情 （管理后台/小程序）
     *
     * @param id id
     * @return {@link Result}<{@link ClassInfo}>
     */
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取班级详情")
    public Result<ClassInfo> detail(@PathVariable Integer id) {
        return Result.OK(this.classInfoService.getById(id));
    }

    /**
     * 获取班级列表（不分页） - 小程序
     *
     * @param classInfo 类信息
     * @return {@link Result}
     */
    @GetMapping("/list")
    @ApiOperation("获取班级列表")
    public Result list(ClassInfo classInfo) {
        List<ClassInfo> list = this.classInfoService.getClassInfoList(classInfo);
        return Result.OK(list);
    }

    /**
     * 导出班级列表 - 管理后台
     *
     * @param classInfo 类信息
     * @param response  回答
     * @throws FebsException FEBS系统内部异常
     */
    @GetMapping("/export")
    @ApiOperation("导出班级列表")
    public void export(ClassInfo classInfo, HttpServletResponse response) throws FebsException {
        try {
            List<ClassInfo> contestantInfoList = this.classInfoService.getClassInfoList(classInfo);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("班级列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ClassInfo.class).sheet("班级列表").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}
