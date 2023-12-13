package com.moil.hafen.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moil.hafen.web.dao.CommuneTicketReservationDao;
import com.moil.hafen.web.dao.CommuneTicketReservationPersonDao;
import com.moil.hafen.web.domain.CommuneTicketReservation;
import com.moil.hafen.web.domain.CommuneTicketReservationPerson;
import com.moil.hafen.web.service.CommuneTicketReservationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommuneTicketReservationServiceImpl extends ServiceImpl<CommuneTicketReservationDao, CommuneTicketReservation> implements CommuneTicketReservationService {
    @Resource
    private CommuneTicketReservationPersonDao communeTicketReservationPersonDao;

    /**
     * 根据id查询详情
     *
     * @param id id
     *
     * @return {@link CommuneTicketReservation}
     */
    @Override
    public CommuneTicketReservation detail(Integer id) {
        CommuneTicketReservation communeTicketReservation = this.baseMapper.selectById(id);
        List<CommuneTicketReservationPerson> communeTicketReservationPeople = communeTicketReservationPersonDao.selectList(new LambdaQueryWrapper<CommuneTicketReservationPerson>().eq(CommuneTicketReservationPerson::getReservationId, id).eq(CommuneTicketReservationPerson::getDelFlag, 0));
        communeTicketReservation.setTicketReservationPeople(communeTicketReservationPeople);
        return communeTicketReservation;
    }
}
