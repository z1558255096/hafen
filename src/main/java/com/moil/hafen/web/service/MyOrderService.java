package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.*;

public interface MyOrderService {
    IPage<GoodsOrder> getGoodsOrderPage(QueryRequest request,Integer status);

    IPage<LessonOnlineOrder> getLessonOnlineOrderPage(QueryRequest request);

    IPage<LessonOfflineOrder> getLessonOfflineOrderPage(QueryRequest request);

    IPage<CommuneLessonOrder> getCommuneLessonOrderPage(QueryRequest request);

    IPage<CommuneTicketOrder> getCommuneTicketOrderPage(QueryRequest request);
}
