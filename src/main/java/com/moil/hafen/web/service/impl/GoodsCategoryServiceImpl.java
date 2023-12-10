package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.GoodsCategoryDao;
import com.moil.hafen.web.domain.GoodsCategory;
import com.moil.hafen.web.service.GoodsCategoryService;
import org.springframework.stereotype.Service;

@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryDao, GoodsCategory> implements GoodsCategoryService {
    @Override
    public IPage<GoodsCategory> getPage(QueryRequest request, GoodsCategory goodsCategory) {

        Page<GoodsCategory> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<GoodsCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GoodsCategory::getDelFlag,0).orderByDesc(GoodsCategory::getWeight);
        return this.page(page, lambdaQueryWrapper);
    }
}
