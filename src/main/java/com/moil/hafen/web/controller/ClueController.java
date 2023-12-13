package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.web.domain.Clue;
import com.moil.hafen.web.service.ClueService;
import com.moil.hafen.web.vo.ClueVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description (Clue)表接口
 * @Version 1.0.0
 * @Date 2023-12-14 02:35:11
 */
@RestController
@RequestMapping("tClue")
@Api(tags = "管理后台/营销管理/线索管理")
public class ClueController extends BaseController {
    @Resource
    private ClueService clueService;

    @GetMapping("page")
    @ApiOperation("分页查询线索列表")
    public Result<IPage<ClueVo>> page(QueryRequest queryRequest,Clue clue){
        IPage<ClueVo> clueVoPage = clueService.page(queryRequest,clue);
        return Result.OK(clueVoPage);
    }

    @PostMapping("add")
    @ApiOperation("新增线索")
    public Result<Object> add(@RequestBody @Valid Clue clue){
        clue.setCreateTime(new Date());
        clue.setModifyTime(new Date());
        int id = JWTUtil.getCurrentCustomerId();
        clue.setCreator(Integer.toString(id));
        clue.setModifier(Integer.toString(id));
        clueService.add(clue);
        return Result.OK();
    }
}

