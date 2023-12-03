package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CustomerBehaviorDao;
import com.moil.hafen.web.domain.Article;
import com.moil.hafen.web.domain.CustomerBehavior;
import com.moil.hafen.web.service.ArticleService;
import com.moil.hafen.web.service.CustomerBehaviorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerBehaviorServiceImpl extends ServiceImpl<CustomerBehaviorDao, CustomerBehavior> implements CustomerBehaviorService {
    @Resource
    private ArticleService articleService;

    @Override
    public void addCustomerBehavior(CustomerBehavior customerBehavior) {
        int customerId = JWTUtil.getCurrentCustomerId();
        customerBehavior.setCreateTime(new Date());
        customerBehavior.setCustomerId(customerId);
        this.save(customerBehavior);
        if(customerBehavior.getSource()==1) {
            int articleId = customerBehavior.getOptionId();
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.setSql(customerBehavior.getBehavior() + " = " + customerBehavior.getBehavior() + " + 1").eq(Article::getId, articleId);
            this.articleService.update(updateWrapper);
        }
    }

    @Override
    public void delete(Integer id) {
        CustomerBehavior customerBehavior = this.getById(id);
        this.removeById(id);
        if(customerBehavior.getSource() == 1){
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.setSql(customerBehavior.getBehavior() + " = " + customerBehavior.getBehavior() + " - 1").eq(Article::getId, customerBehavior.getOptionId());
            this.articleService.update(updateWrapper);
        }
    }

    @Override
    public IPage<CustomerBehavior> getPage(QueryRequest request, CustomerBehavior customerBehavior) {
        Page<CustomerBehavior> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        QueryWrapper<CustomerBehavior> queryWrapper = new QueryWrapper<>();
        if(customerBehavior.getSource()==1){
            queryWrapper.eq("t2.del_flag",0).eq("t2.status",0);
            return this.baseMapper.getArticlePage(page, queryWrapper);
        }else if (customerBehavior.getSource()==2){
            return this.baseMapper.getLessonPage(page, queryWrapper);
        } else if (customerBehavior.getSource()==3) {
            queryWrapper.eq("t2.status",0);
            return this.baseMapper.getTicketPage(page, queryWrapper);
        } else if (customerBehavior.getSource()==4) {
            queryWrapper.eq("t2.del_flag",0).eq("t2.status",0);
            return this.baseMapper.getGoodsPage(page, queryWrapper);
        }
        return null;
    }

    @Override
    public Map<String, Boolean> likeAndCollect(Integer id, Integer source, Integer type) {
        int customerId = JWTUtil.getCurrentCustomerId();
        LambdaQueryWrapper<CustomerBehavior> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CustomerBehavior::getOptionId, id).eq(CustomerBehavior::getCustomerId, customerId).eq(CustomerBehavior::getSource,source);
        if(source == 2){
            lambdaQueryWrapper.eq(CustomerBehavior::getType,type);
        }
        List<CustomerBehavior> list = this.list(lambdaQueryWrapper);
        boolean like = false;
        boolean collect = false;
        for (CustomerBehavior customerBehavior : list) {
            if(customerBehavior.getBehavior().equals("collect")){
                collect = true;
            }else if(customerBehavior.getBehavior().equals("like")){
                like = true;
            }
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("collect",collect);
        map.put("like",like);
        return map;
    }
}
