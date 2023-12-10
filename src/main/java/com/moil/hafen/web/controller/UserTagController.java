package com.moil.hafen.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.web.domain.UserTag;
import com.moil.hafen.web.service.UserTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 管理后台/内部管理/基础规则管理/用户标签管理
 *
 * @author 8129
 */
@Slf4j
@RestController
@RequestMapping({"userTag"})
@Api(tags = "管理后台/内部管理/基础规则管理/用户标签管理")
public class UserTagController extends BaseController {

    private String message;

    @Resource
    private UserTagService userTagService;

    @GetMapping
    @ApiOperation("获取用户标签列表（分页）")
    public Result<IPage<UserTag>> page(QueryRequest request, UserTag userTag) {
        IPage<UserTag> page = this.userTagService.getPage(request, userTag);
        return Result.OK(page);
    }

    @PostMapping
    @ApiOperation("添加用户标签信息")
    public Result add(@RequestBody UserTag userTag) throws FebsException {
        try {
            userTag.setCreateTime(new Date());
            userTag.setModifyTime(new Date());
            return Result.OK(this.userTagService.save(userTag));
        } catch (Exception e) {
            message = "添加用户标签信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping
    @ApiOperation("删除用户标签信息")
    public Result delete(@RequestParam("id") Integer id) throws FebsException {
        try {
            return Result.OK(this.userTagService.removeById(id));
        } catch (Exception e) {
            message = "删除用户标签信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    @ApiOperation("修改用户标签信息")
    public Result update(@RequestBody UserTag userTag) throws FebsException {
        try {
            userTag.setModifyTime(new Date());
            return Result.OK(this.userTagService.updateById(userTag));
        } catch (Exception e) {
            message = "修改用户标签信息失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("通过ID获取用户标签详情")
    public Result<UserTag> detail(@PathVariable Integer id) {
        return Result.OK(this.userTagService.getById(id));
    }

    @GetMapping("/list")
    @ApiOperation("获取用户标签列表")
    public Result list() {
        List<UserTag> list = this.userTagService.list();
        return Result.OK(list);
    }

}
