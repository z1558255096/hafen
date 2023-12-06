package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.common.domain.QueryRequest;
import com.moil.hafen.common.utils.SortUtil;
import com.moil.hafen.web.dao.CommuneTicketDao;
import com.moil.hafen.web.domain.CommuneTicket;
import com.moil.hafen.web.domain.CommuneTicketAdvance;
import com.moil.hafen.web.domain.CommuneTicketAdvanceOption;
import com.moil.hafen.web.service.CommuneTicketAdvanceOptionService;
import com.moil.hafen.web.service.CommuneTicketAdvanceService;
import com.moil.hafen.web.service.CommuneTicketService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommuneTicketServiceImpl extends ServiceImpl<CommuneTicketDao, CommuneTicket> implements CommuneTicketService {
    @Resource
    private CommuneTicketAdvanceService communeTicketAdvanceService;
    @Resource
    private CommuneTicketAdvanceOptionService communeTicketAdvanceOptionService;

    @Override
    public IPage<CommuneTicket> getPage(QueryRequest request, CommuneTicket communeTicket) {
        Page<CommuneTicket> page = new Page<>();
        SortUtil.handlePageSort(request, page, true);
        LambdaQueryWrapper<CommuneTicket> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(communeTicket.getName())){
            lambdaQueryWrapper.eq(CommuneTicket::getName, communeTicket.getName());
        }
        if(communeTicket.getStatus() != null){
            lambdaQueryWrapper.eq(CommuneTicket::getStatus, communeTicket.getStatus());
        }
        return this.page(page, lambdaQueryWrapper);
    }

    @Override
    public CommuneTicket detail(Integer id) {
        CommuneTicket communeTicket = this.getById(id);
        List<CommuneTicketAdvance> communeTicketAdvanceList = communeTicketAdvanceService.list(new LambdaQueryWrapper<CommuneTicketAdvance>().eq(CommuneTicketAdvance::getTicketId, id)
                .orderByAsc(CommuneTicketAdvance::getIndex));
        for (CommuneTicketAdvance communeTicketAdvance : communeTicketAdvanceList) {
            List<CommuneTicketAdvanceOption> communeTicketAdvanceOptions = communeTicketAdvanceOptionService.list(new LambdaQueryWrapper<CommuneTicketAdvanceOption>()
                    .eq(CommuneTicketAdvanceOption::getAdvanceId,communeTicketAdvance.getId()).orderByAsc(CommuneTicketAdvanceOption::getIndex));
            communeTicketAdvance.setCommuneTicketAdvanceOptionList(communeTicketAdvanceOptions);
        }
        communeTicket.setCommuneTicketAdvanceList(communeTicketAdvanceList);
        return communeTicket;

    }
}
