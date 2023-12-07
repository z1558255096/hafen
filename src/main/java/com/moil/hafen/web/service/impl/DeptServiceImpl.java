package com.moil.hafen.web.service.impl;

import com.moil.hafen.common.constant.DeptConstant;
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
}
