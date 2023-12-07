package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.system.domain.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    /**
     * 查找角色分页
     *
     * @param role    角色
     * @param request 分页请求对象
     * @return {@link IPage}<{@link Role}>
     */
    IPage<Role> findRoles(Role role, QueryRequest request);

    List<Role> findUserRole(String userName);

    Role findByName(String roleName);

    /**
     * 创建角色
     *
     * @param role 角色
     */
    void createRole(Role role);

    /**
     * 根据id删除角色
     *
     * @param roleId 角色id
     */
    void deleteRole(int roleId);

    /**
     * 更新角色
     *
     * @param role 角色
     */
    void updateRole(Role role) ;

    /**
     * 根据id获取角色详情
     *
     * @param id id
     * @return {@link Role}
     */
    Role detail(Integer id);
}
