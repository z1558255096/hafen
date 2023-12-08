package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.system.domain.Menu;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 菜单service
 * @Version 1.0.0
 * @Date 2023/12/07 03:08
 */
public interface MenuService extends IService<Menu> {
    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> getUseMenus(String username);

    /**
     * 获取菜单树
     *
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> getMenuTree();
}
