package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.system.domain.RoleMenu;

import java.util.List;

public interface RoleMenuServie extends IService<RoleMenu> {

    void deleteRoleMenusByRoleId(String[] roleIds);

    void deleteRoleMenusByMenuId(String[] menuIds);

    List<RoleMenu> getRoleMenusByRoleId(int roleId);

    List<RoleMenu> getHasPathRoleMenusByRoleId(int roleId);

    /**
     * 角色分配菜单
     *
     * @param roleId  角色id
     * @param menuIds 菜单ID
     */
    void roleAllotMenu(Integer roleId, List<String> menuIds);
}
