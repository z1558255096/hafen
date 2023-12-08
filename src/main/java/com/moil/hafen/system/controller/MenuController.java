package com.moil.hafen.system.controller;

import com.moil.hafen.common.controller.BaseController;
import com.moil.hafen.common.domain.Result;
import com.moil.hafen.system.domain.Menu;
import com.moil.hafen.system.manager.UserManager;
import com.moil.hafen.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈子杰
 * @Description 内部管理/菜单管理
 * @Version 1.0.0
 * @Date 2023/12/6 14:48
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/backend/menu")
@Api("内部管理/菜单管理")
public class MenuController extends BaseController {

    private String message;

    @Resource
    private UserManager userManager;
    @Resource
    private MenuService menuService;

    @GetMapping("/tree")
    @ApiOperation("获取菜单树")
    public Result<List<Menu>> getMenuTree(){
        List<Menu> menuList = menuService.getMenuTree();
        return Result.OK(menuList);
    }

    // @GetMapping("/{username}")
    // @ApiOperation("获取用户菜单")
    // public ArrayList<VueRouter<Menu>> getUserRouters(@NotBlank(message = "{required}") @PathVariable String username) {
    //     return this.userManager.getUserRouters(username);
    // }
    //
    // @GetMapping
    // public Map<String, Object> menuList(int roleId) {
    //     return this.menuService.findMenus(roleId);
    // }
    //
    // @Log("新增菜单/按钮")
    // @PostMapping
    // public void addMenu(@Valid Menu menu) throws FebsException {
    //     try {
    //         this.menuService.createMenu(menu);
    //     } catch (Exception e) {
    //         message = "新增菜单/按钮失败";
    //         log.error(message, e);
    //         throw new FebsException(message);
    //     }
    // }
    //
    // @Log("删除菜单/按钮")
    // @DeleteMapping("/{menuIds}")
    // public void deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) throws FebsException {
    //     try {
    //         String[] ids = menuIds.split(StringPool.COMMA);
    //         this.menuService.deleteMeuns(ids);
    //     } catch (Exception e) {
    //         message = "删除菜单/按钮失败";
    //         log.error(message, e);
    //         throw new FebsException(message);
    //     }
    // }
    //
    // @Log("修改菜单/按钮")
    // @PutMapping
    // public void updateMenu(@Valid Menu menu) throws FebsException {
    //     try {
    //         this.menuService.updateMenu(menu);
    //     } catch (Exception e) {
    //         message = "修改菜单/按钮失败";
    //         log.error(message, e);
    //         throw new FebsException(message);
    //     }
    // }
}
