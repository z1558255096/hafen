package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.HafenCoinObtainDao;
import com.moil.hafen.web.domain.HafenCoinObtain;
import com.moil.hafen.web.service.HafenCoinObtainService;
import org.springframework.stereotype.Service;

/**
 * @Author 陈子杰
 * @Description 哈奋币规则-获取
 * @Version 1.0.0
 * @Date 2023/12/10 05:38
 */
@Service
public class HafenCoinObtainServiceImpl extends ServiceImpl<HafenCoinObtainDao, HafenCoinObtain> implements HafenCoinObtainService {
    @Override
    public IPage<HafenCoinObtain> getPage(QueryRequest request, HafenCoinObtain hafenCoinObtain) {
        Page<HafenCoinObtain> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        return this.page(page, null);
    }
}
