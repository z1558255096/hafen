package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.HafenCoinObtainDao;
import com.moil.hafen.web.domain.HafenCoinObtain;
import com.moil.hafen.web.service.HafenCoinObtainService;
import org.springframework.stereotype.Service;

@Service
public class HafenCoinObtainServiceImpl extends ServiceImpl<HafenCoinObtainDao, HafenCoinObtain> implements HafenCoinObtainService {
    @Override
    public IPage<HafenCoinObtain> getPage(QueryRequest request, HafenCoinObtain hafenCoinObtain) {
        Page<HafenCoinObtain> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, new LambdaQueryWrapper<>());
    }
}
