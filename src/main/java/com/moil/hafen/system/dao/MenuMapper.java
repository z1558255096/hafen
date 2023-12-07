package com.moil.hafen.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moil.hafen.system.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 菜单mapper
 * @Version 1.0.0
 * @Date 2023/12/07 04:17
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return {@link List}<{@link Menu}>
     */
    List<Menu> getUserMenus(@Param("username") String username);
}