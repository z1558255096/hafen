package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.FeedbackDao;
import com.moil.hafen.web.domain.Feedback;
import com.moil.hafen.web.service.FeedbackService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackDao,Feedback> implements FeedbackService {
    @Override
    public IPage<Feedback> getPage(QueryRequest request, Feedback feedback) {
        Page<Feedback> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, getCondition(feedback));
    }
    private LambdaQueryWrapper<Feedback> getCondition(Feedback feedback){
        LambdaQueryWrapper<Feedback> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(feedback.getNickname())){
            lambdaQueryWrapper.eq(Feedback::getNickname, feedback.getNickname());
        }
        if(StringUtils.isNotBlank(feedback.getType())){
            lambdaQueryWrapper.eq(Feedback::getType, feedback.getType());
        }
        if(feedback.getStatus() != null){
            lambdaQueryWrapper.eq(Feedback::getStatus, feedback.getStatus());
        }
        if(StringUtils.isNotBlank(feedback.getMobile())){
            lambdaQueryWrapper.eq(Feedback::getMobile, feedback.getMobile());
        }
        if(StringUtils.isNotBlank(feedback.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(Feedback::getCreateTime,feedback.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(feedback.getCreateTimeTo())){
            lambdaQueryWrapper.le(Feedback::getCreateTime,feedback.getCreateTimeTo()+" 23:59:59");
        }
        return lambdaQueryWrapper;
    }
}