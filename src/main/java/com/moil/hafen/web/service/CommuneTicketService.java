package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.CommuneTicket;

public interface CommuneTicketService extends IService<CommuneTicket> {
    IPage<CommuneTicket> getPage(QueryRequest request, CommuneTicket communeTicket);

    CommuneTicket detail(Integer id);

    Boolean updateTicketCount(Integer ticketId, Integer count);
}
