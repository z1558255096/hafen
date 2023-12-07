package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.system.dao.UserMapper;
import com.moil.hafen.system.domain.User;
import com.moil.hafen.web.dao.CommuneTicketEvaluateDao;
import com.moil.hafen.web.domain.CommuneTicketEvaluate;
import com.moil.hafen.web.service.CommuneTicketEvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommuneTicketEvaluateServiceImpl extends ServiceImpl<CommuneTicketEvaluateDao, CommuneTicketEvaluate> implements CommuneTicketEvaluateService {

    @Resource
    private UserMapper userMapper;
    @Override
    public IPage<CommuneTicketEvaluate> getPage(QueryRequest request, CommuneTicketEvaluate communeTicketEvaluate) {
        Page<CommuneTicketEvaluate> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<CommuneTicketEvaluate> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (communeTicketEvaluate.getStatus() != null) {
            lambdaQueryWrapper.eq(CommuneTicketEvaluate::getStatus, communeTicketEvaluate.getStatus());
        }
        if (communeTicketEvaluate.getUserId() != null) {
            lambdaQueryWrapper.eq(CommuneTicketEvaluate::getUserId, communeTicketEvaluate.getUserId());
        }
        lambdaQueryWrapper.eq(CommuneTicketEvaluate::getDelFlag,0).orderByDesc(CommuneTicketEvaluate::getCreateTime);
        IPage<CommuneTicketEvaluate> communeTicketEvaluateIPage = this.page(page, lambdaQueryWrapper);
        for (CommuneTicketEvaluate record : communeTicketEvaluateIPage.getRecords()) {
            User user = userMapper.selectById(record.getUserId());
            record.setNickName(user.getNickName());
            record.setPhone(user.getUsername());
        }
        return communeTicketEvaluateIPage;
    }
}
