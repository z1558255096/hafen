package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.HafenCoinUseDao;
import com.moil.hafen.web.domain.HafenCoinObtain;
import com.moil.hafen.web.domain.HafenCoinUse;
import com.moil.hafen.web.service.HafenCoinUseService;
import org.springframework.stereotype.Service;

@Service
public class HafenCoinUseServiceImpl extends ServiceImpl<HafenCoinUseDao, HafenCoinUse> implements HafenCoinUseService {
    @Override
    public IPage<HafenCoinUse> getPage(QueryRequest request, HafenCoinUse hafenCoinUse) {
        Page<HafenCoinUse> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, new LambdaQueryWrapper<>());
    }
}
