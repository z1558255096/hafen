package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.system.domain.User;


public interface UserService extends IService<User> {

    /**
     * 通过用户名查找用户
     *
     * @param username username
     * @return user
     */
    User findByName(String username);

    /**
     * 查询用户详情，包括基本信息，用户角色，用户部门
     *
     * @param user user
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<User> findUserDetail(User user, QueryRequest queryRequest);

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(User user) throws Exception;

    /**
     * 修改用户
     *
     * @param user user
     */
    void updateUser(User user) throws Exception;

    /**
     * 删除用户
     *
     * @param userId 用户 id数组
     */
    void deleteUser(int userId) throws Exception;

    /**
     * 更新用户密码
     *
     * @param username 用户名
     * @param password 新密码
     */
    void updatePassword(String username, String password) throws Exception;

    /**
     * 重置密码
     *
     * @param usernames 用户集合
     */
    void resetPassword(String[] usernames) throws Exception;

}
