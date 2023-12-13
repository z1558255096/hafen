package com.moil.hafen.web.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("t_commune_ticket_reservation")
@ApiModel("门票预约信息")
public class CommuneTicketReservation implements Serializable {
    private static final long serialVersionUID = -4281405709591207564L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户id")
    private Integer customerId;
    @ApiModelProperty("门票id")
    private Integer ticketId;
    @ApiModelProperty("数量")
    private Integer count;
    @ApiModelProperty("预约时间")
    private Date appointmentTime;
    @ApiModelProperty("星期")
    private String week;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//是否删除 0正常 1删除
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    @ApiModelProperty("预约人员信息")
    private transient List<CommuneTicketReservationPerson> ticketReservationPeople;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
