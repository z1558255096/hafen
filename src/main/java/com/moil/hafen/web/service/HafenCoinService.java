package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.HafenCoin;

public interface HafenCoinService extends IService<HafenCoin> {
    IPage<HafenCoin> getPage(QueryRequest request, HafenCoin hafenCoin);

    void saveHafenCoin(HafenCoin hafenCoin);

    int getTotalCoin(Integer customerId);
}
