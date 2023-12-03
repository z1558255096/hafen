package com.moil.hafen.system.manager;

import com.moil.hafen.system.domain.Menu;
import com.moil.hafen.system.service.MenuService;
import com.moil.hafen.system.service.RoleService;
import com.moil.hafen.system.service.UserService;
import com.moil.hafen.common.domain.router.RouterMeta;
import com.moil.hafen.common.domain.router.VueRouter;
import com.moil.hafen.common.utils.TreeUtil;
import com.moil.hafen.system.domain.Role;
import com.moil.hafen.system.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 封装一些和 User相关的业务操作
 */
@Service
public class UserManager {

    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private UserService userService;

    /**
     * 通过用户名获取用户基本信息
     *
     * @param username 用户名
     * @return 用户基本信息
     */
    public User getUser(String username) {
        return this.userService.findByName(username);
    }

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    public Set<String> getUserRoles(String username) {
        List<Role> roleList = this.roleService.findUserRole(username);
        return roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
    }

    /**
     * 通过用户名获取用户权限集合
     *
     * @param username 用户名
     * @return 权限集合
     */
    public Set<String> getUserPermissions(String username) {
        List<Menu> permissionList = this.menuService.findUserPermissions(username);
        return permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
    }

    /**
     * 通过用户名构建 Vue路由
     *
     * @param username 用户名
     * @return 路由集合
     */
    public ArrayList<VueRouter<Menu>> getUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.menuService.findUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            route.setId(menu.getMenuId().toString());
            route.setParentId(menu.getParentId().toString());
            route.setIcon(menu.getIcon());
            route.setPath(menu.getPath());
            route.setComponent(menu.getComponent());
            route.setName(menu.getMenuName());
            route.setMeta(new RouterMeta(true, null));
            route.setType(menu.getType());
            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

}
