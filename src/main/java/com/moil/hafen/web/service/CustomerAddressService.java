package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.web.domain.CustomerAddress;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-09-14 14:36
 **/
public interface CustomerAddressService extends IService<CustomerAddress> {
    IPage<CustomerAddress> getPage(QueryRequest request, CustomerAddress customerAddress);
}
