package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.StaffManagementDao;
import com.moil.hafen.web.domain.Campus;
import com.moil.hafen.web.domain.StaffManagement;
import com.moil.hafen.web.service.StaffManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class StaffManagementServiceImpl extends ServiceImpl<StaffManagementDao, StaffManagement> implements StaffManagementService {
    @Override
    public IPage<StaffManagement> getPage(QueryRequest request, StaffManagement staffManagement) {
        Page<StaffManagement> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<StaffManagement> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(staffManagement.getName())){
            lambdaQueryWrapper.and(lq->lq.eq(StaffManagement::getName, staffManagement.getName())
                    .or().eq(StaffManagement::getAccount, staffManagement.getName())
                    .or().eq(StaffManagement::getMobile, staffManagement.getName()));
        }
        if(staffManagement.getStatus() != null){
            lambdaQueryWrapper.eq(StaffManagement::getStatus,staffManagement.getStatus());
        }
        return this.page(page, lambdaQueryWrapper);
    }
}
