package com.moil.hafen.web.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.Campus;
import com.moil.hafen.web.service.CampusService;
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
@RequestMapping({"campus"})
@Api(tags = "校区管理")
public class CampusController extends BaseController {

    private String message;

    @Resource
    private CampusService campusService;

    /**
     * 获取校区列表（分页） -管理后台/小程序
     *
     * @param request 要求
     * @param campus  校园
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    @GetMapping
    @ApiOperation("获取校区列表（分页）")
    public Map<String, Object> page(QueryRequest request, Campus campus) {
        IPage<Campus> page = this.campusService.getPage(request, campus);
        return getDataTable(page);
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
    public Result add(Campus campus) throws FebsException {
        try {
            campus.setCreateTime(new Date());
            campus.setModifyTime(new Date());
            return Result.OK(this.campusService.save(campus));
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
    @DeleteMapping("/{id}")
    @ApiOperation("删除校区信息")
    public Result delete(@PathVariable Integer id) throws FebsException {
        try {
            return Result.OK(this.campusService.removeById(id));
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
    public Result update(Campus campus) throws FebsException {
        try {
            campus.setModifyTime(new Date());
            return Result.OK(this.campusService.updateById(campus));
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
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取校区详情")
    public Result<Campus> detail(@PathVariable Integer id) {
        return Result.OK(this.campusService.getById(id));
    }

    /**
     * 获取校区列表 （管理后台/小程序）
     *
     * @param campus 校园
     * @return {@link Result}
     */
    @GetMapping("/list")
    @ApiOperation("获取校区列表")
    public Result list(Campus campus) {
        List<Campus> list = this.campusService.getCampusList(campus);
        return Result.OK(list);
    }

    /**
     * 导出校区列表 （管理后台）
     *
     * @param campus   校园
     * @param response 回答
     * @throws FebsException FEBS系统内部异常
     */
    @GetMapping("/export")
    @ApiOperation("导出校区列表")
    public void export(Campus campus, HttpServletResponse response) throws FebsException {
        try {
            List<Campus> contestantInfoList = this.campusService.getCampusList(campus);
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("校区列表", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Campus.class).sheet("校区列表").doWrite(contestantInfoList);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

}
