package com.moil.hafen.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.FebsConstant;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.mapper.LambdaQueryWrapperX;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.system.dao.UserDeptMapper;
import com.moil.hafen.system.dao.UserMapper;
import com.moil.hafen.system.dao.UserRoleMapper;
import com.moil.hafen.system.domain.Role;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.domain.UserDept;
import com.moil.hafen.system.domain.UserRole;
import com.moil.hafen.system.service.UserRoleService;
import com.moil.hafen.system.service.UserService;
import com.moil.hafen.system.vo.DeptCampusVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserDeptMapper userDeptMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public User findByName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public IPage<User> userPage(User user, QueryRequest request) {
        Page<User> page = new Page<>();
        SortUtil.handlePageSort(request, page, "id", FebsConstant.ORDER_ASC, false);
        LambdaQueryWrapperX<User> lambdaQuery = new LambdaQueryWrapperX<>();
        lambdaQuery.likeTwoOrIfPresent(User::getUsername, User::getNickName, user.getKeyword());
        lambdaQuery.eqIfPresent(User::getStatus, user.getStatus());
        IPage<User> userIPage = userMapper.selectPage(page, lambdaQuery);
        List<User> records = userIPage.getRecords();
        if (CollectionUtil.isNotEmpty(records)) {
            for (User record : records) {
                List<Role> rolelist = userRoleMapper.getRoleByUserId(record.getId());
                if (CollectionUtil.isNotEmpty(rolelist)) {
                    List<Integer> roleIds = rolelist.stream().map(Role::getId).collect(Collectors.toList());
                    record.setRoleIds(roleIds);
                    List<String> roleNames = rolelist.stream().map(Role::getRoleName).collect(Collectors.toList());
                    record.setRoleNames(roleNames);
                    List<DeptCampusVo> deptDeptCampusVos = userDeptMapper.getDeptCampusByUserId(record.getId());
                    List<Integer> deptIds = deptDeptCampusVos.stream().map(DeptCampusVo::getDeptId).collect(Collectors.toList());
                    List<String> deptNames = deptDeptCampusVos.stream().map(DeptCampusVo::getDeptName).collect(Collectors.toList());
                    List<Integer> campusIds = deptDeptCampusVos.stream().map(DeptCampusVo::getCampusId).collect(Collectors.toList());
                    List<String> campusIdNames = deptDeptCampusVos.stream().map(DeptCampusVo::getCampusName).collect(Collectors.toList());
                    record.setDeptIds(deptIds);
                    record.setDeptNames(deptNames);
                    record.setCampusIds(campusIds);
                    record.setCampusNames(campusIdNames);
                }
            }
        }
        return userIPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user) throws Exception {
        // 创建用户
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(User.DEFAULT_PASSWORD);
        }
        save(user);
        setUserRoles(user);
        setUserDept(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        // 更新用户
        user.setModifyTime(new Date());
        updateById(user);
        setUserRoles(user);
        setUserDept(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(int userId) throws Exception {

        removeById(userId);
        // 删除用户角色
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) throws Exception {
        User user = new User();
        user.setPassword(password);

        this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    @Transactional
    public void resetPassword(String[] usernames) throws Exception {
        for (String username : usernames) {

            User user = new User();
            user.setPassword(User.DEFAULT_PASSWORD);

            this.baseMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        }

    }

    /**
     * 设置用户角色
     *
     * @param user 用户
     */
    private void setUserRoles(User user) {
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()));
        if (CollectionUtil.isNotEmpty(user.getRoleIds())) {
            List<UserRole> userRoles = new ArrayList<>();
            for (Integer roleId : user.getRoleIds()) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(user.getId());
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }
    }

    /**
     * 设置用户部门
     *
     * @param user 用户
     */
    private void setUserDept(User user) {
        userDeptMapper.delete(new LambdaQueryWrapper<UserDept>().eq(UserDept::getUserId, user.getId()));
        if (CollectionUtil.isNotEmpty(user.getDeptIds())) {
            List<UserDept> userDepts = new ArrayList<>();
            for (Integer deptId : user.getDeptIds()) {
                UserDept userDept = new UserDept();
                userDept.setDeptId(deptId);
                userDept.setUserId(user.getId());
                userDeptMapper.insert(userDept);
            }
        }
    }
}
