package com.moil.hafen.web.service;

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
}