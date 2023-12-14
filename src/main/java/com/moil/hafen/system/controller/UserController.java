package com.moil.hafen.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.manager.UserManager;
import com.moil.hafen.system.service.RoleService;
import com.moil.hafen.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author 陈子杰
 * @Description 管理后台/内部管理/用户管理
 * @Version 1.0.0
 * @Date 2023/12/08 11:24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("backend/user")
@Api("管理后台/内部管理/用户管理")
public class UserController extends BaseController {

    private String message;

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserManager userManager;

    @GetMapping("page")
    @ApiOperation("用户分页列表")
    public Result<IPage<User>> userPage(QueryRequest queryRequest, User user) {
        IPage<User> userIPage = userService.userPage(user, queryRequest);
        return Result.OK(userIPage);
    }

    @PostMapping("add")
    @ApiOperation("添加员工")
    public Result<Object> addUser(@RequestBody @Valid User user) throws FebsException {
        try {
            this.userService.createUser(user);
            return Result.OK();
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message, e);
            return Result.error(message);
        }
    }

    @GetMapping("list")
    @ApiOperation("根据校区id获取用户列表")
    public Result<List<User>> list(@RequestParam("campusId") Integer campusId) {
        List<User> userList = userService.listByCampusId(campusId);
        return Result.OK(userList);
    }
}