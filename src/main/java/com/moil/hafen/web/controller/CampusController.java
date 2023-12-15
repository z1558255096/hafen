package com.moil.hafen.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.tencent.TencentService;
import com.moil.hafen.common.tencent.resp.TencentResp;
import com.moil.hafen.web.domain.Campus;
import com.moil.hafen.web.domain.Dept;
import com.moil.hafen.web.service.CampusService;
import com.moil.hafen.web.service.DeptService;
import com.moil.hafen.web.vo.LocationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private TencentService tencentService;

    @GetMapping("list")
    @ApiOperation("获取校区列表")
    public Result<List<Campus>> get() {
        List<Campus> campusList = campusService.getList();
        return Result.OK(campusList);
    }

    @GetMapping("title")
    @ApiOperation("根据关键字获取位置")
    @ApiImplicitParams({@ApiImplicitParam(name = "keyWord", value = "关键字", required = true, paramType = "query"), @ApiImplicitParam(name = "region", value = "城市",
            paramType = "query"),})
    public Result<List<LocationVO>> getTitle(String keyWord, String region) {
        TencentResp tencentResp = tencentService.suggestion(keyWord, region);
        String jsonData = tencentResp.getData();
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        List<LocationVO> locationVOList = new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.parseObject(o.toString());
            JSONObject location = jsonObject.getJSONObject("location");
            LocationVO locationVO = new LocationVO();
            locationVO.setAddress(jsonObject.get("address").toString());
            locationVO.setLat(location.get("lat").toString());
            locationVO.setLng(location.get("lng").toString());
            locationVOList.add(locationVO);
        }
        return Result.OK(locationVOList);
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
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> add(@RequestBody Campus campus) throws FebsException {
        try {
            campus.setCreateTime(new Date());
            campus.setModifyTime(new Date());
            if (CollectionUtil.isNotEmpty(campus.getBusinessScopeArr())) {
                campus.setBusinessScope(String.join(",", campus.getBusinessScopeArr()));
            }
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
    @Transactional(rollbackFor = Exception.class)
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
    @ApiImplicitParams({@ApiImplicitParam(name = "deptId", value = "部门id", required = true), @ApiImplicitParam(name = "deptName", value = "部门名称", required = true)})
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

    @GetMapping("getDeptByCampusId")
    @ApiOperation("根据校区id查询部门")
    public Result<List<Dept>> getDeptByCampusId(@RequestParam("id") Integer campusId) {
        List<Dept> list = deptService.getListByCampusId(campusId);
        return Result.OK(list);
    }

}
