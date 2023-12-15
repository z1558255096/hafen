package com.moil.hafen.web.controller;

import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.domain.RegionCity;
import com.moil.hafen.web.domain.RegionDistrict;
import com.moil.hafen.web.domain.RegionProvince;
import com.moil.hafen.web.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈子杰
 * @Description 省市区接口
 * @Version 1.0.0
 * @Date 2023/12/15 9:06
 */
@RestController
@Api("省市区接口")
@RequestMapping("/region")
public class RegionController {
    @Resource
    private RegionService regionService;

    @GetMapping("getProvince")
    @ApiOperation("获取省份信息")
    public Result<List<RegionProvince>> getProvince() {
        List<RegionProvince> provinceList = regionService.getRegion();
        return Result.OK(provinceList);
    }

    @GetMapping("getCity")
    @ApiOperation("获取城市信息")
    public Result<List<RegionCity>> getCity(String provinceCode) {
        List<RegionCity> cityList = regionService.getCity(provinceCode);
        return Result.OK(cityList);
    }

    @GetMapping("getDistrict")
    @ApiOperation("获取区县信息")
    public Result<List<RegionDistrict>> getDistrict(String cityCode) {
        List<RegionDistrict> districtList = regionService.getDistrict(cityCode);
        return Result.OK(districtList);
    }
}
