package com.moil.hafen.web.vo;

import com.moil.hafen.web.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class CommuneWebVo {
    private List<CommuneWebBanner> communeWebBannerList;
    private CommuneWebHeader communeWebHeader;
    private List<CommuneWebLesson> communeWebLessonList;
    private List<CommuneTicket> communeTicketList;
    private List<CommuneActivity> communeActivityList;

    public CommuneWebVo(List<CommuneWebBanner> communeWebBannerList, CommuneWebHeader communeWebHeader, List<CommuneWebLesson> communeWebLessonList, List<CommuneTicket> communeTicketList, List<CommuneActivity> communeActivityList) {
        this.communeWebBannerList = communeWebBannerList;
        this.communeWebHeader = communeWebHeader;
        this.communeWebLessonList = communeWebLessonList;
        this.communeTicketList = communeTicketList;
        this.communeActivityList = communeActivityList;
    }
}
