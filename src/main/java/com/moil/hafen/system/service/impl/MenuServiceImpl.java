package com.moil.hafen.system.service.impl;

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
import java.util.List;

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
        List<Menu> menuList = menuMapper.getUserMenus(username);
        List<Menu> menuTree = TreeUtils.buildTree(menuList);
        return menuTree;
    }

    @Override
    public List<Menu> getMenuTree() {
        List<Menu> menuList = menuMapper.selectList(null);
        List<Menu> menuTree = TreeUtils.buildTree(menuList);
        return menuTree;
    }
}
