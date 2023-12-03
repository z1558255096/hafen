package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.system.domain.RoleMenu;

import java.util.List;

public interface RoleMenuServie extends IService<RoleMenu> {

    void deleteRoleMenusByRoleId(String[] roleIds);

    void deleteRoleMenusByMenuId(String[] menuIds);

    List<RoleMenu> getRoleMenusByRoleId(int roleId);

    List<RoleMenu> getHasPathRoleMenusByRoleId(int roleId);
}
