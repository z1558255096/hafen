package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.NoticeDao;
import com.moil.hafen.web.domain.Notice;
import com.moil.hafen.web.service.NoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao,Notice> implements NoticeService {
    @Override
    public IPage<Notice> getPage(QueryRequest request, Notice notice) {
        Page<Notice> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<Notice> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Notice::getDelFlag,0);
        if(StringUtils.isNotBlank(notice.getTitle())){
            lambdaQueryWrapper.eq(Notice::getTitle, notice.getTitle());
        }
        if(StringUtils.isNotBlank(notice.getCreateTimeFrom())){
            lambdaQueryWrapper.ge(Notice::getCreateTime,notice.getCreateTimeFrom()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(notice.getCreateTimeTo())){
            lambdaQueryWrapper.le(Notice::getCreateTime,notice.getCreateTimeTo()+" 23:59:59");
        }
        return this.page(page, lambdaQueryWrapper);
    }
}
