package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.CommuneTicketEvaluate;

public interface CommuneTicketEvaluateService extends IService<CommuneTicketEvaluate> {
    IPage<CommuneTicketEvaluate> getPage(QueryRequest request, CommuneTicketEvaluate communeTicketEvaluate);

}
