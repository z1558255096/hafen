package com.moil.hafen.web.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Campus;
import com.moil.hafen.web.domain.Dept;
import com.moil.hafen.web.service.CampusService;
import com.moil.hafen.web.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"campus"})
@Api(tags = "校区管理")
public class CampusController extends BaseController {

    private String message;

    @Resource
    private CampusService campusService;
    @Resource
    private DeptService deptService;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    // /**
    //  * 获取校区列表（分页） -管理后台/小程序
    //  *
    //  * @param request 要求
    //  * @param campus  校园
    //  * @return {@link Map}<{@link String}, {@link Object}>
    //  */
    // @GetMapping
    // @ApiOperation("获取校区列表（分页）")
    // public Result<Object> page(QueryRequest request, Campus campus) {
    //     IPage<Campus> page = this.campusService.getPage(request, campus);
    //     return Result.OK(page);
    // }

    @GetMapping("list")
    @ApiOperation("获取校区列表")
    public Result<List<Campus>> get() {
        List<Campus> campusList = campusService.getList();
        return Result.OK(campusList);
    }

    /**
     * 添加校区信息 -管理后台
     *
     * @param campus 校园
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PostMapping
    @ApiOperation("添加校区信息")
    public Result<Object> add(@RequestBody Campus campus) throws FebsException {
        try {
            campus.setCreateTime(new Date());
            campus.setModifyTime(new Date());
            this.campusService.save(campus);
            deptService.saveDefaultDept(campus.getId());
            return Result.OK();
        } catch (Exception e) {
            message = "添加校区信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 删除校区信息 -管理后台
     *
     * @param id id
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @DeleteMapping("delete")
    @ApiOperation("根据id删除校区信息")
    public Result<Object> delete(@RequestParam("id") Integer id) throws FebsException {
        try {
            this.campusService.removeById(id);
            this.deptService.delete(id);
            return Result.OK();
        } catch (Exception e) {
            message = "删除校区信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 修改校区信息 -管理后台
     *
     * @param campus 校园
     * @return {@link Result}
     * @throws FebsException FEBS系统内部异常
     */
    @PutMapping
    @ApiOperation("修改校区信息")
    public Result<Object> update(@RequestBody Campus campus) throws FebsException {
        try {
            campus.setModifyTime(new Date());
            this.campusService.updateById(campus);
            return Result.OK();
        } catch (Exception e) {
            message = "修改校区信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    /**
     * 通过ID获取校区详情 （管理后台/小程序）
     *
     * @param id id
     * @return {@link Result}<{@link Campus}>
     */
    @GetMapping("detail")
    @ApiOperation("通过ID获取校区详情")
    public Result<Campus> detail(@RequestParam("id") Integer id) {
        return Result.OK(this.campusService.getById(id));
    }

    @PostMapping("addDept")
    @ApiOperation("添加部门")
    public Result<Object> addDept(@RequestBody Dept dept) {
        deptService.add(dept);
        return Result.OK();
    }

    @PutMapping("updateDept")
    @ApiOperation("修改部门")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "部门id", required = true),
            @ApiImplicitParam(name = "deptName", value = "部门名称", required = true)})
    public Result<Object> updateDept(@RequestBody Dept dept) {
        deptService.update(dept);
        return Result.OK();
    }

    @DeleteMapping("deleteDept")
    @ApiOperation("删除部门")
    public Result<Object> deleteDept(@RequestParam("deptId") Integer deptId) {
        deptService.deleteDept(deptId);
        return Result.OK();
    }

    /**
     * 导出校区列表 （管理后台）
     *
     * @param campus   校园
     * @param response 回答
     * @throws FebsException FEBS系统内部异常
     */
    // @GetMapping("/export")
    // @ApiOperation("导出校区列表")
    // public void export(Campus campus, HttpServletResponse response) throws FebsException {
    //     try {
    //         List<Campus> contestantInfoList = this.campusService.getCampusList(campus);
    //         // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
    //         response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    //         response.setCharacterEncoding("utf-8");
    //         // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    //         String fileName = URLEncoder.encode("校区列表", "UTF-8").replaceAll("\\+", "%20");
    //         response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    //         EasyExcel.write(response.getOutputStream(), Campus.class).sheet("校区列表").doWrite(contestantInfoList);
    //     } catch (Exception e) {
    //         message = "导出Excel失败";
    //         log.error(message, e);
    //         throw new FebsException(message);
    //     }
    // }

}
