package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CampusDao;
import com.moil.hafen.web.domain.Campus;
import com.moil.hafen.web.service.CampusService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusServiceImpl extends ServiceImpl<CampusDao, Campus> implements CampusService {
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
