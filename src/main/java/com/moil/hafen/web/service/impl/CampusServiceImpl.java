package com.moil.hafen.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CampusDao;
import com.moil.hafen.web.dao.DeptDao;
import com.moil.hafen.web.domain.Campus;
import com.moil.hafen.web.domain.Dept;
import com.moil.hafen.web.service.CampusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author 陈子杰
 * @Description 校区serviceImpl
 * @Version 1.0.0
 * @Date 2023/12/08 12:02
 */
@Service
public class CampusServiceImpl extends ServiceImpl<CampusDao, Campus> implements CampusService {
    @Resource
    private DeptDao deptDao;

    @Override
    public IPage<Campus> getPage(QueryRequest request, Campus campus) {
        Page<Campus> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(campus));
    }

    @Override
    public List<Campus> getCampusList(Campus campus) {
        return this.list(getCondition(campus));
    }

    @Override
    public List<Campus> getList() {
        List<Campus> campusList = this.list();
        if (CollectionUtil.isNotEmpty(campusList)) {
            for (Campus campus : campusList) {
                LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Dept::getCampusId, campus.getId());
                List<Dept> deptList = deptDao.selectList(queryWrapper);
                campus.setChildren(deptList);
            }
        }
        return campusList;
    }

    private LambdaQueryWrapper<Campus> getCondition(Campus campus){
        LambdaQueryWrapper<Campus> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(campus.getName())){
            lambdaQueryWrapper.eq(Campus::getName, campus.getName());
        }
        if(StringUtils.isNotBlank(campus.getTelephone())){
            lambdaQueryWrapper.eq(Campus::getTelephone, campus.getTelephone());
        }
        return lambdaQueryWrapper;
    }
}
