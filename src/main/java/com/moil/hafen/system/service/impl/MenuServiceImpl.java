package com.moil.hafen.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.utils.TreeUtils;
import com.moil.hafen.system.dao.MenuMapper;
import com.moil.hafen.system.domain.Menu;
import com.moil.hafen.system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @Author 陈子杰
 * @Description 菜单service实现类
 * @Version 1.0.0
 * @Date 2023/12/07 03:51
 */
@Slf4j
@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getUseMenus(String username) {
        List<Menu> menus = menuMapper.selectList(null);
        List<Menu> userMenus = menuMapper.getUserMenus(username);
        List<Menu> menuAndParents = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userMenus)) {
            for (Menu userMenu : userMenus) {
                addParentMenus(menus, userMenu, menuAndParents);
            }
        }
        menuAndParents = menuAndParents.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(Menu::getMenuId))),
                ArrayList::new));;
        List<Menu> menuTree = TreeUtils.buildTree(new ArrayList<>(menuAndParents));
        return menuTree;
    }

    private void addParentMenus(List<Menu> allMenus, Menu currentMenu, List<Menu> result) {
        result.add(currentMenu);
        Integer parentId = currentMenu.getParentId();
        if (ObjUtil.isNotEmpty(parentId)) {
            for (Menu menu : allMenus) {
                if (parentId.equals(menu.getMenuId())) {
                    addParentMenus(allMenus, menu, result);
                    break;
                }
            }
        }
    }


    @Override
    public List<Menu> getMenuTree() {
        List<Menu> menuList = menuMapper.selectList(null);
        List<Menu> menuTree = TreeUtils.buildTree(menuList);
        return menuTree;
    }
}
