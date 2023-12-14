package com.moil.hafen.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.system.domain.User;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 用户服务
 * @Version 1.0.0
 * @Date 2023/12/09 08:34
 */
public interface UserService extends IService<User> {

    /**
     * 通过用户名查找用户
     *
     * @param username username
     * @return user
     */
    User findByName(String username);

    /**
     * 查找用户信息分页
     *
     * @param user         user
     * @param queryRequest queryRequest
     * @return IPage
     */
    IPage<User> userPage(User user, QueryRequest queryRequest);

    /**
     * 新增用户
     *
     * @param user user
     */
    void createUser(User user) throws Exception;

    /**
     * 根据校区id获取用户列表
     *
     * @param campusId 校园id
     * @return {@link List}<{@link User}>
     */
    List<User> listByCampusId(Integer campusId);
}
