package com.moil.hafen.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.mapper.LambdaQueryWrapperX;
import com.moil.hafen.system.service.RoleService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.system.dao.RoleMapper;
import com.moil.hafen.system.dao.RoleMenuMapper;
import com.moil.hafen.system.domain.Role;
import com.moil.hafen.system.domain.RoleMenu;
import com.moil.hafen.system.domain.UserRole;
import com.moil.hafen.system.manager.UserManager;
import com.moil.hafen.system.service.RoleMenuServie;
import com.moil.hafen.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleMenuServie roleMenuService;
    @Resource
    private UserManager userManager;

    @Override
    public IPage<Role> findRoles(Role role, QueryRequest request) {
        LambdaQueryWrapperX<Role> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.likeIfPresent(Role::getRoleName, role.getRoleName());
        queryWrapper.geIfPresent(Role::getCreateTime, role.getCreateTimeFrom()).leIfPresent(Role::getCreateTime, role.getCreateTimeTo());
        queryWrapper.eqIfPresent(Role::getStatus, role.getStatus());
        // if (StringUtils.isNotBlank(role.getRoleName())) {
        //     queryWrapper.like(Role::getRoleName, role.getRoleName());
        // }
        // if (StringUtils.isNotBlank(role.getCreateTimeFrom()) && StringUtils.isNotBlank(role.getCreateTimeTo())) {
        //     queryWrapper.ge(Role::getCreateTime, role.getCreateTimeFrom()).le(Role::getCreateTime, role.getCreateTimeTo());
        // }
        // if (StringUtils.isNotBlank(role.getStatus()) && !role.getStatus().equals("全部")) {
        //     queryWrapper.eq(Role::getStatus, role.getStatus());
        // }
        Page<Role> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, queryWrapper);

    }

    @Override
    public List<Role> findUserRole(String userName) {
        return baseMapper.findUserRole(userName);
    }

    @Override
    public Role findByName(String roleName) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, roleName));
    }

    @Override
    public void createRole(Role role) throws FebsException {
        int count = count(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, role.getRoleName()));
        if (count > 0) {
            throw new FebsException("角色名已存在");
        }
        role.setModifyTime(new Date());
        role.setCreateTime(new Date());
        this.save(role);
    }

    @Override
    public void deleteRole(int roleId) throws Exception {
        baseMapper.deleteById(roleId);
        this.roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));
        this.userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, roleId));

    }

    @Override
    public void updateRole(Role role) throws Exception {
        int count = count(new LambdaQueryWrapper<Role>().eq(Role::getRoleName, role.getRoleName()).ne(Role::getId, role.getId()));
        if (count > 0) {
            throw new FebsException("角色名已存在");
        }
        role.setModifyTime(new Date());
        baseMapper.updateById(role);
    }

    private void setRoleMenus(Role role, String[] menuIds) {
        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu rm = new RoleMenu();
            rm.setMenuId(Integer.valueOf(menuId));
            rm.setRoleId(role.getId());
            this.roleMenuMapper.insert(rm);
        });
    }
}
