package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.ArticleCategoryDao;
import com.moil.hafen.web.domain.Article;
import com.moil.hafen.web.domain.ArticleCategory;
import com.moil.hafen.web.service.ArticleCategoryService;
import com.moil.hafen.web.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryDao,ArticleCategory> implements ArticleCategoryService {
    @Resource
    private ArticleService articleService;
    @Override
    public IPage<ArticleCategory> getPage(QueryRequest request, ArticleCategory articleCategory) {
        Page<ArticleCategory> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<ArticleCategory> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ArticleCategory::getDelFlag,0).orderByDesc(ArticleCategory::getWeight);
        IPage<ArticleCategory> articleCategoryIPage = this.page(page, lambdaQueryWrapper);
        for (ArticleCategory record : articleCategoryIPage.getRecords()) {
            int count = articleService.count(new LambdaQueryWrapper<Article>().eq(Article::getDelFlag, 0)
                    .eq(Article::getCategoryId, record.getId()));
            record.setCount(count);
        }
        return articleCategoryIPage;
    }
}
