package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.GoodsRecommend;

import java.util.List;

public interface GoodsRecommendService extends IService<GoodsRecommend> {
    IPage<GoodsRecommend> getPage(QueryRequest request, GoodsRecommend goodsRecommend);

    List<GoodsRecommend> getList();
}
