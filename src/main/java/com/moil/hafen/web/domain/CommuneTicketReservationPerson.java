package com.moil.hafen.web.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_commune_ticket_reservation_person")
@ApiModel("门票预约人员信息")
public class CommuneTicketReservationPerson implements Serializable {
    private static final long serialVersionUID = -4281405709591207564L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("预约id")
    private Integer reservationId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("用户手机号")
    private String mobile;
    @ApiModelProperty("身份证号")
    private String idCard;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//是否删除 0正常 1删除
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
