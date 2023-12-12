package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.moil.hafen.web.domain.*;
import com.moil.hafen.web.service.*;
import com.moil.hafen.web.vo.CommuneWebVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommuneWebServiceImpl implements CommuneWebService {
    @Resource
    private CommuneWebBannerService communeWebBannerService;
    @Resource
    private CommuneWebHeaderService communeWebHeaderService;
    @Resource
    private CommuneWebLessonService communeWebLessonService;
    @Resource
    private CommuneTicketService communeTicketService;
    @Resource
    private CommuneActivityService communeActivityService;

    @Override
    public CommuneWebVo index() {
        List<CommuneWebBanner> communeWebBannerList = communeWebBannerService.list(new LambdaQueryWrapper<>(new CommuneWebBanner()).eq(CommuneWebBanner::getDelFlag, 0).orderByAsc(CommuneWebBanner::getWeight));
        CommuneWebHeader communeWebHeader = communeWebHeaderService.getOne(new LambdaQueryWrapper<CommuneWebHeader>().eq(CommuneWebHeader::getDelFlag, 0));
        List<CommuneWebLesson > communeWebLessonList = communeWebLessonService.getList();
        List<CommuneTicket > communeTicketList = communeTicketService.list(new LambdaQueryWrapper<CommuneTicket>().eq(CommuneTicket::getIsShow,1).eq(CommuneTicket::getStatus,0));
        List<CommuneActivity > communeActivityList = communeActivityService.list(new LambdaQueryWrapper<CommuneActivity>().eq(CommuneActivity::getIsShow,1).eq(CommuneActivity::getStatus,0));
        return new CommuneWebVo(communeWebBannerList, communeWebHeader, communeWebLessonList, communeTicketList, communeActivityList);
    }
}
