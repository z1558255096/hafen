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
@TableName("t_commune_ticket_advance")
@ApiModel("公社门票高级设置")
public class CommuneTicketAdvance implements Serializable {
    private static final long serialVersionUID = 7033507297723837218L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("公社门票id")
    private Integer ticketId;
    @ApiModelProperty("类型： 0单选，1多选，2文本输入")
    private Integer type;//类型： 0单选，1多选，2文本输入
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片选项")
    private String imgOption;
    @ApiModelProperty("排序")
    private Integer sort;
    @ApiModelProperty("公社高级设置选项列表")
    private transient List<CommuneTicketAdvanceOption> communeTicketAdvanceOptionList;
    @ApiModelProperty("是否删除 0正常 1删除")
    private Integer delFlag;//0正常 1删除
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date modifyTime;
    private transient String createTimeFrom;
    private transient String createTimeTo;
}
