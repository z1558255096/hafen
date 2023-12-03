package com.moil.hafen.system.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moil.hafen.system.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {

    IPage<User> findUserDetail(Page page, @Param("user") User user);

    /**
     * 获取单个用户详情
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findDetail(String username);
    @Select("select * from (select t1.id,t1.username,work_num,t1.password,t1.nick_name,t1.status,t1.create_time,t1.modify_time as modifyTime,GROUP_CONCAT(t3.role_name) as role_name,GROUP_CONCAT(t3.id) as role_ids from t_user t1 LEFT JOIN t_user_role t2 on t1.id=t2.USER_ID LEFT JOIN t_role t3 on t2.ROLE_ID=t3.id ${ew.customSqlSegment} GROUP BY t1.id)t1 ")
    IPage<User> getpage(Page<User> page, @Param(Constants.WRAPPER) QueryWrapper<User> query);
}