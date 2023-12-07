package com.moil.hafen.system.controller;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.utils.MD5Util;
import com.moil.hafen.system.domain.Role;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.manager.UserManager;
import com.moil.hafen.system.service.RoleService;
import com.moil.hafen.system.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping("backend/user")
@Api("管理后台-用户管理")
public class UserController extends BaseController {

    private String message;

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserManager userManager;

    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }

    @GetMapping("/{username}")
    public User detail(@NotBlank(message = "{required}") @PathVariable String username) {
        User user=this.userService.findByName(username);
        //修复用户修改自己的个人信息第二次提示roleId不能为空
        List<Role> roles=roleService.findUserRole(username);
        List<Integer> roleIds=roles.stream().map(role ->role.getId()).collect(Collectors.toList());
        String roleIdStr=StringUtils.join(roleIds.toArray(new Long[roleIds.size()]),",");
        return user;
    }

    @GetMapping
    public Map<String, Object> userList(QueryRequest queryRequest, User user) {
        return getDataTable(userService.findUserDetail(user, queryRequest));
    }

    @PostMapping
    public Result addUser(@Valid User user) throws FebsException {
        try {
            this.userService.createUser(user);
            return Result.OK();
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping
    public Result updateUser(@Valid User user) throws FebsException {
        try {
            this.userService.updateUser(user);
            return Result.OK();
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message, e);
            return Result.error(message);
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteUsers(@PathVariable int id) throws FebsException {
        try {
            this.userService.deleteUser(id);
            return Result.OK();
        } catch (Exception e) {
            message = e.getMessage();
            log.error(message, e);
            return Result.error(message);
        }
    }


    @GetMapping("password/check")
    public boolean checkPassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) {
        String encryptPassword = MD5Util.encrypt(username, password);
        User user = userService.findByName(username);
        if (user != null)
            return StringUtils.equals(user.getPassword(), encryptPassword);
        else
            return false;
    }

    @PutMapping("password")
    public void updatePassword(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws FebsException {
        try {
            userService.updatePassword(username, password);
        } catch (Exception e) {
            message = "修改密码失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @PutMapping("updatePassword")
    public Result updatePassword(
            @NotBlank(message = "{required}") String oldPassword,
            @NotBlank(message = "{required}") String password,
            @NotBlank(message = "{required}") String checkPassword) throws FebsException {
        try {
            String username = JWTUtil.getCurrentUserName();
            User user=this.userService.findByName(username);
            if(!user.getPassword().equals(oldPassword)){
                return Result.error("原密码错误");
            }
            if(!checkPassword.equals(password)){
                return Result.error("新密码不一致");
            }
            userService.updatePassword(username, password);
            return Result.OK();
        } catch (Exception e) {
            message = "修改密码失败";
            log.error(message, e);
            return Result.error(message);
        }
    }

    @PutMapping("password/reset")
    @RequiresPermissions("user:reset")
    public void resetPassword(@NotBlank(message = "{required}") String usernames) throws FebsException {
        try {
            String[] usernameArr = usernames.split(StringPool.COMMA);
            this.userService.resetPassword(usernameArr);
        } catch (Exception e) {
            message = "重置用户密码失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

//    @PostMapping("excel")
//    @RequiresPermissions("user:export")
//    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws FebsException {
//        try {
//            List<User> users = this.userService.findUserDetail(user, queryRequest).getRecords();
//            ExcelKit.$Export(User.class, response).downXlsx(users, false);
//        } catch (Exception e) {
//            message = "导出Excel失败";
//            log.error(message, e);
//            throw new FebsException(message);
//        }
//    }
    @PutMapping("/{id}/updataStatus")
    public Result updataStatus(@PathVariable int id, String status) throws FebsException {
        try {
            User user = new User();
            user.setId(id);
            user.setStatus(status);
            return Result.OK(this.userService.updateById(user));
        } catch (Exception e) {
            message = "修改状态失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    // @GetMapping("/getCurrentUserInfo")
    // public Result getCurrentUserInfo() {
    //     int userId = JWTUtil.getCurrentCustomerId();
    //     User user = userService.getById(userId);
    //     Map<String, Object> userInfo = new HashMap<>();
    //     List<VueRouter<Menu>> menu = this.userManager.getUserRouters(user.getUsername());
    //     userInfo.put("menu", menu);
    //     user.setPassword("it's a secret");
    //     userInfo.put("user", user);
    //     List<Role> userRole = roleService.findUserRole(user.getUsername());
    //     if(CollectionUtils.isNotEmpty(userRole)) {
    //         List<String> roleNames = userRole.stream().map(Role::getRoleName).collect(Collectors.toList());
    //         user.setRoleName(StringUtils.join(roleNames, ";"));
    //     }
    //     return Result.OK(userInfo);
    // }
}