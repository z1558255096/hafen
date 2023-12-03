package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Goods;

public interface GoodsService extends IService<Goods> {
    IPage<Goods> getPage(QueryRequest request, Goods goods);

    Goods detail(Integer id);
}
