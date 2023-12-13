package com.moil.hafen.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moil.hafen.web.domain.CommuneTicket;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CommuneTicketDao extends BaseMapper<CommuneTicket> {

    @Update("update t_commune_ticket set count = count - #{count} where id = #{ticketId} and COUNT -  #{count} >= 0")
    Boolean updateTicketCount(@Param("ticketId") Integer ticketId, @Param("count") Integer count);
}
