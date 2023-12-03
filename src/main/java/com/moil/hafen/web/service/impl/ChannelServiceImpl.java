package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.ChannelDao;
import com.moil.hafen.web.domain.Channel;
import com.moil.hafen.web.service.ChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelDao,Channel> implements ChannelService {
    @Override
    public IPage<Channel> getPage(QueryRequest request, Channel channel) {
        Page<Channel> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<Channel> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(channel.getName())){
            lambdaQueryWrapper.eq(Channel::getName, channel.getName());
        }
        return this.page(page, lambdaQueryWrapper);
    }
}
