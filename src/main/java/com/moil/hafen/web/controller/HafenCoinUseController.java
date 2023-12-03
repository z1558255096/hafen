package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.HafenCoinUse;
import com.moil.hafen.web.service.HafenCoinUseService;
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
@RequestMapping({"hafenCoinUse"})
@Api(tags = "哈奋币规则-使用")
public class HafenCoinUseController extends BaseController {

    private String message;

    @Resource
    private HafenCoinUseService hafenCoinUseService;

    @GetMapping
    @ApiOperation("获取哈奋币规则列表（分页）")
    public Map<String, Object> page(QueryRequest request, HafenCoinUse hafenCoinUse) {
        IPage<HafenCoinUse> page = this.hafenCoinUseService.getPage(request, hafenCoinUse);
        return getDataTable(page);
    }

    @PutMapping
    @ApiOperation("修改哈奋币规则信息")
    public Result update(HafenCoinUse hafenCoinUse) throws FebsException {
        try {
            hafenCoinUse.setModifyTime(new Date());
            return Result.OK(this.hafenCoinUseService.updateById(hafenCoinUse));
        } catch (Exception e) {
            message = "修改哈奋币规则信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }
    @GetMapping("/{id}")
    @ApiOperation("通过ID获取哈奋币规则详情")
    public Result<HafenCoinUse> detail(@PathVariable Integer id) {
        return Result.OK(this.hafenCoinUseService.getById(id));
    }
    @GetMapping("/list")
    @ApiOperation("获取哈奋币规则列表")
    public Result list(HafenCoinUse hafenCoinUse) {
        List<HafenCoinUse> list = this.hafenCoinUseService.list();
        return Result.OK(list);
    }

}
