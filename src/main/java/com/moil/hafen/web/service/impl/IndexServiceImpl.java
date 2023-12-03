package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.web.domain.Banner;
import com.moil.hafen.web.domain.GoodsRecommend;
import com.moil.hafen.web.domain.LessonRecommend;
import com.moil.hafen.web.service.BannerService;
import com.moil.hafen.web.service.GoodsRecommendService;
import com.moil.hafen.web.service.IndexService;
import com.moil.hafen.web.service.LessonRecommendService;
import com.moil.hafen.web.vo.IndexVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {
    @Resource
    private BannerService bannerService;
    @Resource
    private LessonRecommendService lessonRecommendService;
    @Resource
    private GoodsRecommendService goodsRecommendService;
    @Override
    public IndexVo getIndex() {
        List<Banner> bannerList = bannerService.list(new LambdaQueryWrapper<Banner>().eq(Banner::getDelFlag,0).eq(Banner::getStatus,0));
        List<LessonRecommend> lessonRecommendList = lessonRecommendService.getList();
        List<GoodsRecommend> goodsRecommendList = goodsRecommendService.getList();
        return new IndexVo(bannerList, lessonRecommendList, goodsRecommendList);
    }
}
