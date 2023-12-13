package com.moil.hafen.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moil.hafen.web.domain.CommuneTicketReservation;

public interface CommuneTicketReservationService extends IService<CommuneTicketReservation> {

    /**
     * 根据id查询详情
     *
     * @param id id
     *
     * @return {@link CommuneTicketReservation}
     */
    CommuneTicketReservation detail(Integer id);
}
