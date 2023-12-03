package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.Feedback;

public interface FeedbackService extends IService<Feedback> {
    IPage<Feedback> getPage(QueryRequest request, Feedback feedback);
}
