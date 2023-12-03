package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.UserTagDao;
import com.moil.hafen.web.domain.UserTag;
import com.moil.hafen.web.service.UserTagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagDao,UserTag> implements UserTagService {
    @Override
    public IPage<UserTag> getPage(QueryRequest request, UserTag userTag) {
        Page<UserTag> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<UserTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(userTag.getName())){
            lambdaQueryWrapper.eq(UserTag::getName, userTag.getName());
        }
        return this.page(page, lambdaQueryWrapper);
    }
}
