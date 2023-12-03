package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.EvaluateTagDao;
import com.moil.hafen.web.domain.EvaluateTag;
import com.moil.hafen.web.service.EvaluateTagService;
import org.springframework.stereotype.Service;

@Service
public class EvaluateTagServiceImpl extends ServiceImpl<EvaluateTagDao,EvaluateTag> implements EvaluateTagService {
    @Override
    public IPage<EvaluateTag> getPage(QueryRequest request, EvaluateTag evaluateTag) {
        Page<EvaluateTag> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<EvaluateTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(evaluateTag.getType() != null){
            lambdaQueryWrapper.eq(EvaluateTag::getType, evaluateTag.getType());
        }
        return this.page(page, lambdaQueryWrapper);
    }
}
