package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.InviteConfigDao;
import com.moil.hafen.web.domain.InviteConfig;
import com.moil.hafen.web.service.InviteConfigService;
import org.springframework.stereotype.Service;

@Service
public class InviteConfigServiceImpl extends ServiceImpl<InviteConfigDao, InviteConfig> implements InviteConfigService {
    @Override
    public IPage<InviteConfig> getPage(QueryRequest request, InviteConfig inviteConfig) {
        Page<InviteConfig> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, new LambdaQueryWrapper<>());
    }
}
