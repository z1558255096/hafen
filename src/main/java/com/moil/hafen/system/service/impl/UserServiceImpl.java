package com.moil.hafen.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.FebsConstant;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.system.dao.UserMapper;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.domain.UserRole;
import com.moil.hafen.system.service.UserRoleService;
import com.moil.hafen.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleService userRoleService;


    @Override
    public User findByName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }


    @Override
    public IPage<User> findUserDetail(User user, QueryRequest request) {
        try {
            Page<User> page = new Page<>();
            SortUtil.handlePageSort(request, page, "id", FebsConstant.ORDER_ASC, false);
            QueryWrapper<User> lambdaQuery = new QueryWrapper<>();
            if(!StringUtils.isBlank(user.getNickName())){
                lambdaQuery.like("nick_name",user.getNickName());
            }
            if(!StringUtils.isBlank(user.getWorkNum())){
                lambdaQuery.like("work_num",user.getWorkNum());
            }
            if(!StringUtils.isBlank(user.getUsername())){
                lambdaQuery.and(query->query.like("username",user.getUsername())
                        .or().like("nick_name",user.getUsername())
                        .or().like("work_num",user.getUsername()));
            }
            if(!StringUtils.isBlank(user.getStatus())){
                lambdaQuery.eq("t1.status",user.getStatus());
            }
            if(!StringUtils.isBlank(user.getRoleName())){
                lambdaQuery.eq("role_name",user.getRoleName());
            }
            return this.baseMapper.getpage(page, lambdaQuery);
        } catch (Exception e) {
            log.error("查询用户异常", e);
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(User user) throws Exception {
        // 创建用户
        user.setCreateTime(new Date());
        user.setModifyTime(new Date());
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(User.DEFAULT_PASSWORD);
        }
        save(user);
        setUserRoles(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) throws Exception {
        // 更新用户
//        user.setPassword(null);
        user.setModifyTime(new Date());
        updateById(user);
        setUserRoles(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(int userId) throws Exception {

        removeById(userId);
        // 删除用户角色
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,userId));
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

    private void setUserRoles(User user) {
        userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,user.getId()));
        if(StringUtils.isNotBlank(user.getRoleIds())){
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : user.getRoleIds().split(",")) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUserId(user.getId());
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }
    }
}
