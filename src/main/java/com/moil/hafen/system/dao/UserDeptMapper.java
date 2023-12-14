package com.moil.hafen.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.system.domain.UserDept;
import com.moil.hafen.system.vo.DeptCampusVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 用户部门关联表
 * @Version 1.0.0
 * @Date 2023/12/9 20:48
 */
public interface UserDeptMapper extends BaseMapper<UserDept> {
    List<DeptCampusVo> getDeptCampusByUserId(@Param("userId") Integer userId);

    /**
     * 根据校区id获取用户列表
     *
     * @param campusId 校园id
     * @return {@link List}<{@link User}>
     */
    List<User> getListByCampusId(@Param("campusId") Integer campusId);
}
