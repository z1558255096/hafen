package com.moil.hafen.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moil.hafen.system.domain.Role;
import com.moil.hafen.system.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户Id删除该用户的角色关系
     *
     * @param userId 用户ID
     * @return boolean
     * @author lzx
     * @date 2019年03月04日17:46:49
     */
    Boolean deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据角色Id删除该角色的用户关系
     *
     * @param roleId 角色ID
     * @return boolean
     * @author lzx
     * @date 2019年03月04日17:47:16
     */
    Boolean deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户id查询角色信息
     *
     * @param userId 用户id
     * @return {@link List}<{@link Role}>
     */
    List<Role> getRoleByUserId(@Param("userId") Integer userId);
}