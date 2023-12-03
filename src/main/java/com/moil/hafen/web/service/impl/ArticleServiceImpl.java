package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.ArticleDao;
import com.moil.hafen.web.domain.Article;
import com.moil.hafen.web.service.ArticleService;
import com.moil.hafen.web.service.CustomerBehaviorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao,Article> implements ArticleService {
    @Resource
    private CustomerBehaviorService CustomerBehaviorService;
    @Override
    public IPage<Article> getPage(QueryRequest request, Article article) {
        Page<Article> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getDelFlag,0);
        if(StringUtils.isNotBlank(article.getTitle())){
            lambdaQueryWrapper.eq(Article::getTitle, article.getTitle());
        }
        if(article.getStatus()!=null){
            lambdaQueryWrapper.eq(Article::getStatus, article.getStatus());
        }
        if(StringUtils.isNotBlank(article.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(Article::getCreateTime,article.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(article.getCreateTimeTo())){
            lambdaQueryWrapper.le(Article::getCreateTime,article.getCreateTimeTo()+" 23:59:59");
        }
        if(article.getCategoryId()!=null){
            lambdaQueryWrapper.eq(Article::getCategoryId, article.getCategoryId());
        }
        if(article.getRecommendStatus()!=null){
            lambdaQueryWrapper.eq(Article::getRecommendStatus, article.getRecommendStatus());
        }
        return this.page(page, lambdaQueryWrapper);
    }
}
