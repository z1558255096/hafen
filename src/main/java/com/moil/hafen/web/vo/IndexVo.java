package com.moil.hafen.web.vo;

import com.moil.hafen.web.domain.Banner;
import com.moil.hafen.web.domain.GoodsRecommend;
import com.moil.hafen.web.domain.LessonRecommend;
import lombok.Data;

import java.util.List;

@Data
public class IndexVo {
    private List<Banner> bannerList;
    private List<LessonRecommend> lessonRecommendList;
    private List<GoodsRecommend> goodsRecommendList;

    public IndexVo(List<Banner> bannerList, List<LessonRecommend> lessonRecommendList, List<GoodsRecommend> goodsRecommendList) {
        this.bannerList = bannerList;
        this.lessonRecommendList = lessonRecommendList;
        this.goodsRecommendList = goodsRecommendList;
    }
}
