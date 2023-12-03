package com.moil.hafen.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.GoodsDao;
import com.moil.hafen.web.domain.Goods;
import com.moil.hafen.web.domain.GoodsCategory;
import com.moil.hafen.web.domain.GoodsSpecs;
import com.moil.hafen.web.service.GoodsCategoryService;
import com.moil.hafen.web.service.GoodsService;
import com.moil.hafen.web.service.GoodsSpecsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsDao,Goods> implements GoodsService {
    @Resource
    private GoodsCategoryService goodsCategoryService;
    @Resource
    private GoodsSpecsService goodsSpecsService;
    @Override
    public IPage<Goods> getPage(QueryRequest request, Goods goods) {
        Page<Goods> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Goods::getDelFlag,0);
        if(StringUtils.isNotBlank(goods.getName())){
            lambdaQueryWrapper.eq(Goods::getName, goods.getName());
        }
        if(goods.getStatus() != null){
            lambdaQueryWrapper.eq(Goods::getStatus, goods.getStatus());
        }
        if(goods.getCategoryId() != null){
            lambdaQueryWrapper.eq(Goods::getCategoryId, goods.getCategoryId());
        }
        IPage<Goods> goodsIPage = this.page(page, lambdaQueryWrapper);
        List<Integer> categoryIds = goodsIPage.getRecords().stream().map(Goods::getCategoryId).collect(Collectors.toList());
        List<GoodsCategory> goodsCategoryList = goodsCategoryService.list(new LambdaQueryWrapper<GoodsCategory>().in(GoodsCategory::getId, categoryIds));
        Map<Integer, String> goodsCategoryMap = goodsCategoryList.stream().collect(Collectors.toMap(GoodsCategory::getId, GoodsCategory::getName));
        for (Goods record : goodsIPage.getRecords()) {
            record.setCategoryName(goodsCategoryMap.get(record.getCategoryId()));
            List<GoodsSpecs> list = goodsSpecsService.list(new LambdaQueryWrapper<GoodsSpecs>().eq(GoodsSpecs::getGoodsId, record.getId())
                    .orderByAsc(GoodsSpecs::getPrice));
            String price = CollectionUtil.join(list.stream().map(GoodsSpecs::getPrice).collect(Collectors.toList()), ";");
            record.setPriceStr(price);
            record.setPrice(list.get(0).getPrice());
        }
        return goodsIPage;
    }

    @Override
    public Goods detail(Integer id) {
        Goods goods = this.getById(id);
        List<GoodsSpecs> list = goodsSpecsService.list(new LambdaQueryWrapper<GoodsSpecs>().eq(GoodsSpecs::getGoodsId, id)
                .orderByAsc(GoodsSpecs::getPrice));
        goods.setGoodsSpecsList(list);
        goods.setPrice(list.get(0).getPrice());
        return goods;
    }
}
