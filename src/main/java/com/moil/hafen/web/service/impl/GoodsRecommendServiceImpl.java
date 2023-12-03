package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.GoodsRecommendDao;
import com.moil.hafen.web.domain.GoodsRecommend;
import com.moil.hafen.web.service.GoodsRecommendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsRecommendServiceImpl extends ServiceImpl<GoodsRecommendDao,GoodsRecommend> implements GoodsRecommendService {
    @Override
    public IPage<GoodsRecommend> getPage(QueryRequest request, GoodsRecommend goodsRecommend) {
        Page<GoodsRecommend> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        QueryWrapper<GoodsRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t2.status",0).eq("t2.del_flag",0);
        if(StringUtils.isNotBlank(goodsRecommend.getTitle())){
            queryWrapper.eq("t1.title", goodsRecommend.getTitle());
        }
        if(goodsRecommend.getStatus() != null){
            queryWrapper.eq("t1.status", goodsRecommend.getStatus());
        }
        if(StringUtils.isNotBlank(goodsRecommend.getGoodsName())){
            queryWrapper.eq("t2.name", goodsRecommend.getGoodsName());
        }
        if(StringUtils.isNotBlank(goodsRecommend.getCreateTimeFrom())){
            queryWrapper.ge("t1.create_time",goodsRecommend.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(goodsRecommend.getCreateTimeTo())){
            queryWrapper.le("t1.create_time",goodsRecommend.getCreateTimeTo()+" 23:59:59");
        }
        return this.baseMapper.getPage(page, queryWrapper);
    }

    @Override
    public List<GoodsRecommend> getList() {
        QueryWrapper<GoodsRecommend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t2.status",0).eq("t2.del_flag",0).eq("t1.status", 0);
        return this.baseMapper.getList(queryWrapper);
    }
}
