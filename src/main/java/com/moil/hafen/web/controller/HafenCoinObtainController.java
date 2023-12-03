package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.HafenCoinObtain;
import com.moil.hafen.web.service.HafenCoinObtainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"hafenCoinObtain"})
@Api(tags = "哈奋币规则-获取")
public class HafenCoinObtainController extends BaseController {

    private String message;

    @Resource
    private HafenCoinObtainService hafenCoinObtainService;

    @GetMapping
    @ApiOperation("获取哈奋币规则列表（分页）")
    public Map<String, Object> page(QueryRequest request, HafenCoinObtain hafenCoinObtain) {
        IPage<HafenCoinObtain> page = this.hafenCoinObtainService.getPage(request, hafenCoinObtain);
        return getDataTable(page);
    }


    @PutMapping
    @ApiOperation("修改哈奋币规则信息")
    public Result update(HafenCoinObtain hafenCoinObtain) throws FebsException {
        try {
            hafenCoinObtain.setModifyTime(new Date());
            return Result.OK(this.hafenCoinObtainService.updateById(hafenCoinObtain));
        } catch (Exception e) {
            message = "修改哈奋币规则信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取哈奋币规则详情")
    public Result<HafenCoinObtain> detail(@PathVariable Integer id) {
        return Result.OK(this.hafenCoinObtainService.getById(id));
    }
    @GetMapping("/list")
    @ApiOperation("获取哈奋币规则列表")
    public Result list() {
        List<HafenCoinObtain> list = this.hafenCoinObtainService.list();
        return Result.OK(list);
    }
}
