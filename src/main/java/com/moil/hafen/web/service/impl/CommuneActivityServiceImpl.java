package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CommuneActivityDao;
import com.moil.hafen.web.domain.CommuneActivity;
import com.moil.hafen.web.service.CommuneActivityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class CommuneActivityServiceImpl extends ServiceImpl<CommuneActivityDao,CommuneActivity> implements CommuneActivityService {
    @Override
    public IPage<CommuneActivity> getPage(QueryRequest request, CommuneActivity communeActivity) {

        Page<CommuneActivity> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(communeActivity));
    }
    private LambdaQueryWrapper<CommuneActivity> getCondition(CommuneActivity communeActivity){
        LambdaQueryWrapper<CommuneActivity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(communeActivity.getName())){
            lambdaQueryWrapper.eq(CommuneActivity::getName, communeActivity.getName());
        }
        if(communeActivity.getStatus() != null){
            lambdaQueryWrapper.eq(CommuneActivity::getStatus, communeActivity.getStatus());
        }
        if(communeActivity.getIsShow() != null){
            lambdaQueryWrapper.eq(CommuneActivity::getIsShow, communeActivity.getIsShow());
        }
        if(StringUtils.isNotBlank(communeActivity.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(CommuneActivity::getCreateTime,communeActivity.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(communeActivity.getCreateTimeTo())){
            lambdaQueryWrapper.le(CommuneActivity::getCreateTime,communeActivity.getCreateTimeTo()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(communeActivity.getActivityTimeFrom())){
            lambdaQueryWrapper.ge(CommuneActivity::getActivityTime,communeActivity.getActivityTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(communeActivity.getActivityTimeTo())){
            lambdaQueryWrapper.le(CommuneActivity::getActivityTime,communeActivity.getActivityTimeTo()+" 23:59:59");
        }
        lambdaQueryWrapper.eq(CommuneActivity::getDelFlag,0).orderByDesc(CommuneActivity::getCreateTime);
        return lambdaQueryWrapper;
    }
}
