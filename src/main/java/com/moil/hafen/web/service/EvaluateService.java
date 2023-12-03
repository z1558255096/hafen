package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Evaluate;

public interface EvaluateService extends IService<Evaluate> {
    IPage<Evaluate> getPage(QueryRequest request, Evaluate evaluate);
}
