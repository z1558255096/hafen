package com.moil.hafen.web.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author 陈子杰
 * @Description 公社退款规则配置
 * @Version 1.0.0
 * @Date 2023/12/10 11:37
 */
@Data
@TableName("t_commune_refund_rules_config")
public class CommuneRefundRulesConfig implements Serializable {
    private static final long serialVersionUID = 8513694822992391636L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("0:公社-门票退款;1:公社-课程退款")
    private Integer type;// 0:公社-门票退款;1:公社-课程退款
    @ApiModelProperty("距离上架前多少天")
    private Integer refundDays;
    @ApiModelProperty("退款比例")
    private Integer refundRate;
    @ApiModelProperty("0:开启;1:关闭")
    private Integer status;// 0开启;1关闭
    @ApiModelProperty("删除标识：0-正常;1-删除")
    @TableLogic
    private Integer delFlag;
    private Date createTime;
    private Date modifyTime;
    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;
}
