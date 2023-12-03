package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.BannerDao;
import com.moil.hafen.web.domain.Banner;
import com.moil.hafen.web.service.BannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerDao,Banner> implements BannerService {
    @Override
    public IPage<Banner> getPage(QueryRequest request, Banner banner) {
        Page<Banner> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<Banner> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Banner::getDelFlag,0);
        if(StringUtils.isNotBlank(banner.getTitle())){
            lambdaQueryWrapper.eq(Banner::getTitle, banner.getTitle());
        }
        if(banner.getStatus()!=null){
            lambdaQueryWrapper.eq(Banner::getStatus, banner.getStatus());
        }
        if(StringUtils.isNotBlank(banner.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(Banner::getCreateTime,banner.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(banner.getCreateTimeTo())){
            lambdaQueryWrapper.le(Banner::getCreateTime,banner.getCreateTimeTo()+" 23:59:59");
        }
        return this.page(page, lambdaQueryWrapper);
    }
}
