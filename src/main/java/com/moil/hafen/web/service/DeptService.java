package com.moil.hafen.web.service;

import com.moil.hafen.web.domain.Dept;

import java.util.List;

/**
 * @Author 陈子杰
 * @Description 校区部门表service
 * @Version 1.0.0
 * @Date 2023/12/7 12:18
 */
public interface DeptService {
    /**
     * 添加校区默认部门
     *
     * @param campusId 校区id
     */
    void saveDefaultDept(Integer campusId);

    /**
     * 添加部门
     *
     * @param dept dept
     */
    void add(Dept dept);

    /**
     * 根据校区id删除部门
     *
     * @param campusId 校区id
     */
    void delete(Integer campusId);

    /**
     * 修改部门
     *
     * @param dept dept
     */
    void update(Dept dept);

    /**
     * 删除dept
     *
     * @param deptId 部门id
     */
    void deleteDept(Integer deptId);

    /**
     * 按校园id获取列表
     *
     * @param campusId 校园id
     * @return {@link List}<{@link Dept}>
     */
    List<Dept> getListByCampusId(Integer campusId);
}
