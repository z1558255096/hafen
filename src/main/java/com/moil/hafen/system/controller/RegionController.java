package com.moil.hafen.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.system.domain.Region;
import com.moil.hafen.system.service.RegionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 8129
 */
@Slf4j
@Validated
@RestController
@RequestMapping({"region","backend/region"})
public class RegionController extends BaseController {

    @Resource
    private RegionService regionService;

    @GetMapping("/{id}")
    public Result province(@PathVariable int id) {
        return Result.OK(this.regionService.list(new LambdaQueryWrapper<Region>().eq(Region::getParent,id)));
    }
}
