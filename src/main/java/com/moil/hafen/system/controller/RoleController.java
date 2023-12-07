package com.moil.hafen.system.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.system.domain.Role;
import com.moil.hafen.system.service.RoleMenuServie;
import com.moil.hafen.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @Author 陈子杰
 * @Description 内部管理/角色管理
 * @Version 1.0.0
 * @Date 2023/12/6 14:48
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/backend/role")
@Api("内部管理/角色管理")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;
    @Resource
    private RoleMenuServie roleMenuServie;
    private String message;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("list")
    @ApiOperation("获取角色列表")
    public Map<String, Object> roleList(QueryRequest queryRequest, Role role) {
        return getDataTable(roleService.findRoles(role, queryRequest));
    }

    @GetMapping("detail/{id}")
    @ApiOperation("根据id获取角色详情")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "int", paramType = "path")
    public Result<Role> detail(@PathVariable("id") Integer id) {
        Role role = roleService.detail(id);
        return Result.OK(role);
    }

    @PostMapping
    @ApiOperation("新增角色")
    public Result<Void> addRole(@RequestBody @Valid Role role) {
        this.roleService.createRole(role);
        return Result.OK();
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation("根据id删除角色")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "int", paramType = "path")
    public Result<Void> delete(@PathVariable("id") Integer id) {
        this.roleService.deleteRole(id);
        return Result.OK();
    }

    @ApiOperation("修改角色")
    @PutMapping
    public Result<Void> updateRole(@RequestBody Role role) {
        this.roleService.updateRole(role);
        return Result.OK();
    }

    /* --------------------------------不知道要不要用,先注释着--------------------------------------------------*/

    // @GetMapping("check/{roleName}")
    // public boolean checkRoleName(@NotBlank(message = "{required}") @PathVariable String roleName) {
    //     Role result = this.roleService.findByName(roleName);
    //     return result == null;
    // }
    //
    // @GetMapping("menu/{roleId}")
    // public List<String> getRoleMenus(@PathVariable int roleId) {
    //     List<RoleMenu> list = this.roleMenuServie.getRoleMenusByRoleId(roleId);
    //     return list.stream().map(roleMenu -> String.valueOf(roleMenu.getMenuId())).collect(Collectors.toList());
    // }
    //
    // @ApiOperation("新增角色菜单")
    // @PostMapping("/{roleId}/addMenu")
    // public Result addMenu(@RequestBody List<Tree> list, @PathVariable("roleId") int roleId) {
    //     try {
    //
    //         roleMenuServie.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
    //         if (CollectionUtils.isEmpty(list)) {
    //             return Result.OK();
    //         }
    //         List<RoleMenu> roleMenuList = new ArrayList<>();
    //         for (Tree tree : list) {
    //             getMenuId(tree, roleMenuList, roleId);
    //         }
    //         if (CollectionUtils.isNotEmpty(roleMenuList)) {
    //             roleMenuServie.saveBatch(roleMenuList);
    //         }
    //         return Result.OK();
    //     } catch (Exception e) {
    //         message = e.getMessage();
    //         log.error(message, e);
    //         return Result.error(message);
    //     }
    // }
    //
    // private void getMenuId(Tree<Menu> tree, List<RoleMenu> list, int roleId) {
    //     if (tree == null) {
    //         return;
    //     }
    //     RoleMenu roleMenu = new RoleMenu();
    //     roleMenu.setMenuId(Integer.parseInt(tree.getId()));
    //     roleMenu.setRoleId(roleId);
    //     list.add(roleMenu);
    //     if (CollectionUtils.isEmpty(tree.getChildren())) {
    //         return;
    //     }
    //     for (Tree<Menu> child : tree.getChildren()) {
    //         getMenuId(child, list, roleId);
    //     }
    //
    // }
}
