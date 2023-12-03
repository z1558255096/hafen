package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.CustomerBehavior;

import java.util.Map;

public interface CustomerBehaviorService extends IService<CustomerBehavior> {
    void addCustomerBehavior(CustomerBehavior customerBehavior);

    void delete(Integer id);

    IPage<CustomerBehavior> getPage(QueryRequest request, CustomerBehavior customerBehavior);

    Map<String, Boolean> likeAndCollect(Integer id, Integer source, Integer type);
}
