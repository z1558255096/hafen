package com.moil.hafen.web.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import com.moil.hafen.common.constant.DeptConstant;
import com.moil.hafen.common.exception.FebsException;
import com.moil.hafen.common.mapper.LambdaQueryWrapperX;
import com.moil.hafen.web.dao.DeptDao;
import com.moil.hafen.web.domain.Dept;
import com.moil.hafen.web.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 校区部门表serviceImpl
 * @Version 1.0.0
 * @Date 2023/12/7 12:18
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptDao dao;

    @Override
    public void saveDefaultDept(Integer campusId) {
        for (int i = 0; i < 2; i++) {
            Dept dept = new Dept();
            dept.setDeptName(DeptConstant.MARKET_DEPT);
            dept.setIsDefault(DeptConstant.DEFAULT_DEPT);
            dept.setCampusId(campusId);
            dept.setCreateTime(new Date());
            dept.setModifyTime(new Date());
            dao.insert(dept);
        }
    }

    @Override
    public void add(Dept dept) {
        dept.setCreateTime(new Date());
        dept.setModifyTime(new Date());
        dao.insert(dept);
    }

    @Override
    public void delete(Integer campusId) {
        dao.delete(new LambdaQueryWrapperX<Dept>().eq(Dept::getCampusId, campusId));
    }

    @Override
    public void update(Dept dept) {
        LambdaQueryWrapperX<Dept> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Dept::getDeptId, dept.getDeptId());
        Dept deptOne = dao.selectOne(queryWrapperX);
        if (ObjUtil.isNotEmpty(deptOne)) {
            Assert.isTrue(deptOne.getIsDefault().equals(1), () -> new FebsException("默认部门不可修改"));
        }
        dao.updateById(dept);
    }

    @Override
    public void deleteDept(Integer deptId) {
        Dept dept = dao.selectById(deptId);
        if (ObjUtil.isNotEmpty(dept)) {
            Assert.isTrue(dept.getIsDefault().equals(1), () -> new FebsException("默认部门不可删除"));
        }
        dao.deleteById(deptId);
    }
}
