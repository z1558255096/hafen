package com.moil.hafen.system.manager;

import com.moil.hafen.system.domain.Menu;
import com.moil.hafen.system.domain.Role;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.service.MenuService;
import com.moil.hafen.system.service.RoleService;
import com.moil.hafen.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
     * 获取用户菜单
     *
     * @param username 用户名
     * @return {@link List}<{@link Menu}>
     */
    public List<Menu> getUserMenus(String username) {
        List<Menu> menuList = menuService.getUseMenus(username);
        return menuList;
    }
}
