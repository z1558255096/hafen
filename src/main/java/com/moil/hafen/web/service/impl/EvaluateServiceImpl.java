package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.EvaluateDao;
import com.moil.hafen.web.domain.Evaluate;
import com.moil.hafen.web.service.EvaluateService;
import org.springframework.stereotype.Service;

@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateDao,Evaluate> implements EvaluateService {
    @Override
    public IPage<Evaluate> getPage(QueryRequest request, Evaluate evaluate) {
        Page<Evaluate> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<Evaluate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Evaluate::getLessonId,evaluate.getLessonId()).orderByDesc(Evaluate::getCreateTime);
        return this.page(page, lambdaQueryWrapper);
    }
}
