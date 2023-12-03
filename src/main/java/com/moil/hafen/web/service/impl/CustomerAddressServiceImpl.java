package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.authentication.JWTUtil;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CustomerAddressDao;
import com.moil.hafen.web.domain.CustomerAddress;
import com.moil.hafen.web.service.CustomerAddressService;
import org.springframework.stereotype.Service;

/**
 * @program: myhousekeep
 * @description:
 * @author: Moil
 * @create: 2022-09-14 14:42
 **/
@Service
public class CustomerAddressServiceImpl extends ServiceImpl<CustomerAddressDao, CustomerAddress> implements CustomerAddressService {
    @Override
    public IPage<CustomerAddress> getPage(QueryRequest request, CustomerAddress customerAddress) {
        Page<CustomerAddress> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<CustomerAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CustomerAddress::getCustomerId, JWTUtil.getCurrentCustomerId());
        return this.page(page, lambdaQueryWrapper);
    }
}
