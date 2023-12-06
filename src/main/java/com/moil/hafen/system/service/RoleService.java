package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.exception.FebsException;
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

    void createRole(Role role) throws FebsException;

    void deleteRole(int roleId) throws Exception;

    void updateRole(Role role) throws Exception;
}
