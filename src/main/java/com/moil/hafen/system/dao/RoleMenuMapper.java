package com.moil.hafen.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moil.hafen.system.domain.RoleMenu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Select("select t1.* from  t_role_menu t1 LEFT JOIN t_menu t2 on t1.menu_id=t2.MENU_ID where t1.role_id = #{roleId} " +
            "and t2.menu_id not in (select distinct PARENT_ID as  PARENT_ID from t_menu tm  where  menu_id in (select menu_id from t_role_menu where role_id = 74))")
    List<RoleMenu> getHasPathRoleMenusByRoleId(int roleId);
}