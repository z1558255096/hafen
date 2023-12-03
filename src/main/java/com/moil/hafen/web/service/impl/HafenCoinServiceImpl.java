package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.HafenCoinDao;
import com.moil.hafen.web.domain.HafenCoin;
import com.moil.hafen.web.service.HafenCoinService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HafenCoinServiceImpl extends ServiceImpl<HafenCoinDao,HafenCoin> implements HafenCoinService {
    @Override
    public IPage<HafenCoin> getPage(QueryRequest request, HafenCoin hafenCoin) {
        Page<HafenCoin> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<HafenCoin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(hafenCoin.getCustomerId() != null){
            lambdaQueryWrapper.eq(HafenCoin::getCustomerId, hafenCoin.getCustomerId());
        }
        lambdaQueryWrapper.orderByDesc(HafenCoin::getCreateTime);
        return this.page(page, lambdaQueryWrapper);
    }

    @Override
    public void saveHafenCoin(HafenCoin hafenCoin) {
        Integer customerId = hafenCoin.getCustomerId();
        if(customerId == null){
            customerId = JWTUtil.getCurrentCustomerId();
        }
        hafenCoin.setTotalCoin(getTotalCoin(customerId)+hafenCoin.getCoin());
        hafenCoin.setCreateTime(new Date());
        save(hafenCoin);
    }

    @Override
    public int getTotalCoin(Integer customerId) {
        QueryWrapper<HafenCoin> queryWrapper = new QueryWrapper<HafenCoin>();
        queryWrapper.select("sum(coin) as sumCoin").eq("customer_id",customerId);
        HafenCoin coin = this.getOne(queryWrapper);
        return coin.getSumCoin();
    }
}
